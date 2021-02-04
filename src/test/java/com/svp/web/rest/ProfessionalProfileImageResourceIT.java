package com.svp.web.rest;

import com.svp.SilvousplaitApp;
import com.svp.domain.ProfessionalProfileImage;
import com.svp.repository.ProfessionalProfileImageRepository;
import com.svp.service.ProfessionalProfileImageService;
import com.svp.service.dto.ProfessionalProfileImageDTO;
import com.svp.service.mapper.ProfessionalProfileImageMapper;
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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.svp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ProfessionalProfileImageResource} REST controller.
 */
@SpringBootTest(classes = SilvousplaitApp.class)
public class ProfessionalProfileImageResourceIT {

    private static final Long DEFAULT_PRO_ID = 1L;
    private static final Long UPDATED_PRO_ID = 2L;
    private static final Long SMALLER_PRO_ID = 1L - 1L;

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    @Autowired
    private ProfessionalProfileImageRepository professionalProfileImageRepository;

    @Autowired
    private ProfessionalProfileImageMapper professionalProfileImageMapper;

    @Autowired
    private ProfessionalProfileImageService professionalProfileImageService;

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

    private MockMvc restProfessionalProfileImageMockMvc;

    private ProfessionalProfileImage professionalProfileImage;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProfessionalProfileImageResource professionalProfileImageResource = new ProfessionalProfileImageResource(professionalProfileImageService);
        this.restProfessionalProfileImageMockMvc = MockMvcBuilders.standaloneSetup(professionalProfileImageResource)
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
    public static ProfessionalProfileImage createEntity(EntityManager em) {
        ProfessionalProfileImage professionalProfileImage = new ProfessionalProfileImage()
            .proId(DEFAULT_PRO_ID)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE);
        return professionalProfileImage;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProfessionalProfileImage createUpdatedEntity(EntityManager em) {
        ProfessionalProfileImage professionalProfileImage = new ProfessionalProfileImage()
            .proId(UPDATED_PRO_ID)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        return professionalProfileImage;
    }

    @BeforeEach
    public void initTest() {
        professionalProfileImage = createEntity(em);
    }

