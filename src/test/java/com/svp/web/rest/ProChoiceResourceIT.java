package com.svp.web.rest;

import com.svp.SilvousplaitApp;
import com.svp.domain.ProChoice;
import com.svp.repository.ProChoiceRepository;
import com.svp.service.ProChoiceService;
import com.svp.service.dto.ProChoiceDTO;
import com.svp.service.mapper.ProChoiceMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.svp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ProChoiceResource} REST controller.
 */
@SpringBootTest(classes = SilvousplaitApp.class)
public class ProChoiceResourceIT {

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_DEVICE_REGISTRATION_ID = "AAAAAAAAAA";
    private static final String UPDATED_DEVICE_REGISTRATION_ID = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE = LocalDate.ofEpochDay(-1L);

    @Autowired
    private ProChoiceRepository proChoiceRepository;

    @Autowired
    private ProChoiceMapper proChoiceMapper;

    @Autowired
    private ProChoiceService proChoiceService;

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

    private MockMvc restProChoiceMockMvc;

    private ProChoice proChoice;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProChoiceResource proChoiceResource = new ProChoiceResource(proChoiceService);
        this.restProChoiceMockMvc = MockMvcBuilders.standaloneSetup(proChoiceResource)
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
    public static ProChoice createEntity(EntityManager em) {
        ProChoice proChoice = new ProChoice()
            .location(DEFAULT_LOCATION)
            .deviceRegistrationId(DEFAULT_DEVICE_REGISTRATION_ID)
            .date(DEFAULT_DATE);
        return proChoice;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProChoice createUpdatedEntity(EntityManager em) {
        ProChoice proChoice = new ProChoice()
            .location(UPDATED_LOCATION)
            .deviceRegistrationId(UPDATED_DEVICE_REGISTRATION_ID)
            .date(UPDATED_DATE);
        return proChoice;
    }

    @BeforeEach
    public void initTest() {
        proChoice = createEntity(em);
    }

    @Test
    @Transactional
    public void createProChoice() throws Exception {
        int databaseSizeBeforeCreate = proChoiceRepository.findAll().size();

        // Create the ProChoice
        ProChoiceDTO proChoiceDTO = proChoiceMapper.toDto(proChoice);
        restProChoiceMockMvc.perform(post("/api/pro-choices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proChoiceDTO)))
            .andExpect(status().isCreated());

        // Validate the ProChoice in the database
        List<ProChoice> proChoiceList = proChoiceRepository.findAll();
        assertThat(proChoiceList).hasSize(databaseSizeBeforeCreate + 1);
        ProChoice testProChoice = proChoiceList.get(proChoiceList.size() - 1);
        assertThat(testProChoice.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testProChoice.getDeviceRegistrationId()).isEqualTo(DEFAULT_DEVICE_REGISTRATION_ID);
        assertThat(testProChoice.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createProChoiceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = proChoiceRepository.findAll().size();

        // Create the ProChoice with an existing ID
        proChoice.setId(1L);
        ProChoiceDTO proChoiceDTO = proChoiceMapper.toDto(proChoice);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProChoiceMockMvc.perform(post("/api/pro-choices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proChoiceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProChoice in the database
        List<ProChoice> proChoiceList = proChoiceRepository.findAll();
        assertThat(proChoiceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProChoices() throws Exception {
        // Initialize the database
        proChoiceRepository.saveAndFlush(proChoice);

        // Get all the proChoiceList
        restProChoiceMockMvc.perform(get("/api/pro-choices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(proChoice.getId().intValue())))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())))
            .andExpect(jsonPath("$.[*].deviceRegistrationId").value(hasItem(DEFAULT_DEVICE_REGISTRATION_ID.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getProChoice() throws Exception {
        // Initialize the database
        proChoiceRepository.saveAndFlush(proChoice);

        // Get the proChoice
        restProChoiceMockMvc.perform(get("/api/pro-choices/{id}", proChoice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(proChoice.getId().intValue()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()))
            .andExpect(jsonPath("$.deviceRegistrationId").value(DEFAULT_DEVICE_REGISTRATION_ID.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProChoice() throws Exception {
        // Get the proChoice
        restProChoiceMockMvc.perform(get("/api/pro-choices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProChoice() throws Exception {
        // Initialize the database
        proChoiceRepository.saveAndFlush(proChoice);

        int databaseSizeBeforeUpdate = proChoiceRepository.findAll().size();

        // Update the proChoice
        ProChoice updatedProChoice = proChoiceRepository.findById(proChoice.getId()).get();
        // Disconnect from session so that the updates on updatedProChoice are not directly saved in db
        em.detach(updatedProChoice);
        updatedProChoice
            .location(UPDATED_LOCATION)
            .deviceRegistrationId(UPDATED_DEVICE_REGISTRATION_ID)
            .date(UPDATED_DATE);
        ProChoiceDTO proChoiceDTO = proChoiceMapper.toDto(updatedProChoice);

        restProChoiceMockMvc.perform(put("/api/pro-choices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proChoiceDTO)))
            .andExpect(status().isOk());

        // Validate the ProChoice in the database
        List<ProChoice> proChoiceList = proChoiceRepository.findAll();
        assertThat(proChoiceList).hasSize(databaseSizeBeforeUpdate);
        ProChoice testProChoice = proChoiceList.get(proChoiceList.size() - 1);
        assertThat(testProChoice.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testProChoice.getDeviceRegistrationId()).isEqualTo(UPDATED_DEVICE_REGISTRATION_ID);
        assertThat(testProChoice.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingProChoice() throws Exception {
        int databaseSizeBeforeUpdate = proChoiceRepository.findAll().size();

        // Create the ProChoice
        ProChoiceDTO proChoiceDTO = proChoiceMapper.toDto(proChoice);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProChoiceMockMvc.perform(put("/api/pro-choices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proChoiceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProChoice in the database
        List<ProChoice> proChoiceList = proChoiceRepository.findAll();
        assertThat(proChoiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProChoice() throws Exception {
        // Initialize the database
        proChoiceRepository.saveAndFlush(proChoice);

        int databaseSizeBeforeDelete = proChoiceRepository.findAll().size();

        // Delete the proChoice
        restProChoiceMockMvc.perform(delete("/api/pro-choices/{id}", proChoice.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProChoice> proChoiceList = proChoiceRepository.findAll();
        assertThat(proChoiceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProChoice.class);
        ProChoice proChoice1 = new ProChoice();
        proChoice1.setId(1L);
        ProChoice proChoice2 = new ProChoice();
        proChoice2.setId(proChoice1.getId());
        assertThat(proChoice1).isEqualTo(proChoice2);
        proChoice2.setId(2L);
        assertThat(proChoice1).isNotEqualTo(proChoice2);
        proChoice1.setId(null);
        assertThat(proChoice1).isNotEqualTo(proChoice2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProChoiceDTO.class);
        ProChoiceDTO proChoiceDTO1 = new ProChoiceDTO();
        proChoiceDTO1.setId(1L);
        ProChoiceDTO proChoiceDTO2 = new ProChoiceDTO();
        assertThat(proChoiceDTO1).isNotEqualTo(proChoiceDTO2);
        proChoiceDTO2.setId(proChoiceDTO1.getId());
        assertThat(proChoiceDTO1).isEqualTo(proChoiceDTO2);
        proChoiceDTO2.setId(2L);
        assertThat(proChoiceDTO1).isNotEqualTo(proChoiceDTO2);
        proChoiceDTO1.setId(null);
        assertThat(proChoiceDTO1).isNotEqualTo(proChoiceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(proChoiceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(proChoiceMapper.fromId(null)).isNull();
    }
}
