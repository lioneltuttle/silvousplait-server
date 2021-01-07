package com.svp.web.rest;

import com.svp.SilvousplaitApp;
import com.svp.domain.Summary;
import com.svp.repository.SummaryRepository;
import com.svp.service.SummaryService;
import com.svp.service.dto.SummaryDTO;
import com.svp.service.mapper.SummaryMapper;
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
 * Integration tests for the {@link SummaryResource} REST controller.
 */
@SpringBootTest(classes = SilvousplaitApp.class)
public class SummaryResourceIT {

    private static final Integer DEFAULT_HITS = 1;
    private static final Integer UPDATED_HITS = 2;
    private static final Integer SMALLER_HITS = 1 - 1;

    private static final Integer DEFAULT_MISSED = 1;
    private static final Integer UPDATED_MISSED = 2;
    private static final Integer SMALLER_MISSED = 1 - 1;

    private static final Double DEFAULT_CURRENT_BILL = 1D;
    private static final Double UPDATED_CURRENT_BILL = 2D;
    private static final Double SMALLER_CURRENT_BILL = 1D - 1D;

    private static final Double DEFAULT_RATING = 1D;
    private static final Double UPDATED_RATING = 2D;
    private static final Double SMALLER_RATING = 1D - 1D;

    @Autowired
    private SummaryRepository summaryRepository;

    @Autowired
    private SummaryMapper summaryMapper;

    @Autowired
    private SummaryService summaryService;

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

    private MockMvc restSummaryMockMvc;

