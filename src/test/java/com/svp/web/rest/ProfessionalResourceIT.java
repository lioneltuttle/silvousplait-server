package com.svp.web.rest;

import com.svp.SilvousplaitApp;
import com.svp.domain.Professional;
import com.svp.repository.ProfessionalRepository;
import com.svp.service.ProfessionalService;
import com.svp.service.dto.ProfessionalDTO;
import com.svp.service.mapper.ProfessionalMapper;
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
 * Integration tests for the {@link ProfessionalResource} REST controller.
 */
@SpringBootTest(classes = SilvousplaitApp.class)
public class ProfessionalResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_CREATION_DATE = LocalDate.ofEpochDay(-1L);

    private static final Boolean DEFAULT_UP = false;
    private static final Boolean UPDATED_UP = true;

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private ProfessionalRepository professionalRepository;

    @Autowired
    private ProfessionalMapper professionalMapper;

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

    private MockMvc restProfessionalMockMvc;

    private Professional professional;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProfessionalResource professionalResource = new ProfessionalResource(professionalService);
        this.restProfessionalMockMvc = MockMvcBuilders.standaloneSetup(professionalResource)
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
    public static Professional createEntity(EntityManager em) {
        Professional professional = new Professional()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .creationDate(DEFAULT_CREATION_DATE)
            .up(DEFAULT_UP)
            .active(DEFAULT_ACTIVE);
        return professional;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Professional createUpdatedEntity(EntityManager em) {
        Professional professional = new Professional()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .creationDate(UPDATED_CREATION_DATE)
            .up(UPDATED_UP)
            .active(UPDATED_ACTIVE);
        return professional;
    }

    @BeforeEach
    public void initTest() {
        professional = createEntity(em);
    }

    @Test
    @Transactional
    public void createProfessional() throws Exception {
        int databaseSizeBeforeCreate = professionalRepository.findAll().size();

        // Create the Professional
        ProfessionalDTO professionalDTO = professionalMapper.toDto(professional);
        restProfessionalMockMvc.perform(post("/api/professionals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(professionalDTO)))
            .andExpect(status().isCreated());

        // Validate the Professional in the database
        List<Professional> professionalList = professionalRepository.findAll();
        assertThat(professionalList).hasSize(databaseSizeBeforeCreate + 1);
        Professional testProfessional = professionalList.get(professionalList.size() - 1);
        assertThat(testProfessional.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testProfessional.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testProfessional.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testProfessional.isUp()).isEqualTo(DEFAULT_UP);
        assertThat(testProfessional.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createProfessionalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = professionalRepository.findAll().size();

        // Create the Professional with an existing ID
        professional.setId(1L);
        ProfessionalDTO professionalDTO = professionalMapper.toDto(professional);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfessionalMockMvc.perform(post("/api/professionals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(professionalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Professional in the database
        List<Professional> professionalList = professionalRepository.findAll();
        assertThat(professionalList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProfessionals() throws Exception {
        // Initialize the database
        professionalRepository.saveAndFlush(professional);

        // Get all the professionalList
        restProfessionalMockMvc.perform(get("/api/professionals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(professional.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].up").value(hasItem(DEFAULT_UP.booleanValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void getProfessional() throws Exception {
        // Initialize the database
        professionalRepository.saveAndFlush(professional);

        // Get the professional
        restProfessionalMockMvc.perform(get("/api/professionals/{id}", professional.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(professional.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.up").value(DEFAULT_UP.booleanValue()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingProfessional() throws Exception {
        // Get the professional
        restProfessionalMockMvc.perform(get("/api/professionals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProfessional() throws Exception {
        // Initialize the database
        professionalRepository.saveAndFlush(professional);

        int databaseSizeBeforeUpdate = professionalRepository.findAll().size();

        // Update the professional
        Professional updatedProfessional = professionalRepository.findById(professional.getId()).get();
        // Disconnect from session so that the updates on updatedProfessional are not directly saved in db
        em.detach(updatedProfessional);
        updatedProfessional
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .creationDate(UPDATED_CREATION_DATE)
            .up(UPDATED_UP)
            .active(UPDATED_ACTIVE);
        ProfessionalDTO professionalDTO = professionalMapper.toDto(updatedProfessional);

        restProfessionalMockMvc.perform(put("/api/professionals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(professionalDTO)))
            .andExpect(status().isOk());

        // Validate the Professional in the database
        List<Professional> professionalList = professionalRepository.findAll();
        assertThat(professionalList).hasSize(databaseSizeBeforeUpdate);
        Professional testProfessional = professionalList.get(professionalList.size() - 1);
        assertThat(testProfessional.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testProfessional.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testProfessional.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testProfessional.isUp()).isEqualTo(UPDATED_UP);
        assertThat(testProfessional.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingProfessional() throws Exception {
        int databaseSizeBeforeUpdate = professionalRepository.findAll().size();

        // Create the Professional
        ProfessionalDTO professionalDTO = professionalMapper.toDto(professional);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfessionalMockMvc.perform(put("/api/professionals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(professionalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Professional in the database
        List<Professional> professionalList = professionalRepository.findAll();
        assertThat(professionalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProfessional() throws Exception {
        // Initialize the database
        professionalRepository.saveAndFlush(professional);

        int databaseSizeBeforeDelete = professionalRepository.findAll().size();

        // Delete the professional
        restProfessionalMockMvc.perform(delete("/api/professionals/{id}", professional.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Professional> professionalList = professionalRepository.findAll();
        assertThat(professionalList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Professional.class);
        Professional professional1 = new Professional();
        professional1.setId(1L);
        Professional professional2 = new Professional();
        professional2.setId(professional1.getId());
        assertThat(professional1).isEqualTo(professional2);
        professional2.setId(2L);
        assertThat(professional1).isNotEqualTo(professional2);
        professional1.setId(null);
        assertThat(professional1).isNotEqualTo(professional2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProfessionalDTO.class);
        ProfessionalDTO professionalDTO1 = new ProfessionalDTO();
        professionalDTO1.setId(1L);
        ProfessionalDTO professionalDTO2 = new ProfessionalDTO();
        assertThat(professionalDTO1).isNotEqualTo(professionalDTO2);
        professionalDTO2.setId(professionalDTO1.getId());
        assertThat(professionalDTO1).isEqualTo(professionalDTO2);
        professionalDTO2.setId(2L);
        assertThat(professionalDTO1).isNotEqualTo(professionalDTO2);
        professionalDTO1.setId(null);
        assertThat(professionalDTO1).isNotEqualTo(professionalDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(professionalMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(professionalMapper.fromId(null)).isNull();
    }
}
