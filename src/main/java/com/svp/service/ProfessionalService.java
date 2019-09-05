package com.svp.service;

import com.svp.service.dto.ProfessionalDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.svp.domain.Professional}.
 */
public interface ProfessionalService {

    /**
     * Save a professional.
     *
     * @param professionalDTO the entity to save.
     * @return the persisted entity.
     */
    ProfessionalDTO save(ProfessionalDTO professionalDTO);

    /**
     * Get all the professionals.
     *
     * @return the list of entities.
     */
    List<ProfessionalDTO> findAll();


    /**
     * Get the "id" professional.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProfessionalDTO> findOne(Long id);

    /**
     * Delete the "id" professional.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
