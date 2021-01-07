package com.svp.web.rest;

import com.svp.SilvousplaitApp;
import com.svp.domain.Hit;
import com.svp.repository.HitRepository;
import com.svp.service.HitService;
import com.svp.service.dto.HitDTO;
import com.svp.service.mapper.HitMapper;
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
 * Integration tests for the {@link HitResource} REST controller.
 */
@SpringBootTest(classes = SilvousplaitApp.class)
public class HitResourceIT {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE = LocalDate.ofEpochDay(-1L);

    private static final Boolean DEFAULT_ANSWERED = false;
    private static final Boolean UPDATED_ANSWERED = true;

    private static final Boolean DEFAULT_TRANSFORMED = false;
    private static final Boolean UPDATED_TRANSFORMED = true;

    @Autowired
    private HitRepository hitRepository;

    @Autowired
    private HitMapper hitMapper;

    @Autowired
    private HitService hitService;

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

    private MockMvc restHitMockMvc;

    private Hit hit;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HitResource hitResource = new HitResource(hitService);
        this.restHitMockMvc = MockMvcBuilders.standaloneSetup(hitResource)
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
    public static Hit createEntity(EntityManager em) {
        Hit hit = new Hit()
            .date(DEFAULT_DATE)
            .answered(DEFAULT_ANSWERED)
            .transformed(DEFAULT_TRANSFORMED);
        return hit;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hit createUpdatedEntity(EntityManager em) {
        Hit hit = new Hit()
            .date(UPDATED_DATE)
            .answered(UPDATED_ANSWERED)
            .transformed(UPDATED_TRANSFORMED);
        return hit;
    }

    @BeforeEach
    public void initTest() {
        hit = createEntity(em);
    }

    @Test
    @Transactional
    public void createHit() throws Exception {
        int databaseSizeBeforeCreate = hitRepository.findAll().size();

        // Create the Hit
        HitDTO hitDTO = hitMapper.toDto(hit);
        restHitMockMvc.perform(post("/api/hits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hitDTO)))
            .andExpect(status().isCreated());

        // Validate the Hit in the database
        List<Hit> hitList = hitRepository.findAll();
        assertThat(hitList).hasSize(databaseSizeBeforeCreate + 1);
        Hit testHit = hitList.get(hitList.size() - 1);
        assertThat(testHit.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testHit.isAnswered()).isEqualTo(DEFAULT_ANSWERED);
        assertThat(testHit.isTransformed()).isEqualTo(DEFAULT_TRANSFORMED);
    }

    @Test
    @Transactional
    public void createHitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hitRepository.findAll().size();

        // Create the Hit with an existing ID
        hit.setId(1L);
        HitDTO hitDTO = hitMapper.toDto(hit);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHitMockMvc.perform(post("/api/hits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Hit in the database
        List<Hit> hitList = hitRepository.findAll();
        assertThat(hitList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllHits() throws Exception {
        // Initialize the database
        hitRepository.saveAndFlush(hit);

        // Get all the hitList
        restHitMockMvc.perform(get("/api/hits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hit.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].answered").value(hasItem(DEFAULT_ANSWERED.booleanValue())))
            .andExpect(jsonPath("$.[*].transformed").value(hasItem(DEFAULT_TRANSFORMED.booleanValue())));
    }

    @Test
    @Transactional
    public void getHit() throws Exception {
        // Initialize the database
        hitRepository.saveAndFlush(hit);

        // Get the hit
        restHitMockMvc.perform(get("/api/hits/{id}", hit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(hit.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.answered").value(DEFAULT_ANSWERED.booleanValue()))
            .andExpect(jsonPath("$.transformed").value(DEFAULT_TRANSFORMED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingHit() throws Exception {
        // Get the hit
        restHitMockMvc.perform(get("/api/hits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHit() throws Exception {
        // Initialize the database
        hitRepository.saveAndFlush(hit);

        int databaseSizeBeforeUpdate = hitRepository.findAll().size();

        // Update the hit
        Hit updatedHit = hitRepository.findById(hit.getId()).get();
        // Disconnect from session so that the updates on updatedHit are not directly saved in db
        em.detach(updatedHit);
        updatedHit
            .date(UPDATED_DATE)
            .answered(UPDATED_ANSWERED)
            .transformed(UPDATED_TRANSFORMED);
        HitDTO hitDTO = hitMapper.toDto(updatedHit);

        restHitMockMvc.perform(put("/api/hits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hitDTO)))
            .andExpect(status().isOk());

        // Validate the Hit in the database
        List<Hit> hitList = hitRepository.findAll();
        assertThat(hitList).hasSize(databaseSizeBeforeUpdate);
        Hit testHit = hitList.get(hitList.size() - 1);
        assertThat(testHit.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testHit.isAnswered()).isEqualTo(UPDATED_ANSWERED);
        assertThat(testHit.isTransformed()).isEqualTo(UPDATED_TRANSFORMED);
    }

    @Test
    @Transactional
    public void updateNonExistingHit() throws Exception {
        int databaseSizeBeforeUpdate = hitRepository.findAll().size();

        // Create the Hit
        HitDTO hitDTO = hitMapper.toDto(hit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHitMockMvc.perform(put("/api/hits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Hit in the database
        List<Hit> hitList = hitRepository.findAll();
        assertThat(hitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHit() throws Exception {
        // Initialize the database
        hitRepository.saveAndFlush(hit);

        int databaseSizeBeforeDelete = hitRepository.findAll().size();

        // Delete the hit
        restHitMockMvc.perform(delete("/api/hits/{id}", hit.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Hit> hitList = hitRepository.findAll();
        assertThat(hitList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Hit.class);
        Hit hit1 = new Hit();
        hit1.setId(1L);
        Hit hit2 = new Hit();
        hit2.setId(hit1.getId());
        assertThat(hit1).isEqualTo(hit2);
        hit2.setId(2L);
        assertThat(hit1).isNotEqualTo(hit2);
        hit1.setId(null);
        assertThat(hit1).isNotEqualTo(hit2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HitDTO.class);
        HitDTO hitDTO1 = new HitDTO();
        hitDTO1.setId(1L);
        HitDTO hitDTO2 = new HitDTO();
        assertThat(hitDTO1).isNotEqualTo(hitDTO2);
        hitDTO2.setId(hitDTO1.getId());
        assertThat(hitDTO1).isEqualTo(hitDTO2);
        hitDTO2.setId(2L);
        assertThat(hitDTO1).isNotEqualTo(hitDTO2);
        hitDTO1.setId(null);
        assertThat(hitDTO1).isNotEqualTo(hitDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(hitMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(hitMapper.fromId(null)).isNull();
    }
}
