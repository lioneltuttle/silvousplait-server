package com.svp.service;

import com.svp.service.dto.BillAuditDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.svp.domain.BillAudit}.
 */
public interface BillAuditService {

    /**
     * Save a billAudit.
     *
     * @param billAuditDTO the entity to save.
     * @return the persisted entity.
     */
    BillAuditDTO save(BillAuditDTO billAuditDTO);

    /**
     * Get all the billAudits.
     *
     * @return the list of entities.
     */
    List<BillAuditDTO> findAll();


    /**
     * Get the "id" billAudit.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BillAuditDTO> findOne(Long id);

    /**
     * Delete the "id" billAudit.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
