package com.svp.service;

import com.svp.service.dto.ProRequestDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.svp.domain.ProRequest}.
 */
public interface ProRequestService {

    /**
     * Save a proRequest.
     *
     * @param proRequestDTO the entity to save.
     * @return the persisted entity.
     */
    ProRequestDTO save(ProRequestDTO proRequestDTO);

    /**
     * Get all the proRequests.
     *
     * @return the list of entities.
     */
    List<ProRequestDTO> findAll();


    /**
     * Get the "id" proRequest.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProRequestDTO> findOne(Long id);

    /**
     * Delete the "id" proRequest.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
