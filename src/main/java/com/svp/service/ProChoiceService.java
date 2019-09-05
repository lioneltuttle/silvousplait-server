package com.svp.service;

import com.svp.service.dto.ProChoiceDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.svp.domain.ProChoice}.
 */
public interface ProChoiceService {

    /**
     * Save a proChoice.
     *
     * @param proChoiceDTO the entity to save.
     * @return the persisted entity.
     */
    ProChoiceDTO save(ProChoiceDTO proChoiceDTO);

    /**
     * Get all the proChoices.
     *
     * @return the list of entities.
     */
    List<ProChoiceDTO> findAll();


    /**
     * Get the "id" proChoice.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProChoiceDTO> findOne(Long id);

    /**
     * Delete the "id" proChoice.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
