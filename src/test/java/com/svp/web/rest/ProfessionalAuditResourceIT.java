package com.svp.web.rest;

import com.svp.SilvousplaitApp;
import com.svp.domain.ProfessionalAudit;
import com.svp.domain.enumeration.ProfessionalEvent;
import com.svp.repository.ProfessionalAuditRepository;
import com.svp.service.ProfessionalAuditService;
import com.svp.service.dto.ProfessionalAuditDTO;
import com.svp.service.mapper.ProfessionalAuditMapper;
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
 * Integration tests for the {@link ProfessionalAuditResource} REST controller.
 */
@SpringBootTest(classes = SilvousplaitApp.class)
public class ProfessionalAuditResourceIT {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final ProfessionalEvent DEFAULT_EVENT = ProfessionalEvent.AVAILABLE;
    private static final ProfessionalEvent UPDATED_EVENT = ProfessionalEvent.NOT_AVAILABLE;

    @Autowired
    private ProfessionalAuditRepository professionalAuditRepository;

    @Autowired
    private ProfessionalAuditMapper professionalAuditMapper;

    @Autowired
    private ProfessionalAuditService professionalAuditService;

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

    private MockMvc restProfessionalAuditMockMvc;

    private ProfessionalAudit professionalAudit;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProfessionalAuditResource professionalAuditResource = new ProfessionalAuditResource(professionalAuditService);
        this.restProfessionalAuditMockMvc = MockMvcBuilders.standaloneSetup(professionalAuditResource)
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
    public static ProfessionalAudit createEntity(EntityManager em) {
        ProfessionalAudit professionalAudit = new ProfessionalAudit()
            .date(DEFAULT_DATE)
            .message(DEFAULT_MESSAGE)
            .event(DEFAULT_EVENT);
        return professionalAudit;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProfessionalAudit createUpdatedEntity(EntityManager em) {
        ProfessionalAudit professionalAudit = new ProfessionalAudit()
            .date(UPDATED_DATE)
            .message(UPDATED_MESSAGE)
            .event(UPDATED_EVENT);
        return professionalAudit;
    }

    @BeforeEach
    public void initTest() {
        professionalAudit = createEntity(em);
    }

    @Test
    @Transactional
    public void createProfessionalAudit() throws Exception {
        int databaseSizeBeforeCreate = professionalAuditRepository.findAll().size();

        // Create the ProfessionalAudit
        ProfessionalAuditDTO professionalAuditDTO = professionalAuditMapper.toDto(professionalAudit);
        restProfessionalAuditMockMvc.perform(post("/api/professional-audits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(professionalAuditDTO)))
            .andExpect(status().isCreated());

        // Validate the ProfessionalAudit in the database
        List<ProfessionalAudit> professionalAuditList = professionalAuditRepository.findAll();
        assertThat(professionalAuditList).hasSize(databaseSizeBeforeCreate + 1);
        ProfessionalAudit testProfessionalAudit = professionalAuditList.get(professionalAuditList.size() - 1);
        assertThat(testProfessionalAudit.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testProfessionalAudit.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testProfessionalAudit.getEvent()).isEqualTo(DEFAULT_EVENT);
    }

    @Test
    @Transactional
    public void createProfessionalAuditWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = professionalAuditRepository.findAll().size();

        // Create the ProfessionalAudit with an existing ID
        professionalAudit.setId(1L);
        ProfessionalAuditDTO professionalAuditDTO = professionalAuditMapper.toDto(professionalAudit);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfessionalAuditMockMvc.perform(post("/api/professional-audits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(professionalAuditDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProfessionalAudit in the database
        List<ProfessionalAudit> professionalAuditList = professionalAuditRepository.findAll();
        assertThat(professionalAuditList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProfessionalAudits() throws Exception {
        // Initialize the database
        professionalAuditRepository.saveAndFlush(professionalAudit);

        // Get all the professionalAuditList
        restProfessionalAuditMockMvc.perform(get("/api/professional-audits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(professionalAudit.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE.toString())))
            .andExpect(jsonPath("$.[*].event").value(hasItem(DEFAULT_EVENT.toString())));
    }

    @Test
    @Transactional
    public void getProfessionalAudit() throws Exception {
        // Initialize the database
        professionalAuditRepository.saveAndFlush(professionalAudit);

        // Get the professionalAudit
        restProfessionalAuditMockMvc.perform(get("/api/professional-audits/{id}", professionalAudit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(professionalAudit.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE.toString()))
            .andExpect(jsonPath("$.event").value(DEFAULT_EVENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProfessionalAudit() throws Exception {
        // Get the professionalAudit
        restProfessionalAuditMockMvc.perform(get("/api/professional-audits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProfessionalAudit() throws Exception {
        // Initialize the database
        professionalAuditRepository.saveAndFlush(professionalAudit);

        int databaseSizeBeforeUpdate = professionalAuditRepository.findAll().size();

        // Update the professionalAudit
        ProfessionalAudit updatedProfessionalAudit = professionalAuditRepository.findById(professionalAudit.getId()).get();
        // Disconnect from session so that the updates on updatedProfessionalAudit are not directly saved in db
        em.detach(updatedProfessionalAudit);
        updatedProfessionalAudit
            .date(UPDATED_DATE)
            .message(UPDATED_MESSAGE)
            .event(UPDATED_EVENT);
        ProfessionalAuditDTO professionalAuditDTO = professionalAuditMapper.toDto(updatedProfessionalAudit);

        restProfessionalAuditMockMvc.perform(put("/api/professional-audits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(professionalAuditDTO)))
            .andExpect(status().isOk());

        // Validate the ProfessionalAudit in the database
        List<ProfessionalAudit> professionalAuditList = professionalAuditRepository.findAll();
        assertThat(professionalAuditList).hasSize(databaseSizeBeforeUpdate);
        ProfessionalAudit testProfessionalAudit = professionalAuditList.get(professionalAuditList.size() - 1);
        assertThat(testProfessionalAudit.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testProfessionalAudit.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testProfessionalAudit.getEvent()).isEqualTo(UPDATED_EVENT);
    }

    @Test
    @Transactional
    public void updateNonExistingProfessionalAudit() throws Exception {
        int databaseSizeBeforeUpdate = professionalAuditRepository.findAll().size();

        // Create the ProfessionalAudit
        ProfessionalAuditDTO professionalAuditDTO = professionalAuditMapper.toDto(professionalAudit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfessionalAuditMockMvc.perform(put("/api/professional-audits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(professionalAuditDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProfessionalAudit in the database
        List<ProfessionalAudit> professionalAuditList = professionalAuditRepository.findAll();
        assertThat(professionalAuditList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProfessionalAudit() throws Exception {
        // Initialize the database
        professionalAuditRepository.saveAndFlush(professionalAudit);

        int databaseSizeBeforeDelete = professionalAuditRepository.findAll().size();

        // Delete the professionalAudit
        restProfessionalAuditMockMvc.perform(delete("/api/professional-audits/{id}", professionalAudit.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProfessionalAudit> professionalAuditList = professionalAuditRepository.findAll();
        assertThat(professionalAuditList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProfessionalAudit.class);
        ProfessionalAudit professionalAudit1 = new ProfessionalAudit();
        professionalAudit1.setId(1L);
        ProfessionalAudit professionalAudit2 = new ProfessionalAudit();
        professionalAudit2.setId(professionalAudit1.getId());
        assertThat(professionalAudit1).isEqualTo(professionalAudit2);
        professionalAudit2.setId(2L);
        assertThat(professionalAudit1).isNotEqualTo(professionalAudit2);
        professionalAudit1.setId(null);
        assertThat(professionalAudit1).isNotEqualTo(professionalAudit2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProfessionalAuditDTO.class);
        ProfessionalAuditDTO professionalAuditDTO1 = new ProfessionalAuditDTO();
        professionalAuditDTO1.setId(1L);
        ProfessionalAuditDTO professionalAuditDTO2 = new ProfessionalAuditDTO();
        assertThat(professionalAuditDTO1).isNotEqualTo(professionalAuditDTO2);
        professionalAuditDTO2.setId(professionalAuditDTO1.getId());
        assertThat(professionalAuditDTO1).isEqualTo(professionalAuditDTO2);
        professionalAuditDTO2.setId(2L);
        assertThat(professionalAuditDTO1).isNotEqualTo(professionalAuditDTO2);
        professionalAuditDTO1.setId(null);
        assertThat(professionalAuditDTO1).isNotEqualTo(professionalAuditDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(professionalAuditMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(professionalAuditMapper.fromId(null)).isNull();
    }
}
