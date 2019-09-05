package com.svp.service;

import com.svp.service.dto.CompanyTypeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.svp.domain.CompanyType}.
 */
public interface CompanyTypeService {

    /**
     * Save a companyType.
     *
     * @param companyTypeDTO the entity to save.
     * @return the persisted entity.
     */
    CompanyTypeDTO save(CompanyTypeDTO companyTypeDTO);

    /**
     * Get all the companyTypes.
     *
     * @return the list of entities.
     */
    List<CompanyTypeDTO> findAll();


    /**
     * Get the "id" companyType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CompanyTypeDTO> findOne(Long id);

    /**
     * Delete the "id" companyType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