    @Test
    @Transactional
    public void createProfessionalProfileImage() throws Exception {
        int databaseSizeBeforeCreate = professionalProfileImageRepository.findAll().size();

        // Create the ProfessionalProfileImage
        ProfessionalProfileImageDTO professionalProfileImageDTO = professionalProfileImageMapper.toDto(professionalProfileImage);
        restProfessionalProfileImageMockMvc.perform(post("/api/professional-profile-images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(professionalProfileImageDTO)))
            .andExpect(status().isCreated());

        // Validate the ProfessionalProfileImage in the database
        List<ProfessionalProfileImage> professionalProfileImageList = professionalProfileImageRepository.findAll();
        assertThat(professionalProfileImageList).hasSize(databaseSizeBeforeCreate + 1);
        ProfessionalProfileImage testProfessionalProfileImage = professionalProfileImageList.get(professionalProfileImageList.size() - 1);
        assertThat(testProfessionalProfileImage.getProId()).isEqualTo(DEFAULT_PRO_ID);
        assertThat(testProfessionalProfileImage.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testProfessionalProfileImage.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createProfessionalProfileImageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = professionalProfileImageRepository.findAll().size();

        // Create the ProfessionalProfileImage with an existing ID
        professionalProfileImage.setId(1L);
        ProfessionalProfileImageDTO professionalProfileImageDTO = professionalProfileImageMapper.toDto(professionalProfileImage);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfessionalProfileImageMockMvc.perform(post("/api/professional-profile-images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(professionalProfileImageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProfessionalProfileImage in the database
        List<ProfessionalProfileImage> professionalProfileImageList = professionalProfileImageRepository.findAll();
        assertThat(professionalProfileImageList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProfessionalProfileImages() throws Exception {
        // Initialize the database
        professionalProfileImageRepository.saveAndFlush(professionalProfileImage);

        // Get all the professionalProfileImageList
        restProfessionalProfileImageMockMvc.perform(get("/api/professional-profile-images?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(professionalProfileImage.getId().intValue())))
            .andExpect(jsonPath("$.[*].proId").value(hasItem(DEFAULT_PRO_ID.intValue())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));
    }
    
    @Test
    @Transactional
    public void getProfessionalProfileImage() throws Exception {
        // Initialize the database
        professionalProfileImageRepository.saveAndFlush(professionalProfileImage);

        // Get the professionalProfileImage
        restProfessionalProfileImageMockMvc.perform(get("/api/professional-profile-images/{id}", professionalProfileImage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(professionalProfileImage.getId().intValue()))
            .andExpect(jsonPath("$.proId").value(DEFAULT_PRO_ID.intValue()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)));
    }

    @Test
    @Transactional
    public void getNonExistingProfessionalProfileImage() throws Exception {
        // Get the professionalProfileImage
        restProfessionalProfileImageMockMvc.perform(get("/api/professional-profile-images/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProfessionalProfileImage() throws Exception {
        // Initialize the database
        professionalProfileImageRepository.saveAndFlush(professionalProfileImage);

        int databaseSizeBeforeUpdate = professionalProfileImageRepository.findAll().size();

        // Update the professionalProfileImage
        ProfessionalProfileImage updatedProfessionalProfileImage = professionalProfileImageRepository.findById(professionalProfileImage.getId()).get();
        // Disconnect from session so that the updates on updatedProfessionalProfileImage are not directly saved in db
        em.detach(updatedProfessionalProfileImage);
        updatedProfessionalProfileImage
            .proId(UPDATED_PRO_ID)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        ProfessionalProfileImageDTO professionalProfileImageDTO = professionalProfileImageMapper.toDto(updatedProfessionalProfileImage);

        restProfessionalProfileImageMockMvc.perform(put("/api/professional-profile-images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(professionalProfileImageDTO)))
            .andExpect(status().isOk());

        // Validate the ProfessionalProfileImage in the database
        List<ProfessionalProfileImage> professionalProfileImageList = professionalProfileImageRepository.findAll();
        assertThat(professionalProfileImageList).hasSize(databaseSizeBeforeUpdate);
        ProfessionalProfileImage testProfessionalProfileImage = professionalProfileImageList.get(professionalProfileImageList.size() - 1);
        assertThat(testProfessionalProfileImage.getProId()).isEqualTo(UPDATED_PRO_ID);
        assertThat(testProfessionalProfileImage.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testProfessionalProfileImage.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingProfessionalProfileImage() throws Exception {
        int databaseSizeBeforeUpdate = professionalProfileImageRepository.findAll().size();

        // Create the ProfessionalProfileImage
        ProfessionalProfileImageDTO professionalProfileImageDTO = professionalProfileImageMapper.toDto(professionalProfileImage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfessionalProfileImageMockMvc.perform(put("/api/professional-profile-images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(professionalProfileImageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProfessionalProfileImage in the database
        List<ProfessionalProfileImage> professionalProfileImageList = professionalProfileImageRepository.findAll();
        assertThat(professionalProfileImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProfessionalProfileImage() throws Exception {
        // Initialize the database
        professionalProfileImageRepository.saveAndFlush(professionalProfileImage);

        int databaseSizeBeforeDelete = professionalProfileImageRepository.findAll().size();

        // Delete the professionalProfileImage
        restProfessionalProfileImageMockMvc.perform(delete("/api/professional-profile-images/{id}", professionalProfileImage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProfessionalProfileImage> professionalProfileImageList = professionalProfileImageRepository.findAll();
        assertThat(professionalProfileImageList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProfessionalProfileImage.class);
        ProfessionalProfileImage professionalProfileImage1 = new ProfessionalProfileImage();
        professionalProfileImage1.setId(1L);
        ProfessionalProfileImage professionalProfileImage2 = new ProfessionalProfileImage();
        professionalProfileImage2.setId(professionalProfileImage1.getId());
        assertThat(professionalProfileImage1).isEqualTo(professionalProfileImage2);
        professionalProfileImage2.setId(2L);
        assertThat(professionalProfileImage1).isNotEqualTo(professionalProfileImage2);
        professionalProfileImage1.setId(null);
        assertThat(professionalProfileImage1).isNotEqualTo(professionalProfileImage2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProfessionalProfileImageDTO.class);
        ProfessionalProfileImageDTO professionalProfileImageDTO1 = new ProfessionalProfileImageDTO();
        professionalProfileImageDTO1.setId(1L);
        ProfessionalProfileImageDTO professionalProfileImageDTO2 = new ProfessionalProfileImageDTO();
        assertThat(professionalProfileImageDTO1).isNotEqualTo(professionalProfileImageDTO2);
        professionalProfileImageDTO2.setId(professionalProfileImageDTO1.getId());
        assertThat(professionalProfileImageDTO1).isEqualTo(professionalProfileImageDTO2);
        professionalProfileImageDTO2.setId(2L);
        assertThat(professionalProfileImageDTO1).isNotEqualTo(professionalProfileImageDTO2);
        professionalProfileImageDTO1.setId(null);
        assertThat(professionalProfileImageDTO1).isNotEqualTo(professionalProfileImageDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(professionalProfileImageMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(professionalProfileImageMapper.fromId(null)).isNull();
    }
}
