package com.svp.web.rest;

import com.svp.SilvousplaitApp;
import com.svp.domain.ProRequest;
import com.svp.repository.ProRequestRepository;
import com.svp.service.ProRequestService;
import com.svp.service.ProfessionalService;
import com.svp.service.dto.ProRequestDTO;
import com.svp.service.mapper.ProRequestMapper;
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
 * Integration tests for the {@link ProRequestResource} REST controller.
 */
@SpringBootTest(classes = SilvousplaitApp.class)
public class ProRequestResourceIT {

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final Double DEFAULT_LAT = 1D;
    private static final Double UPDATED_LAT = 2D;
    private static final Double SMALLER_LAT = 1D - 1D;

    private static final Double DEFAULT_LNG = 1D;
    private static final Double UPDATED_LNG = 2D;
    private static final Double SMALLER_LNG = 1D - 1D;

    private static final String DEFAULT_DEVICE_REGISTRATION_ID = "AAAAAAAAAA";
    private static final String UPDATED_DEVICE_REGISTRATION_ID = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE = LocalDate.ofEpochDay(-1L);

    private static final Boolean DEFAULT_COME_OVER = false;
    private static final Boolean UPDATED_COME_OVER = true;

    @Autowired
    private ProRequestRepository proRequestRepository;

    @Autowired
    private ProRequestMapper proRequestMapper;

    @Autowired
    private ProRequestService proRequestService;

    @Autowired
    private ProfessionalService professionalService;

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

    private MockMvc restProRequestMockMvc;

