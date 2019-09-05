package com.svp.service;

import com.svp.service.dto.SummaryDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.svp.domain.Summary}.
 */
public interface SummaryService {

    /**
     * Save a summary.
     *
     * @param summaryDTO the entity to save.
     * @return the persisted entity.
     */
    SummaryDTO save(SummaryDTO summaryDTO);

    /**
     * Get all the summaries.
     *
     * @return the list of entities.
     */
    List<SummaryDTO> findAll();


    /**
     * Get the "id" summary.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SummaryDTO> findOne(Long id);

    /**
     * Delete the "id" summary.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
