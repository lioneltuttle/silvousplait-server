package com.svp.service;

import com.svp.service.dto.ProResponseDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.svp.domain.ProResponse}.
 */
public interface ProResponseService {

    /**
     * Save a proResponse.
     *
     * @param proResponseDTO the entity to save.
     * @return the persisted entity.
     */
    ProResponseDTO save(ProResponseDTO proResponseDTO);

    /**
     * Get all the proResponses.
     *
     * @return the list of entities.
     */
    List<ProResponseDTO> findAll();


    /**
     * Get the "id" proResponse.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProResponseDTO> findOne(Long id);

    /**
     * Delete the "id" proResponse.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
