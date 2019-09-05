package com.svp.service;

import com.svp.service.dto.ProfessionalAuditDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.svp.domain.ProfessionalAudit}.
 */
public interface ProfessionalAuditService {

    /**
     * Save a professionalAudit.
     *
     * @param professionalAuditDTO the entity to save.
     * @return the persisted entity.
     */
    ProfessionalAuditDTO save(ProfessionalAuditDTO professionalAuditDTO);

    /**
     * Get all the professionalAudits.
     *
     * @return the list of entities.
     */
    List<ProfessionalAuditDTO> findAll();


    /**
     * Get the "id" professionalAudit.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProfessionalAuditDTO> findOne(Long id);

    /**
     * Delete the "id" professionalAudit.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
