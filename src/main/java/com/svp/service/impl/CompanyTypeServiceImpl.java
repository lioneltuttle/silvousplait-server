package com.svp.service.impl;

import com.svp.domain.CompanyType;
import com.svp.repository.CompanyTypeRepository;
import com.svp.service.CompanyTypeService;
import com.svp.service.dto.CompanyTypeDTO;
import com.svp.service.mapper.CompanyTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link CompanyType}.
 */
@Service
@Transactional
public class CompanyTypeServiceImpl implements CompanyTypeService {

    private final Logger log = LoggerFactory.getLogger(CompanyTypeServiceImpl.class);

    private final CompanyTypeRepository companyTypeRepository;

    private final CompanyTypeMapper companyTypeMapper;

    public CompanyTypeServiceImpl(CompanyTypeRepository companyTypeRepository, CompanyTypeMapper companyTypeMapper) {
        this.companyTypeRepository = companyTypeRepository;
        this.companyTypeMapper = companyTypeMapper;
    }

    /**
     * Save a companyType.
     *
     * @param companyTypeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CompanyTypeDTO save(CompanyTypeDTO companyTypeDTO) {
        log.debug("Request to save CompanyType : {}", companyTypeDTO);
        CompanyType companyType = companyTypeMapper.toEntity(companyTypeDTO);
        companyType = companyTypeRepository.save(companyType);
        return companyTypeMapper.toDto(companyType);
    }

    /**
     * Get all the companyTypes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CompanyTypeDTO> findAll() {
        log.debug("Request to get all CompanyTypes");
        return companyTypeRepository.findAll().stream()
            .map(companyTypeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one companyType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CompanyTypeDTO> findOne(Long id) {
        log.debug("Request to get CompanyType : {}", id);
        return companyTypeRepository.findById(id)
            .map(companyTypeMapper::toDto);
    }

    /**
     * Delete the companyType by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CompanyType : {}", id);
        companyTypeRepository.deleteById(id);
    }
}
