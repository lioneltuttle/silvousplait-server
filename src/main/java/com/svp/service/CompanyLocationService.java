package com.svp.service;

import com.svp.service.dto.CompanyLocationDTO;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.svp.domain.CompanyLocation}.
 */
public interface CompanyLocationService {

    /**
     * Save a companyLocation.
     *
     * @param companyLocationDTO the entity to save.
     * @return the persisted entity.
     */
    CompanyLocationDTO save(CompanyLocationDTO companyLocationDTO);

    /**
     * Get all the companyLocations.
     *
     * @return the list of entities.
     */
    List<CompanyLocationDTO> findAll();


    /**
     * Get the "id" companyLocation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CompanyLocationDTO> findOne(Long id);

    /**
     * Delete the "id" companyLocation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<CompanyLocationDTO> findAllFromLocation(@PathVariable Double lat, @PathVariable Double lng);

    List<CompanyLocationDTO> findAllFromLocationAndType(@PathVariable Double lat, @PathVariable Double lng, long type);
}