    private Summary summary;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SummaryResource summaryResource = new SummaryResource(summaryService);
        this.restSummaryMockMvc = MockMvcBuilders.standaloneSetup(summaryResource)
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
    public static Summary createEntity(EntityManager em) {
        Summary summary = new Summary()
            .hits(DEFAULT_HITS)
            .missed(DEFAULT_MISSED)
            .currentBill(DEFAULT_CURRENT_BILL)
            .rating(DEFAULT_RATING);
        return summary;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Summary createUpdatedEntity(EntityManager em) {
        Summary summary = new Summary()
            .hits(UPDATED_HITS)
            .missed(UPDATED_MISSED)
            .currentBill(UPDATED_CURRENT_BILL)
            .rating(UPDATED_RATING);
        return summary;
    }

    @BeforeEach
    public void initTest() {
        summary = createEntity(em);
    }

    @Test
    @Transactional
    public void createSummary() throws Exception {
        int databaseSizeBeforeCreate = summaryRepository.findAll().size();

        // Create the Summary
        SummaryDTO summaryDTO = summaryMapper.toDto(summary);
        restSummaryMockMvc.perform(post("/api/summaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(summaryDTO)))
            .andExpect(status().isCreated());

        // Validate the Summary in the database
        List<Summary> summaryList = summaryRepository.findAll();
        assertThat(summaryList).hasSize(databaseSizeBeforeCreate + 1);
        Summary testSummary = summaryList.get(summaryList.size() - 1);
        assertThat(testSummary.getHits()).isEqualTo(DEFAULT_HITS);
        assertThat(testSummary.getMissed()).isEqualTo(DEFAULT_MISSED);
        assertThat(testSummary.getCurrentBill()).isEqualTo(DEFAULT_CURRENT_BILL);
        assertThat(testSummary.getRating()).isEqualTo(DEFAULT_RATING);
    }

    @Test
    @Transactional
    public void createSummaryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = summaryRepository.findAll().size();

        // Create the Summary with an existing ID
        summary.setId(1L);
        SummaryDTO summaryDTO = summaryMapper.toDto(summary);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSummaryMockMvc.perform(post("/api/summaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(summaryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Summary in the database
        List<Summary> summaryList = summaryRepository.findAll();
        assertThat(summaryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSummaries() throws Exception {
        // Initialize the database
        summaryRepository.saveAndFlush(summary);

        // Get all the summaryList
        restSummaryMockMvc.perform(get("/api/summaries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(summary.getId().intValue())))
            .andExpect(jsonPath("$.[*].hits").value(hasItem(DEFAULT_HITS)))
            .andExpect(jsonPath("$.[*].missed").value(hasItem(DEFAULT_MISSED)))
            .andExpect(jsonPath("$.[*].currentBill").value(hasItem(DEFAULT_CURRENT_BILL.doubleValue())))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getSummary() throws Exception {
        // Initialize the database
        summaryRepository.saveAndFlush(summary);

        // Get the summary
        restSummaryMockMvc.perform(get("/api/summaries/{id}", summary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(summary.getId().intValue()))
            .andExpect(jsonPath("$.hits").value(DEFAULT_HITS))
            .andExpect(jsonPath("$.missed").value(DEFAULT_MISSED))
            .andExpect(jsonPath("$.currentBill").value(DEFAULT_CURRENT_BILL.doubleValue()))
            .andExpect(jsonPath("$.rating").value(DEFAULT_RATING.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSummary() throws Exception {
        // Get the summary
        restSummaryMockMvc.perform(get("/api/summaries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSummary() throws Exception {
        // Initialize the database
        summaryRepository.saveAndFlush(summary);

        int databaseSizeBeforeUpdate = summaryRepository.findAll().size();

        // Update the summary
        Summary updatedSummary = summaryRepository.findById(summary.getId()).get();
        // Disconnect from session so that the updates on updatedSummary are not directly saved in db
        em.detach(updatedSummary);
        updatedSummary
            .hits(UPDATED_HITS)
            .missed(UPDATED_MISSED)
            .currentBill(UPDATED_CURRENT_BILL)
            .rating(UPDATED_RATING);
        SummaryDTO summaryDTO = summaryMapper.toDto(updatedSummary);

        restSummaryMockMvc.perform(put("/api/summaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(summaryDTO)))
            .andExpect(status().isOk());

        // Validate the Summary in the database
        List<Summary> summaryList = summaryRepository.findAll();
        assertThat(summaryList).hasSize(databaseSizeBeforeUpdate);
        Summary testSummary = summaryList.get(summaryList.size() - 1);
        assertThat(testSummary.getHits()).isEqualTo(UPDATED_HITS);
        assertThat(testSummary.getMissed()).isEqualTo(UPDATED_MISSED);
        assertThat(testSummary.getCurrentBill()).isEqualTo(UPDATED_CURRENT_BILL);
        assertThat(testSummary.getRating()).isEqualTo(UPDATED_RATING);
    }

    @Test
    @Transactional
    public void updateNonExistingSummary() throws Exception {
        int databaseSizeBeforeUpdate = summaryRepository.findAll().size();

        // Create the Summary
        SummaryDTO summaryDTO = summaryMapper.toDto(summary);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSummaryMockMvc.perform(put("/api/summaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(summaryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Summary in the database
        List<Summary> summaryList = summaryRepository.findAll();
        assertThat(summaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSummary() throws Exception {
        // Initialize the database
        summaryRepository.saveAndFlush(summary);

        int databaseSizeBeforeDelete = summaryRepository.findAll().size();

        // Delete the summary
        restSummaryMockMvc.perform(delete("/api/summaries/{id}", summary.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Summary> summaryList = summaryRepository.findAll();
        assertThat(summaryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Summary.class);
        Summary summary1 = new Summary();
        summary1.setId(1L);
        Summary summary2 = new Summary();
        summary2.setId(summary1.getId());
        assertThat(summary1).isEqualTo(summary2);
        summary2.setId(2L);
        assertThat(summary1).isNotEqualTo(summary2);
        summary1.setId(null);
        assertThat(summary1).isNotEqualTo(summary2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SummaryDTO.class);
        SummaryDTO summaryDTO1 = new SummaryDTO();
        summaryDTO1.setId(1L);
        SummaryDTO summaryDTO2 = new SummaryDTO();
        assertThat(summaryDTO1).isNotEqualTo(summaryDTO2);
        summaryDTO2.setId(summaryDTO1.getId());
        assertThat(summaryDTO1).isEqualTo(summaryDTO2);
        summaryDTO2.setId(2L);
        assertThat(summaryDTO1).isNotEqualTo(summaryDTO2);
        summaryDTO1.setId(null);
        assertThat(summaryDTO1).isNotEqualTo(summaryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(summaryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(summaryMapper.fromId(null)).isNull();
    }
}
