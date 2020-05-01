package com.svp.web.rest;

import com.svp.SilvousplaitApp;
import com.svp.domain.BillAudit;
import com.svp.repository.BillAuditRepository;
import com.svp.service.BillAuditService;
import com.svp.service.dto.BillAuditDTO;
import com.svp.service.mapper.BillAuditMapper;
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

import com.svp.domain.enumeration.BillEvent;

/**
 * Integration tests for the {@Link BillAuditResource} REST controller.
 */
@SpringBootTest(classes = SilvousplaitApp.class)
public class BillAuditResourceIT {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final BillEvent DEFAULT_EVENT = BillEvent.START;
    private static final BillEvent UPDATED_EVENT = BillEvent.END;

    @Autowired
    private BillAuditRepository billAuditRepository;

    @Autowired
    private BillAuditMapper billAuditMapper;

    @Autowired
    private BillAuditService billAuditService;

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

    private MockMvc restBillAuditMockMvc;

    private BillAudit billAudit;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BillAuditResource billAuditResource = new BillAuditResource(billAuditService);
        this.restBillAuditMockMvc = MockMvcBuilders.standaloneSetup(billAuditResource)
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
    public static BillAudit createEntity(EntityManager em) {
        return BillAudit.builder()
            .date(DEFAULT_DATE)
            .message(DEFAULT_MESSAGE)
            .event(DEFAULT_EVENT).build();
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BillAudit createUpdatedEntity(EntityManager em) {
        return BillAudit.builder()
            .date(UPDATED_DATE)
            .message(UPDATED_MESSAGE)
            .event(UPDATED_EVENT).build();
    }

    @BeforeEach
    public void initTest() {
        billAudit = createEntity(em);
    }

    @Test
    @Transactional
    public void createBillAudit() throws Exception {
        int databaseSizeBeforeCreate = billAuditRepository.findAll().size();

        // Create the BillAudit
        BillAuditDTO billAuditDTO = billAuditMapper.toDto(billAudit);
        restBillAuditMockMvc.perform(post("/api/bill-audits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billAuditDTO)))
            .andExpect(status().isCreated());

        // Validate the BillAudit in the database
        List<BillAudit> billAuditList = billAuditRepository.findAll();
        assertThat(billAuditList).hasSize(databaseSizeBeforeCreate + 1);
        BillAudit testBillAudit = billAuditList.get(billAuditList.size() - 1);
        assertThat(testBillAudit.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testBillAudit.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testBillAudit.getEvent()).isEqualTo(DEFAULT_EVENT);
    }

    @Test
    @Transactional
    public void createBillAuditWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = billAuditRepository.findAll().size();

        // Create the BillAudit with an existing ID
        billAudit.setId(1L);
        BillAuditDTO billAuditDTO = billAuditMapper.toDto(billAudit);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBillAuditMockMvc.perform(post("/api/bill-audits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billAuditDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BillAudit in the database
        List<BillAudit> billAuditList = billAuditRepository.findAll();
        assertThat(billAuditList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBillAudits() throws Exception {
        // Initialize the database
        billAuditRepository.saveAndFlush(billAudit);

        // Get all the billAuditList
        restBillAuditMockMvc.perform(get("/api/bill-audits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(billAudit.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE.toString())))
            .andExpect(jsonPath("$.[*].event").value(hasItem(DEFAULT_EVENT.toString())));
    }

    @Test
    @Transactional
    public void getBillAudit() throws Exception {
        // Initialize the database
        billAuditRepository.saveAndFlush(billAudit);

        // Get the billAudit
        restBillAuditMockMvc.perform(get("/api/bill-audits/{id}", billAudit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(billAudit.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE.toString()))
            .andExpect(jsonPath("$.event").value(DEFAULT_EVENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBillAudit() throws Exception {
        // Get the billAudit
        restBillAuditMockMvc.perform(get("/api/bill-audits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBillAudit() throws Exception {
        // Initialize the database
        billAuditRepository.saveAndFlush(billAudit);

        int databaseSizeBeforeUpdate = billAuditRepository.findAll().size();

        // Update the billAudit
        BillAudit updatedBillAudit = billAuditRepository.findById(billAudit.getId()).get();
        // Disconnect from session so that the updates on updatedBillAudit are not directly saved in db
        em.detach(updatedBillAudit);

        updatedBillAudit.setDate(UPDATED_DATE);
        updatedBillAudit.setMessage(UPDATED_MESSAGE);
        updatedBillAudit.setEvent(UPDATED_EVENT);
        BillAuditDTO billAuditDTO = billAuditMapper.toDto(updatedBillAudit);

        restBillAuditMockMvc.perform(put("/api/bill-audits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billAuditDTO)))
            .andExpect(status().isOk());

        // Validate the BillAudit in the database
        List<BillAudit> billAuditList = billAuditRepository.findAll();
        assertThat(billAuditList).hasSize(databaseSizeBeforeUpdate);
        BillAudit testBillAudit = billAuditList.get(billAuditList.size() - 1);
        assertThat(testBillAudit.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testBillAudit.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testBillAudit.getEvent()).isEqualTo(UPDATED_EVENT);
    }

    @Test
    @Transactional
    public void updateNonExistingBillAudit() throws Exception {
        int databaseSizeBeforeUpdate = billAuditRepository.findAll().size();

        // Create the BillAudit
        BillAuditDTO billAuditDTO = billAuditMapper.toDto(billAudit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBillAuditMockMvc.perform(put("/api/bill-audits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billAuditDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BillAudit in the database
        List<BillAudit> billAuditList = billAuditRepository.findAll();
        assertThat(billAuditList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBillAudit() throws Exception {
        // Initialize the database
        billAuditRepository.saveAndFlush(billAudit);

        int databaseSizeBeforeDelete = billAuditRepository.findAll().size();

        // Delete the billAudit
        restBillAuditMockMvc.perform(delete("/api/bill-audits/{id}", billAudit.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<BillAudit> billAuditList = billAuditRepository.findAll();
        assertThat(billAuditList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BillAudit.class);
        BillAudit billAudit1 = new BillAudit();
        billAudit1.setId(1L);
        BillAudit billAudit2 = new BillAudit();
        billAudit2.setId(billAudit1.getId());
        assertThat(billAudit1).isEqualTo(billAudit2);
        billAudit2.setId(2L);
        assertThat(billAudit1).isNotEqualTo(billAudit2);
        billAudit1.setId(null);
        assertThat(billAudit1).isNotEqualTo(billAudit2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BillAuditDTO.class);
        BillAuditDTO billAuditDTO1 = new BillAuditDTO();
        billAuditDTO1.setId(1L);
        BillAuditDTO billAuditDTO2 = new BillAuditDTO();
        assertThat(billAuditDTO1).isNotEqualTo(billAuditDTO2);
        billAuditDTO2.setId(billAuditDTO1.getId());
        assertThat(billAuditDTO1).isEqualTo(billAuditDTO2);
        billAuditDTO2.setId(2L);
        assertThat(billAuditDTO1).isNotEqualTo(billAuditDTO2);
        billAuditDTO1.setId(null);
        assertThat(billAuditDTO1).isNotEqualTo(billAuditDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(billAuditMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(billAuditMapper.fromId(null)).isNull();
    }
}
