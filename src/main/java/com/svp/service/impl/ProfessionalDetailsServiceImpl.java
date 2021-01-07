package com.svp.service.impl;

import com.svp.domain.ProfessionalDetails;
import com.svp.repository.ProfessionalDetailsRepository;
import com.svp.service.ProfessionalDetailsService;
import com.svp.service.dto.ProfessionalDetailsDTO;
import com.svp.service.mapper.ProfessionalDetailsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ProfessionalDetails}.
 */
@Service
@Transactional
public class ProfessionalDetailsServiceImpl implements ProfessionalDetailsService {

    private final Logger log = LoggerFactory.getLogger(ProfessionalDetailsServiceImpl.class);

    private final ProfessionalDetailsRepository professionalDetailsRepository;

    private final ProfessionalDetailsMapper professionalDetailsMapper;

    public ProfessionalDetailsServiceImpl(ProfessionalDetailsRepository professionalDetailsRepository, ProfessionalDetailsMapper professionalDetailsMapper) {
        this.professionalDetailsRepository = professionalDetailsRepository;
        this.professionalDetailsMapper = professionalDetailsMapper;
    }

    /**
     * Save a professionalDetails.
     *
     * @param professionalDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProfessionalDetailsDTO save(ProfessionalDetailsDTO professionalDetailsDTO) {
        log.debug("Request to save ProfessionalDetails : {}", professionalDetailsDTO);
        ProfessionalDetails professionalDetails = professionalDetailsMapper.toEntity(professionalDetailsDTO);
        professionalDetails = professionalDetailsRepository.save(professionalDetails);
        return professionalDetailsMapper.toDto(professionalDetails);
    }

    /**
     * Get all the professionalDetails.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProfessionalDetailsDTO> findAll() {
        log.debug("Request to get all ProfessionalDetails");
        return professionalDetailsRepository.findAll().stream()
            .map(professionalDetailsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one professionalDetails by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProfessionalDetailsDTO> findOne(Long id) {
        log.debug("Request to get ProfessionalDetails : {}", id);
        return professionalDetailsRepository.findById(id)
            .map(professionalDetailsMapper::toDto);
    }

    /**
     * Delete the professionalDetails by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProfessionalDetails : {}", id);
        professionalDetailsRepository.deleteById(id);
    }
}