    private ProRequest proRequest;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProRequestResource proRequestResource = new ProRequestResource(proRequestService, professionalService);
        this.restProRequestMockMvc = MockMvcBuilders.standaloneSetup(proRequestResource)
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
    public static ProRequest createEntity(EntityManager em) {
        ProRequest proRequest = new ProRequest()
            .location(DEFAULT_LOCATION)
            .lat(DEFAULT_LAT)
            .lng(DEFAULT_LNG)
            .deviceRegistrationId(DEFAULT_DEVICE_REGISTRATION_ID)
            .date(DEFAULT_DATE)
            .comeOver(DEFAULT_COME_OVER);
        return proRequest;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProRequest createUpdatedEntity(EntityManager em) {
        ProRequest proRequest = new ProRequest()
            .location(UPDATED_LOCATION)
            .lat(UPDATED_LAT)
            .lng(UPDATED_LNG)
            .deviceRegistrationId(UPDATED_DEVICE_REGISTRATION_ID)
            .date(UPDATED_DATE)
            .comeOver(UPDATED_COME_OVER);
        return proRequest;
    }

    @BeforeEach
    public void initTest() {
        proRequest = createEntity(em);
    }

    @Test
    @Transactional
    public void createProRequest() throws Exception {
        int databaseSizeBeforeCreate = proRequestRepository.findAll().size();

        // Create the ProRequest
        ProRequestDTO proRequestDTO = proRequestMapper.toDto(proRequest);
        restProRequestMockMvc.perform(post("/api/pro-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proRequestDTO)))
            .andExpect(status().isCreated());

        // Validate the ProRequest in the database
        List<ProRequest> proRequestList = proRequestRepository.findAll();
        assertThat(proRequestList).hasSize(databaseSizeBeforeCreate + 1);
        ProRequest testProRequest = proRequestList.get(proRequestList.size() - 1);
        assertThat(testProRequest.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testProRequest.getLat()).isEqualTo(DEFAULT_LAT);
        assertThat(testProRequest.getLng()).isEqualTo(DEFAULT_LNG);
        assertThat(testProRequest.getDeviceRegistrationId()).isEqualTo(DEFAULT_DEVICE_REGISTRATION_ID);
        assertThat(testProRequest.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testProRequest.isComeOver()).isEqualTo(DEFAULT_COME_OVER);
    }

    @Test
    @Transactional
    public void createProRequestWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = proRequestRepository.findAll().size();

        // Create the ProRequest with an existing ID
        proRequest.setId(1L);
        ProRequestDTO proRequestDTO = proRequestMapper.toDto(proRequest);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProRequestMockMvc.perform(post("/api/pro-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proRequestDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProRequest in the database
        List<ProRequest> proRequestList = proRequestRepository.findAll();
        assertThat(proRequestList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProRequests() throws Exception {
        // Initialize the database
        proRequestRepository.saveAndFlush(proRequest);

        // Get all the proRequestList
        restProRequestMockMvc.perform(get("/api/pro-requests?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(proRequest.getId().intValue())))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())))
            .andExpect(jsonPath("$.[*].lat").value(hasItem(DEFAULT_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].lng").value(hasItem(DEFAULT_LNG.doubleValue())))
            .andExpect(jsonPath("$.[*].deviceRegistrationId").value(hasItem(DEFAULT_DEVICE_REGISTRATION_ID.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].comeOver").value(hasItem(DEFAULT_COME_OVER.booleanValue())));
    }

    @Test
    @Transactional
    public void getProRequest() throws Exception {
        // Initialize the database
        proRequestRepository.saveAndFlush(proRequest);

        // Get the proRequest
        restProRequestMockMvc.perform(get("/api/pro-requests/{id}", proRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(proRequest.getId().intValue()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()))
            .andExpect(jsonPath("$.lat").value(DEFAULT_LAT.doubleValue()))
            .andExpect(jsonPath("$.lng").value(DEFAULT_LNG.doubleValue()))
            .andExpect(jsonPath("$.deviceRegistrationId").value(DEFAULT_DEVICE_REGISTRATION_ID.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.comeOver").value(DEFAULT_COME_OVER.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingProRequest() throws Exception {
        // Get the proRequest
        restProRequestMockMvc.perform(get("/api/pro-requests/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProRequest() throws Exception {
        // Initialize the database
        proRequestRepository.saveAndFlush(proRequest);

        int databaseSizeBeforeUpdate = proRequestRepository.findAll().size();

        // Update the proRequest
        ProRequest updatedProRequest = proRequestRepository.findById(proRequest.getId()).get();
        // Disconnect from session so that the updates on updatedProRequest are not directly saved in db
        em.detach(updatedProRequest);
        updatedProRequest
            .location(UPDATED_LOCATION)
            .lat(UPDATED_LAT)
            .lng(UPDATED_LNG)
            .deviceRegistrationId(UPDATED_DEVICE_REGISTRATION_ID)
            .date(UPDATED_DATE)
            .comeOver(UPDATED_COME_OVER);
        ProRequestDTO proRequestDTO = proRequestMapper.toDto(updatedProRequest);

        restProRequestMockMvc.perform(put("/api/pro-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proRequestDTO)))
            .andExpect(status().isOk());

        // Validate the ProRequest in the database
        List<ProRequest> proRequestList = proRequestRepository.findAll();
        assertThat(proRequestList).hasSize(databaseSizeBeforeUpdate);
        ProRequest testProRequest = proRequestList.get(proRequestList.size() - 1);
        assertThat(testProRequest.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testProRequest.getLat()).isEqualTo(UPDATED_LAT);
        assertThat(testProRequest.getLng()).isEqualTo(UPDATED_LNG);
        assertThat(testProRequest.getDeviceRegistrationId()).isEqualTo(UPDATED_DEVICE_REGISTRATION_ID);
        assertThat(testProRequest.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testProRequest.isComeOver()).isEqualTo(UPDATED_COME_OVER);
    }

    @Test
    @Transactional
    public void updateNonExistingProRequest() throws Exception {
        int databaseSizeBeforeUpdate = proRequestRepository.findAll().size();

        // Create the ProRequest
        ProRequestDTO proRequestDTO = proRequestMapper.toDto(proRequest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProRequestMockMvc.perform(put("/api/pro-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proRequestDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProRequest in the database
        List<ProRequest> proRequestList = proRequestRepository.findAll();
        assertThat(proRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProRequest() throws Exception {
        // Initialize the database
        proRequestRepository.saveAndFlush(proRequest);

        int databaseSizeBeforeDelete = proRequestRepository.findAll().size();

        // Delete the proRequest
        restProRequestMockMvc.perform(delete("/api/pro-requests/{id}", proRequest.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProRequest> proRequestList = proRequestRepository.findAll();
        assertThat(proRequestList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProRequest.class);
        ProRequest proRequest1 = new ProRequest();
        proRequest1.setId(1L);
        ProRequest proRequest2 = new ProRequest();
        proRequest2.setId(proRequest1.getId());
        assertThat(proRequest1).isEqualTo(proRequest2);
        proRequest2.setId(2L);
        assertThat(proRequest1).isNotEqualTo(proRequest2);
        proRequest1.setId(null);
        assertThat(proRequest1).isNotEqualTo(proRequest2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProRequestDTO.class);
        ProRequestDTO proRequestDTO1 = new ProRequestDTO();
        proRequestDTO1.setId(1L);
        ProRequestDTO proRequestDTO2 = new ProRequestDTO();
        assertThat(proRequestDTO1).isNotEqualTo(proRequestDTO2);
        proRequestDTO2.setId(proRequestDTO1.getId());
        assertThat(proRequestDTO1).isEqualTo(proRequestDTO2);
        proRequestDTO2.setId(2L);
        assertThat(proRequestDTO1).isNotEqualTo(proRequestDTO2);
        proRequestDTO1.setId(null);
        assertThat(proRequestDTO1).isNotEqualTo(proRequestDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(proRequestMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(proRequestMapper.fromId(null)).isNull();
    }
}
