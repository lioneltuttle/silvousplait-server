package com.svp.service;

import com.svp.service.dto.ProfessionalProfileImageDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.svp.domain.ProfessionalProfileImage}.
 */
public interface ProfessionalProfileImageService {

    /**
     * Save a professionalProfileImage.
     *
     * @param professionalProfileImageDTO the entity to save.
     * @return the persisted entity.
     */
    ProfessionalProfileImageDTO save(ProfessionalProfileImageDTO professionalProfileImageDTO);

    /**
     * Get all the professionalProfileImages.
     *
     * @return the list of entities.
     */
    List<ProfessionalProfileImageDTO> findAll();


    /**
     * Get the "id" professionalProfileImage.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProfessionalProfileImageDTO> findOneByProId(Long id);

    /**
     * Delete the "id" professionalProfileImage.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
