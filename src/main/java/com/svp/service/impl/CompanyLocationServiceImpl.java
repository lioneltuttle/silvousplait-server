package com.svp.service.impl;

import com.svp.service.CompanyLocationService;
import com.svp.domain.CompanyLocation;
import com.svp.repository.CompanyLocationRepository;
import com.svp.service.dto.CompanyLocationDTO;
import com.svp.service.mapper.CompanyLocationMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link CompanyLocation}.
 */
@Service
@Transactional
@Slf4j
public class CompanyLocationServiceImpl implements CompanyLocationService {

    private final CompanyLocationRepository companyLocationRepository;

    private final CompanyLocationMapper companyLocationMapper;

    public CompanyLocationServiceImpl(CompanyLocationRepository companyLocationRepository, CompanyLocationMapper companyLocationMapper) {
        this.companyLocationRepository = companyLocationRepository;
        this.companyLocationMapper = companyLocationMapper;
    }

    /**
     * Save a companyLocation.
     *
     * @param companyLocationDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CompanyLocationDTO save(CompanyLocationDTO companyLocationDTO) {
        log.debug("Request to save CompanyLocation : {}", companyLocationDTO);
        CompanyLocation companyLocation = companyLocationMapper.toEntity(companyLocationDTO);
        companyLocation = companyLocationRepository.save(companyLocation);
        return companyLocationMapper.toDto(companyLocation);
    }

    /**
     * Get all the companyLocations.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CompanyLocationDTO> findAll() {
        log.debug("Request to get all CompanyLocations");
        return companyLocationRepository.findAll().stream()
            .map(companyLocationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one companyLocation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CompanyLocationDTO> findOne(Long id) {
        log.debug("Request to get CompanyLocation : {}", id);
        return companyLocationRepository.findById(id)
            .map(companyLocationMapper::toDto);
    }

    /**
     * Delete the companyLocation by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CompanyLocation : {}", id);
        companyLocationRepository.deleteById(id);
    }
}
