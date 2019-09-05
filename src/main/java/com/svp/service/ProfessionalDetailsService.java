package com.svp.service;

import com.svp.service.dto.ProfessionalDetailsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.svp.domain.ProfessionalDetails}.
 */
public interface ProfessionalDetailsService {

    /**
     * Save a professionalDetails.
     *
     * @param professionalDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    ProfessionalDetailsDTO save(ProfessionalDetailsDTO professionalDetailsDTO);

    /**
     * Get all the professionalDetails.
     *
     * @return the list of entities.
     */
    List<ProfessionalDetailsDTO> findAll();


    /**
     * Get the "id" professionalDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProfessionalDetailsDTO> findOne(Long id);

    /**
     * Delete the "id" professionalDetails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
