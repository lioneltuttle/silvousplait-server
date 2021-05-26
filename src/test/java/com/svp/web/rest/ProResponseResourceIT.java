package com.svp.web.rest;

import com.svp.SilvousplaitApp;
import com.svp.domain.ProResponse;
import com.svp.repository.ProResponseRepository;
import com.svp.service.ProResponseService;
import com.svp.service.dto.ProResponseDTO;
import com.svp.service.mapper.ProResponseMapper;
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
 * Integration tests for the {@link ProResponseResource} REST controller.
 */
@SpringBootTest(classes = SilvousplaitApp.class)
public class ProResponseResourceIT {

    private static final Boolean DEFAULT_ACCEPT = false;
    private static final Boolean UPDATED_ACCEPT = true;

    @Autowired
    private ProResponseRepository proResponseRepository;

    @Autowired
    private ProResponseMapper proResponseMapper;

    @Autowired
    private ProResponseService proResponseService;

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

    private MockMvc restProResponseMockMvc;

    private ProResponse proResponse;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProResponseResource proResponseResource = new ProResponseResource(proResponseService);
        this.restProResponseMockMvc = MockMvcBuilders.standaloneSetup(proResponseResource)
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
    public static ProResponse createEntity(EntityManager em) {
        ProResponse proResponse = new ProResponse()
            .accept(DEFAULT_ACCEPT);
        return proResponse;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProResponse createUpdatedEntity(EntityManager em) {
        ProResponse proResponse = new ProResponse()
            .accept(UPDATED_ACCEPT);
        return proResponse;
    }

    @BeforeEach
    public void initTest() {
        proResponse = createEntity(em);
    }

    @Test
    @Transactional
    public void createProResponse() throws Exception {
        int databaseSizeBeforeCreate = proResponseRepository.findAll().size();

        // Create the ProResponse
        ProResponseDTO proResponseDTO = proResponseMapper.toDto(proResponse);
        restProResponseMockMvc.perform(post("/api/pro-responses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proResponseDTO)))
            .andExpect(status().isCreated());

        // Validate the ProResponse in the database
        List<ProResponse> proResponseList = proResponseRepository.findAll();
        assertThat(proResponseList).hasSize(databaseSizeBeforeCreate + 1);
        ProResponse testProResponse = proResponseList.get(proResponseList.size() - 1);
        assertThat(testProResponse.isAccept()).isEqualTo(DEFAULT_ACCEPT);
    }

    @Test
    @Transactional
    public void createProResponseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = proResponseRepository.findAll().size();

        // Create the ProResponse with an existing ID
        proResponse.setId(1L);
        ProResponseDTO proResponseDTO = proResponseMapper.toDto(proResponse);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProResponseMockMvc.perform(post("/api/pro-responses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proResponseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProResponse in the database
        List<ProResponse> proResponseList = proResponseRepository.findAll();
        assertThat(proResponseList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProResponses() throws Exception {
        // Initialize the database
        proResponseRepository.saveAndFlush(proResponse);

        // Get all the proResponseList
        restProResponseMockMvc.perform(get("/api/pro-responses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(proResponse.getId().intValue())))
            .andExpect(jsonPath("$.[*].accept").value(hasItem(DEFAULT_ACCEPT.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getProResponse() throws Exception {
        // Initialize the database
        proResponseRepository.saveAndFlush(proResponse);

        // Get the proResponse
        restProResponseMockMvc.perform(get("/api/pro-responses/{id}", proResponse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(proResponse.getId().intValue()))
            .andExpect(jsonPath("$.accept").value(DEFAULT_ACCEPT.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingProResponse() throws Exception {
        // Get the proResponse
        restProResponseMockMvc.perform(get("/api/pro-responses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProResponse() throws Exception {
        // Initialize the database
        proResponseRepository.saveAndFlush(proResponse);

        int databaseSizeBeforeUpdate = proResponseRepository.findAll().size();

        // Update the proResponse
        ProResponse updatedProResponse = proResponseRepository.findById(proResponse.getId()).get();
        // Disconnect from session so that the updates on updatedProResponse are not directly saved in db
        em.detach(updatedProResponse);
        updatedProResponse
            .accept(UPDATED_ACCEPT);
        ProResponseDTO proResponseDTO = proResponseMapper.toDto(updatedProResponse);

        restProResponseMockMvc.perform(put("/api/pro-responses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proResponseDTO)))
            .andExpect(status().isOk());

        // Validate the ProResponse in the database
        List<ProResponse> proResponseList = proResponseRepository.findAll();
        assertThat(proResponseList).hasSize(databaseSizeBeforeUpdate);
        ProResponse testProResponse = proResponseList.get(proResponseList.size() - 1);
        assertThat(testProResponse.isAccept()).isEqualTo(UPDATED_ACCEPT);
    }

    @Test
    @Transactional
    public void updateNonExistingProResponse() throws Exception {
        int databaseSizeBeforeUpdate = proResponseRepository.findAll().size();

        // Create the ProResponse
        ProResponseDTO proResponseDTO = proResponseMapper.toDto(proResponse);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProResponseMockMvc.perform(put("/api/pro-responses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proResponseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProResponse in the database
        List<ProResponse> proResponseList = proResponseRepository.findAll();
        assertThat(proResponseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProResponse() throws Exception {
        // Initialize the database
        proResponseRepository.saveAndFlush(proResponse);

        int databaseSizeBeforeDelete = proResponseRepository.findAll().size();

        // Delete the proResponse
        restProResponseMockMvc.perform(delete("/api/pro-responses/{id}", proResponse.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProResponse> proResponseList = proResponseRepository.findAll();
        assertThat(proResponseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProResponse.class);
        ProResponse proResponse1 = new ProResponse();
        proResponse1.setId(1L);
        ProResponse proResponse2 = new ProResponse();
        proResponse2.setId(proResponse1.getId());
        assertThat(proResponse1).isEqualTo(proResponse2);
        proResponse2.setId(2L);
        assertThat(proResponse1).isNotEqualTo(proResponse2);
        proResponse1.setId(null);
        assertThat(proResponse1).isNotEqualTo(proResponse2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProResponseDTO.class);
        ProResponseDTO proResponseDTO1 = new ProResponseDTO();
        proResponseDTO1.setId(1L);
        ProResponseDTO proResponseDTO2 = new ProResponseDTO();
        assertThat(proResponseDTO1).isNotEqualTo(proResponseDTO2);
        proResponseDTO2.setId(proResponseDTO1.getId());
        assertThat(proResponseDTO1).isEqualTo(proResponseDTO2);
        proResponseDTO2.setId(2L);
        assertThat(proResponseDTO1).isNotEqualTo(proResponseDTO2);
        proResponseDTO1.setId(null);
        assertThat(proResponseDTO1).isNotEqualTo(proResponseDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(proResponseMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(proResponseMapper.fromId(null)).isNull();
    }
}
