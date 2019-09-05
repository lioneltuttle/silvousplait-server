package com.svp.service;

import com.svp.service.dto.BillDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.svp.domain.Bill}.
 */
public interface BillService {

    /**
     * Save a bill.
     *
     * @param billDTO the entity to save.
     * @return the persisted entity.
     */
    BillDTO save(BillDTO billDTO);

    /**
     * Get all the bills.
     *
     * @return the list of entities.
     */
    List<BillDTO> findAll();


    /**
     * Get the "id" bill.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BillDTO> findOne(Long id);

    /**
     * Delete the "id" bill.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
