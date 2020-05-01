package com.svp.web.rest;

import com.svp.SilvousplaitApp;
import com.svp.domain.CompanyLocation;
import com.svp.repository.CompanyLocationRepository;
import com.svp.service.CompanyLocationService;
import com.svp.service.dto.CompanyLocationDTO;
import com.svp.service.mapper.CompanyLocationMapper;
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
 * Integration tests for the {@Link CompanyLocationResource} REST controller.
 */
@SpringBootTest(classes = SilvousplaitApp.class)
public class CompanyLocationResourceIT {

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    @Autowired
    private CompanyLocationRepository companyLocationRepository;

    @Autowired
    private CompanyLocationMapper companyLocationMapper;

    @Autowired
    private CompanyLocationService companyLocationService;

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

    private MockMvc restCompanyLocationMockMvc;

    private CompanyLocation companyLocation;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompanyLocationResource companyLocationResource = new CompanyLocationResource(companyLocationService);
        this.restCompanyLocationMockMvc = MockMvcBuilders.standaloneSetup(companyLocationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompanyLocation createEntity(EntityManager em) {
        return CompanyLocation.builder()
            .adresse(DEFAULT_ADRESSE).build();
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompanyLocation createUpdatedEntity(EntityManager em) {
        return CompanyLocation.builder()
            .adresse(UPDATED_ADRESSE).build();
    }

    @BeforeEach
    public void initTest() {
        companyLocation = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompanyLocation() throws Exception {
        int databaseSizeBeforeCreate = companyLocationRepository.findAll().size();

        // Create the CompanyLocation
        CompanyLocationDTO companyLocationDTO = companyLocationMapper.toDto(companyLocation);
        restCompanyLocationMockMvc.perform(post("/api/company-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyLocationDTO)))
            .andExpect(status().isCreated());

        // Validate the CompanyLocation in the database
        List<CompanyLocation> companyLocationList = companyLocationRepository.findAll();
        assertThat(companyLocationList).hasSize(databaseSizeBeforeCreate + 1);
        CompanyLocation testCompanyLocation = companyLocationList.get(companyLocationList.size() - 1);
        assertThat(testCompanyLocation.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
    }

    @Test
    @Transactional
    public void createCompanyLocationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = companyLocationRepository.findAll().size();

        // Create the CompanyLocation with an existing ID
        companyLocation.setId(1L);
        CompanyLocationDTO companyLocationDTO = companyLocationMapper.toDto(companyLocation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyLocationMockMvc.perform(post("/api/company-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyLocationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CompanyLocation in the database
        List<CompanyLocation> companyLocationList = companyLocationRepository.findAll();
        assertThat(companyLocationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCompanyLocations() throws Exception {
        // Initialize the database
        companyLocationRepository.saveAndFlush(companyLocation);

        // Get all the companyLocationList
        restCompanyLocationMockMvc.perform(get("/api/company-locations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyLocation.getId().intValue())))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE.toString())));
    }

    @Test
    @Transactional
    public void getCompanyLocation() throws Exception {
        // Initialize the database
        companyLocationRepository.saveAndFlush(companyLocation);

        // Get the companyLocation
        restCompanyLocationMockMvc.perform(get("/api/company-locations/{id}", companyLocation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(companyLocation.getId().intValue()))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCompanyLocation() throws Exception {
        // Get the companyLocation
        restCompanyLocationMockMvc.perform(get("/api/company-locations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompanyLocation() throws Exception {
        // Initialize the database
        companyLocationRepository.saveAndFlush(companyLocation);

        int databaseSizeBeforeUpdate = companyLocationRepository.findAll().size();

        // Update the companyLocation
        CompanyLocation updatedCompanyLocation = companyLocationRepository.findById(companyLocation.getId()).get();
        // Disconnect from session so that the updates on updatedCompanyLocation are not directly saved in db
        em.detach(updatedCompanyLocation);
        updatedCompanyLocation.setAdresse(UPDATED_ADRESSE);
        CompanyLocationDTO companyLocationDTO = companyLocationMapper.toDto(updatedCompanyLocation);

        restCompanyLocationMockMvc.perform(put("/api/company-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyLocationDTO)))
            .andExpect(status().isOk());

        // Validate the CompanyLocation in the database
        List<CompanyLocation> companyLocationList = companyLocationRepository.findAll();
        assertThat(companyLocationList).hasSize(databaseSizeBeforeUpdate);
        CompanyLocation testCompanyLocation = companyLocationList.get(companyLocationList.size() - 1);
        assertThat(testCompanyLocation.getAdresse()).isEqualTo(UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    public void updateNonExistingCompanyLocation() throws Exception {
        int databaseSizeBeforeUpdate = companyLocationRepository.findAll().size();

        // Create the CompanyLocation
        CompanyLocationDTO companyLocationDTO = companyLocationMapper.toDto(companyLocation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyLocationMockMvc.perform(put("/api/company-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyLocationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CompanyLocation in the database
        List<CompanyLocation> companyLocationList = companyLocationRepository.findAll();
        assertThat(companyLocationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCompanyLocation() throws Exception {
        // Initialize the database
        companyLocationRepository.saveAndFlush(companyLocation);

        int databaseSizeBeforeDelete = companyLocationRepository.findAll().size();

        // Delete the companyLocation
        restCompanyLocationMockMvc.perform(delete("/api/company-locations/{id}", companyLocation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<CompanyLocation> companyLocationList = companyLocationRepository.findAll();
        assertThat(companyLocationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyLocation.class);
        CompanyLocation companyLocation1 = new CompanyLocation();
        companyLocation1.setId(1L);
        CompanyLocation companyLocation2 = new CompanyLocation();
        companyLocation2.setId(companyLocation1.getId());
        assertThat(companyLocation1).isEqualTo(companyLocation2);
        companyLocation2.setId(2L);
        assertThat(companyLocation1).isNotEqualTo(companyLocation2);
        companyLocation1.setId(null);
        assertThat(companyLocation1).isNotEqualTo(companyLocation2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyLocationDTO.class);
        CompanyLocationDTO companyLocationDTO1 = new CompanyLocationDTO();
        companyLocationDTO1.setId(1L);
        CompanyLocationDTO companyLocationDTO2 = new CompanyLocationDTO();
        assertThat(companyLocationDTO1).isNotEqualTo(companyLocationDTO2);
        companyLocationDTO2.setId(companyLocationDTO1.getId());
        assertThat(companyLocationDTO1).isEqualTo(companyLocationDTO2);
        companyLocationDTO2.setId(2L);
        assertThat(companyLocationDTO1).isNotEqualTo(companyLocationDTO2);
        companyLocationDTO1.setId(null);
        assertThat(companyLocationDTO1).isNotEqualTo(companyLocationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(companyLocationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(companyLocationMapper.fromId(null)).isNull();
    }
}
