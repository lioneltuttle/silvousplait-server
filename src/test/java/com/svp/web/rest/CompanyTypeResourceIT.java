package com.svp.web.rest;

import com.svp.SilvousplaitApp;
import com.svp.domain.CompanyType;
import com.svp.repository.CompanyTypeRepository;
import com.svp.service.CompanyTypeService;
import com.svp.service.dto.CompanyTypeDTO;
import com.svp.service.mapper.CompanyTypeMapper;
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
 * Integration tests for the {@Link CompanyTypeResource} REST controller.
 */
@SpringBootTest(classes = SilvousplaitApp.class)
public class CompanyTypeResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    @Autowired
    private CompanyTypeRepository companyTypeRepository;

    @Autowired
    private CompanyTypeMapper companyTypeMapper;

    @Autowired
    private CompanyTypeService companyTypeService;

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

    private MockMvc restCompanyTypeMockMvc;

    private CompanyType companyType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompanyTypeResource companyTypeResource = new CompanyTypeResource(companyTypeService);
        this.restCompanyTypeMockMvc = MockMvcBuilders.standaloneSetup(companyTypeResource)
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
    public static CompanyType createEntity(EntityManager em) {
        CompanyType companyType = new CompanyType()
            .type(DEFAULT_TYPE);
        return companyType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompanyType createUpdatedEntity(EntityManager em) {
        CompanyType companyType = new CompanyType()
            .type(UPDATED_TYPE);
        return companyType;
    }

    @BeforeEach
    public void initTest() {
        companyType = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompanyType() throws Exception {
        int databaseSizeBeforeCreate = companyTypeRepository.findAll().size();

        // Create the CompanyType
        CompanyTypeDTO companyTypeDTO = companyTypeMapper.toDto(companyType);
        restCompanyTypeMockMvc.perform(post("/api/company-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the CompanyType in the database
        List<CompanyType> companyTypeList = companyTypeRepository.findAll();
        assertThat(companyTypeList).hasSize(databaseSizeBeforeCreate + 1);
        CompanyType testCompanyType = companyTypeList.get(companyTypeList.size() - 1);
        assertThat(testCompanyType.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createCompanyTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = companyTypeRepository.findAll().size();

        // Create the CompanyType with an existing ID
        companyType.setId(1L);
        CompanyTypeDTO companyTypeDTO = companyTypeMapper.toDto(companyType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyTypeMockMvc.perform(post("/api/company-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CompanyType in the database
        List<CompanyType> companyTypeList = companyTypeRepository.findAll();
        assertThat(companyTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCompanyTypes() throws Exception {
        // Initialize the database
        companyTypeRepository.saveAndFlush(companyType);

        // Get all the companyTypeList
        restCompanyTypeMockMvc.perform(get("/api/company-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyType.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getCompanyType() throws Exception {
        // Initialize the database
        companyTypeRepository.saveAndFlush(companyType);

        // Get the companyType
        restCompanyTypeMockMvc.perform(get("/api/company-types/{id}", companyType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(companyType.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCompanyType() throws Exception {
        // Get the companyType
        restCompanyTypeMockMvc.perform(get("/api/company-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompanyType() throws Exception {
        // Initialize the database
        companyTypeRepository.saveAndFlush(companyType);

        int databaseSizeBeforeUpdate = companyTypeRepository.findAll().size();

        // Update the companyType
        CompanyType updatedCompanyType = companyTypeRepository.findById(companyType.getId()).get();
        // Disconnect from session so that the updates on updatedCompanyType are not directly saved in db
        em.detach(updatedCompanyType);
        updatedCompanyType
            .type(UPDATED_TYPE);
        CompanyTypeDTO companyTypeDTO = companyTypeMapper.toDto(updatedCompanyType);

        restCompanyTypeMockMvc.perform(put("/api/company-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyTypeDTO)))
            .andExpect(status().isOk());

        // Validate the CompanyType in the database
        List<CompanyType> companyTypeList = companyTypeRepository.findAll();
        assertThat(companyTypeList).hasSize(databaseSizeBeforeUpdate);
        CompanyType testCompanyType = companyTypeList.get(companyTypeList.size() - 1);
        assertThat(testCompanyType.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingCompanyType() throws Exception {
        int databaseSizeBeforeUpdate = companyTypeRepository.findAll().size();

        // Create the CompanyType
        CompanyTypeDTO companyTypeDTO = companyTypeMapper.toDto(companyType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyTypeMockMvc.perform(put("/api/company-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CompanyType in the database
        List<CompanyType> companyTypeList = companyTypeRepository.findAll();
        assertThat(companyTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCompanyType() throws Exception {
        // Initialize the database
        companyTypeRepository.saveAndFlush(companyType);

        int databaseSizeBeforeDelete = companyTypeRepository.findAll().size();

        // Delete the companyType
        restCompanyTypeMockMvc.perform(delete("/api/company-types/{id}", companyType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<CompanyType> companyTypeList = companyTypeRepository.findAll();
        assertThat(companyTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyType.class);
        CompanyType companyType1 = new CompanyType();
        companyType1.setId(1L);
        CompanyType companyType2 = new CompanyType();
        companyType2.setId(companyType1.getId());
        assertThat(companyType1).isEqualTo(companyType2);
        companyType2.setId(2L);
        assertThat(companyType1).isNotEqualTo(companyType2);
        companyType1.setId(null);
        assertThat(companyType1).isNotEqualTo(companyType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyTypeDTO.class);
        CompanyTypeDTO companyTypeDTO1 = new CompanyTypeDTO();
        companyTypeDTO1.setId(1L);
        CompanyTypeDTO companyTypeDTO2 = new CompanyTypeDTO();
        assertThat(companyTypeDTO1).isNotEqualTo(companyTypeDTO2);
        companyTypeDTO2.setId(companyTypeDTO1.getId());
        assertThat(companyTypeDTO1).isEqualTo(companyTypeDTO2);
        companyTypeDTO2.setId(2L);
        assertThat(companyTypeDTO1).isNotEqualTo(companyTypeDTO2);
        companyTypeDTO1.setId(null);
        assertThat(companyTypeDTO1).isNotEqualTo(companyTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(companyTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(companyTypeMapper.fromId(null)).isNull();
    }
}
