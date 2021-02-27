package com.svp.service;

import com.svp.domain.Professional;
import com.svp.service.dto.HitDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.svp.domain.Hit}.
 */
public interface HitService {

    /**
     * Save a hit.
     *
     * @param hitDTO the entity to save.
     * @return the persisted entity.
     */
    HitDTO save(HitDTO hitDTO);

    /**
     * Get all the hits.
     *
     * @return the list of entities.
     */
    List<HitDTO> findAll();


    /**
     * Get the "id" hit.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<HitDTO> findOne(Long id);

    /**
     * Delete the "id" hit.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Returns all hits for One pro
     *
     * @param pro
     * @return
     */
    List<HitDTO> findAll(Professional pro);


    /**
     *
     *
     * @param proI
     * @param from
     * @param to
     * @return
     */
    List<HitDTO> findBetweenDatesForPro(Professional pro, LocalDate from, LocalDate to);


    /**
     * retourne tous les hits entre deux dates
     * @param from
     * @param to
     * @return
     */
    List<HitDTO> findAllBetweenDates( LocalDate from, LocalDate to);
}
