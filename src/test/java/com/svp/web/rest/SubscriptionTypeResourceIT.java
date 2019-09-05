package com.svp.web.rest;

import com.svp.SilvousplaitApp;
import com.svp.domain.SubscriptionType;
import com.svp.repository.SubscriptionTypeRepository;
import com.svp.service.SubscriptionTypeService;
import com.svp.service.dto.SubscriptionTypeDTO;
import com.svp.service.mapper.SubscriptionTypeMapper;
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
 * Integration tests for the {@Link SubscriptionTypeResource} REST controller.
 */
@SpringBootTest(classes = SilvousplaitApp.class)
public class SubscriptionTypeResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    @Autowired
    private SubscriptionTypeRepository subscriptionTypeRepository;

    @Autowired
    private SubscriptionTypeMapper subscriptionTypeMapper;

    @Autowired
    private SubscriptionTypeService subscriptionTypeService;

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

    private MockMvc restSubscriptionTypeMockMvc;

    private SubscriptionType subscriptionType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SubscriptionTypeResource subscriptionTypeResource = new SubscriptionTypeResource(subscriptionTypeService);
        this.restSubscriptionTypeMockMvc = MockMvcBuilders.standaloneSetup(subscriptionTypeResource)
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
    public static SubscriptionType createEntity(EntityManager em) {
        SubscriptionType subscriptionType = new SubscriptionType()
            .type(DEFAULT_TYPE);
        return subscriptionType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubscriptionType createUpdatedEntity(EntityManager em) {
        SubscriptionType subscriptionType = new SubscriptionType()
            .type(UPDATED_TYPE);
        return subscriptionType;
    }

    @BeforeEach
    public void initTest() {
        subscriptionType = createEntity(em);
    }

    @Test
    @Transactional
    public void createSubscriptionType() throws Exception {
        int databaseSizeBeforeCreate = subscriptionTypeRepository.findAll().size();

        // Create the SubscriptionType
        SubscriptionTypeDTO subscriptionTypeDTO = subscriptionTypeMapper.toDto(subscriptionType);
        restSubscriptionTypeMockMvc.perform(post("/api/subscription-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subscriptionTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the SubscriptionType in the database
        List<SubscriptionType> subscriptionTypeList = subscriptionTypeRepository.findAll();
        assertThat(subscriptionTypeList).hasSize(databaseSizeBeforeCreate + 1);
        SubscriptionType testSubscriptionType = subscriptionTypeList.get(subscriptionTypeList.size() - 1);
        assertThat(testSubscriptionType.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createSubscriptionTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = subscriptionTypeRepository.findAll().size();

        // Create the SubscriptionType with an existing ID
        subscriptionType.setId(1L);
        SubscriptionTypeDTO subscriptionTypeDTO = subscriptionTypeMapper.toDto(subscriptionType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubscriptionTypeMockMvc.perform(post("/api/subscription-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subscriptionTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SubscriptionType in the database
        List<SubscriptionType> subscriptionTypeList = subscriptionTypeRepository.findAll();
        assertThat(subscriptionTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSubscriptionTypes() throws Exception {
        // Initialize the database
        subscriptionTypeRepository.saveAndFlush(subscriptionType);

        // Get all the subscriptionTypeList
        restSubscriptionTypeMockMvc.perform(get("/api/subscription-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subscriptionType.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getSubscriptionType() throws Exception {
        // Initialize the database
        subscriptionTypeRepository.saveAndFlush(subscriptionType);

        // Get the subscriptionType
        restSubscriptionTypeMockMvc.perform(get("/api/subscription-types/{id}", subscriptionType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(subscriptionType.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSubscriptionType() throws Exception {
        // Get the subscriptionType
        restSubscriptionTypeMockMvc.perform(get("/api/subscription-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSubscriptionType() throws Exception {
        // Initialize the database
        subscriptionTypeRepository.saveAndFlush(subscriptionType);

        int databaseSizeBeforeUpdate = subscriptionTypeRepository.findAll().size();

        // Update the subscriptionType
        SubscriptionType updatedSubscriptionType = subscriptionTypeRepository.findById(subscriptionType.getId()).get();
        // Disconnect from session so that the updates on updatedSubscriptionType are not directly saved in db
        em.detach(updatedSubscriptionType);
        updatedSubscriptionType
            .type(UPDATED_TYPE);
        SubscriptionTypeDTO subscriptionTypeDTO = subscriptionTypeMapper.toDto(updatedSubscriptionType);

        restSubscriptionTypeMockMvc.perform(put("/api/subscription-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subscriptionTypeDTO)))
            .andExpect(status().isOk());

        // Validate the SubscriptionType in the database
        List<SubscriptionType> subscriptionTypeList = subscriptionTypeRepository.findAll();
        assertThat(subscriptionTypeList).hasSize(databaseSizeBeforeUpdate);
        SubscriptionType testSubscriptionType = subscriptionTypeList.get(subscriptionTypeList.size() - 1);
        assertThat(testSubscriptionType.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingSubscriptionType() throws Exception {
        int databaseSizeBeforeUpdate = subscriptionTypeRepository.findAll().size();

        // Create the SubscriptionType
        SubscriptionTypeDTO subscriptionTypeDTO = subscriptionTypeMapper.toDto(subscriptionType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubscriptionTypeMockMvc.perform(put("/api/subscription-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subscriptionTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SubscriptionType in the database
        List<SubscriptionType> subscriptionTypeList = subscriptionTypeRepository.findAll();
        assertThat(subscriptionTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSubscriptionType() throws Exception {
        // Initialize the database
        subscriptionTypeRepository.saveAndFlush(subscriptionType);

        int databaseSizeBeforeDelete = subscriptionTypeRepository.findAll().size();

        // Delete the subscriptionType
        restSubscriptionTypeMockMvc.perform(delete("/api/subscription-types/{id}", subscriptionType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<SubscriptionType> subscriptionTypeList = subscriptionTypeRepository.findAll();
        assertThat(subscriptionTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubscriptionType.class);
        SubscriptionType subscriptionType1 = new SubscriptionType();
        subscriptionType1.setId(1L);
        SubscriptionType subscriptionType2 = new SubscriptionType();
        subscriptionType2.setId(subscriptionType1.getId());
        assertThat(subscriptionType1).isEqualTo(subscriptionType2);
        subscriptionType2.setId(2L);
        assertThat(subscriptionType1).isNotEqualTo(subscriptionType2);
        subscriptionType1.setId(null);
        assertThat(subscriptionType1).isNotEqualTo(subscriptionType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubscriptionTypeDTO.class);
        SubscriptionTypeDTO subscriptionTypeDTO1 = new SubscriptionTypeDTO();
        subscriptionTypeDTO1.setId(1L);
        SubscriptionTypeDTO subscriptionTypeDTO2 = new SubscriptionTypeDTO();
        assertThat(subscriptionTypeDTO1).isNotEqualTo(subscriptionTypeDTO2);
        subscriptionTypeDTO2.setId(subscriptionTypeDTO1.getId());
        assertThat(subscriptionTypeDTO1).isEqualTo(subscriptionTypeDTO2);
        subscriptionTypeDTO2.setId(2L);
        assertThat(subscriptionTypeDTO1).isNotEqualTo(subscriptionTypeDTO2);
        subscriptionTypeDTO1.setId(null);
        assertThat(subscriptionTypeDTO1).isNotEqualTo(subscriptionTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(subscriptionTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(subscriptionTypeMapper.fromId(null)).isNull();
    }
}
