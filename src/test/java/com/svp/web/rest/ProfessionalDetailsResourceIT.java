package com.svp.web.rest;

import com.svp.SilvousplaitApp;
import com.svp.domain.ProfessionalDetails;
import com.svp.repository.ProfessionalDetailsRepository;
import com.svp.service.ProfessionalDetailsService;
import com.svp.service.dto.ProfessionalDetailsDTO;
import com.svp.service.mapper.ProfessionalDetailsMapper;
import com.svp.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.svp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link ProfessionalDetailsResource} REST controller.
 */
@SpringBootTest(classes = SilvousplaitApp.class)
public class ProfessionalDetailsResourceIT {

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final Double DEFAULT_HOURLY_RATE = 1D;
    private static final Double UPDATED_HOURLY_RATE = 2D;

    private static final Boolean DEFAULT_ON_MOBILITY = false;
    private static final Boolean UPDATED_ON_MOBILITY = true;

    @Autowired
    private ProfessionalDetailsRepository professionalDetailsRepository;

    @Autowired
    private ProfessionalDetailsMapper professionalDetailsMapper;

    @Autowired
    private ProfessionalDetailsService professionalDetailsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restProfessionalDetailsMockMvc;

    private ProfessionalDetails professionalDetails;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProfessionalDetailsResource professionalDetailsResource = new ProfessionalDetailsResource(professionalDetailsService);
        this.restProfessionalDetailsMockMvc = MockMvcBuilders.standaloneSetup(professionalDetailsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProfessionalDetails createEntity(EntityManager em) {
        ProfessionalDetails professionalDetails = new ProfessionalDetails()
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .hourlyRate(DEFAULT_HOURLY_RATE)
            .onMobility(DEFAULT_ON_MOBILITY);
        return professionalDetails;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProfessionalDetails createUpdatedEntity(EntityManager em) {
        ProfessionalDetails professionalDetails = new ProfessionalDetails()
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .hourlyRate(UPDATED_HOURLY_RATE)
            .onMobility(UPDATED_ON_MOBILITY);
        return professionalDetails;
    }

    @BeforeEach
    public void initTest() {
        professionalDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createProfessionalDetails() throws Exception {
        int databaseSizeBeforeCreate = professionalDetailsRepository.findAll().size();

        // Create the ProfessionalDetails
        ProfessionalDetailsDTO professionalDetailsDTO = professionalDetailsMapper.toDto(professionalDetails);
        restProfessionalDetailsMockMvc.perform(post("/api/professional-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(professionalDetailsDTO)))
            .andExpect(status().isCreated());

        // Validate the ProfessionalDetails in the database
        List<ProfessionalDetails> professionalDetailsList = professionalDetailsRepository.findAll();
        assertThat(professionalDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        ProfessionalDetails testProfessionalDetails = professionalDetailsList.get(professionalDetailsList.size() - 1);
        assertThat(testProfessionalDetails.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testProfessionalDetails.getHourlyRate()).isEqualTo(DEFAULT_HOURLY_RATE);
        assertThat(testProfessionalDetails.isOnMobility()).isEqualTo(DEFAULT_ON_MOBILITY);
    }

    @Test
    @Transactional
    public void createProfessionalDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = professionalDetailsRepository.findAll().size();

        // Create the ProfessionalDetails with an existing ID
        professionalDetails.setId(1L);
        ProfessionalDetailsDTO professionalDetailsDTO = professionalDetailsMapper.toDto(professionalDetails);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfessionalDetailsMockMvc.perform(post("/api/professional-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(professionalDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProfessionalDetails in the database
        List<ProfessionalDetails> professionalDetailsList = professionalDetailsRepository.findAll();
        assertThat(professionalDetailsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProfessionalDetails() throws Exception {
        // Initialize the database
        professionalDetailsRepository.saveAndFlush(professionalDetails);

        // Get all the professionalDetailsList
        restProfessionalDetailsMockMvc.perform(get("/api/professional-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(professionalDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].hourlyRate").value(hasItem(DEFAULT_HOURLY_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].onMobility").value(hasItem(DEFAULT_ON_MOBILITY.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getProfessionalDetails() throws Exception {
        // Initialize the database
        professionalDetailsRepository.saveAndFlush(professionalDetails);

        // Get the professionalDetails
        restProfessionalDetailsMockMvc.perform(get("/api/professional-details/{id}", professionalDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(professionalDetails.getId().intValue()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER.toString()))
            .andExpect(jsonPath("$.hourlyRate").value(DEFAULT_HOURLY_RATE.doubleValue()))
            .andExpect(jsonPath("$.onMobility").value(DEFAULT_ON_MOBILITY.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingProfessionalDetails() throws Exception {
        // Get the professionalDetails
        restProfessionalDetailsMockMvc.perform(get("/api/professional-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProfessionalDetails() throws Exception {
        // Initialize the database
        professionalDetailsRepository.saveAndFlush(professionalDetails);

        int databaseSizeBeforeUpdate = professionalDetailsRepository.findAll().size();

        // Update the professionalDetails
        ProfessionalDetails updatedProfessionalDetails = professionalDetailsRepository.findById(professionalDetails.getId()).get();
        // Disconnect from session so that the updates on updatedProfessionalDetails are not directly saved in db
        em.detach(updatedProfessionalDetails);
        updatedProfessionalDetails
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .hourlyRate(UPDATED_HOURLY_RATE)
            .onMobility(UPDATED_ON_MOBILITY);
        ProfessionalDetailsDTO professionalDetailsDTO = professionalDetailsMapper.toDto(updatedProfessionalDetails);

        restProfessionalDetailsMockMvc.perform(put("/api/professional-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(professionalDetailsDTO)))
            .andExpect(status().isOk());

        // Validate the ProfessionalDetails in the database
        List<ProfessionalDetails> professionalDetailsList = professionalDetailsRepository.findAll();
        assertThat(professionalDetailsList).hasSize(databaseSizeBeforeUpdate);
        ProfessionalDetails testProfessionalDetails = professionalDetailsList.get(professionalDetailsList.size() - 1);
        assertThat(testProfessionalDetails.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testProfessionalDetails.getHourlyRate()).isEqualTo(UPDATED_HOURLY_RATE);
        assertThat(testProfessionalDetails.isOnMobility()).isEqualTo(UPDATED_ON_MOBILITY);
    }

    @Test
    @Transactional
    public void updateNonExistingProfessionalDetails() throws Exception {
        int databaseSizeBeforeUpdate = professionalDetailsRepository.findAll().size();

        // Create the ProfessionalDetails
        ProfessionalDetailsDTO professionalDetailsDTO = professionalDetailsMapper.toDto(professionalDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfessionalDetailsMockMvc.perform(put("/api/professional-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(professionalDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProfessionalDetails in the database
        List<ProfessionalDetails> professionalDetailsList = professionalDetailsRepository.findAll();
        assertThat(professionalDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProfessionalDetails() throws Exception {
        // Initialize the database
        professionalDetailsRepository.saveAndFlush(professionalDetails);

        int databaseSizeBeforeDelete = professionalDetailsRepository.findAll().size();

        // Delete the professionalDetails
        restProfessionalDetailsMockMvc.perform(delete("/api/professional-details/{id}", professionalDetails.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<ProfessionalDetails> professionalDetailsList = professionalDetailsRepository.findAll();
        assertThat(professionalDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProfessionalDetails.class);
        ProfessionalDetails professionalDetails1 = new ProfessionalDetails();
        professionalDetails1.setId(1L);
        ProfessionalDetails professionalDetails2 = new ProfessionalDetails();
        professionalDetails2.setId(professionalDetails1.getId());
        assertThat(professionalDetails1).isEqualTo(professionalDetails2);
        professionalDetails2.setId(2L);
        assertThat(professionalDetails1).isNotEqualTo(professionalDetails2);
        professionalDetails1.setId(null);
        assertThat(professionalDetails1).isNotEqualTo(professionalDetails2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProfessionalDetailsDTO.class);
        ProfessionalDetailsDTO professionalDetailsDTO1 = new ProfessionalDetailsDTO();
        professionalDetailsDTO1.setId(1L);
        ProfessionalDetailsDTO professionalDetailsDTO2 = new ProfessionalDetailsDTO();
        assertThat(professionalDetailsDTO1).isNotEqualTo(professionalDetailsDTO2);
        professionalDetailsDTO2.setId(professionalDetailsDTO1.getId());
        assertThat(professionalDetailsDTO1).isEqualTo(professionalDetailsDTO2);
        professionalDetailsDTO2.setId(2L);
        assertThat(professionalDetailsDTO1).isNotEqualTo(professionalDetailsDTO2);
        professionalDetailsDTO1.setId(null);
        assertThat(professionalDetailsDTO1).isNotEqualTo(professionalDetailsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(professionalDetailsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(professionalDetailsMapper.fromId(null)).isNull();
    }
}
