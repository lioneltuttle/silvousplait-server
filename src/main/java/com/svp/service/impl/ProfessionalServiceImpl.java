package com.svp.service.impl;

import com.svp.service.ProfessionalService;
import com.svp.domain.Professional;
import com.svp.repository.ProfessionalRepository;
import com.svp.service.dto.ProfessionalDTO;
import com.svp.service.mapper.ProfessionalMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Professional}.
 */
@Service
@Transactional
public class ProfessionalServiceImpl implements ProfessionalService {

    private final Logger log = LoggerFactory.getLogger(ProfessionalServiceImpl.class);

    private final ProfessionalRepository professionalRepository;

    private final ProfessionalMapper professionalMapper;

    public ProfessionalServiceImpl(ProfessionalRepository professionalRepository, ProfessionalMapper professionalMapper) {
        this.professionalRepository = professionalRepository;
        this.professionalMapper = professionalMapper;
    }

    /**
     * Save a professional.
     *
     * @param professionalDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProfessionalDTO save(ProfessionalDTO professionalDTO) {
        log.debug("Request to save Professional : {}", professionalDTO);
        Professional professional = professionalMapper.toEntity(professionalDTO);
        professional = professionalRepository.save(professional);
        return professionalMapper.toDto(professional);
    }

    /**
     * Get all the professionals.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProfessionalDTO> findAll() {
        log.debug("Request to get all Professionals");
        return professionalRepository.findAll().stream()
            .map(professionalMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one professional by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProfessionalDTO> findOne(Long id) {
        log.debug("Request to get Professional : {}", id);
        return professionalRepository.findById(id)
            .map(professionalMapper::toDto);
    }

    /**
     * Delete the professional by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Professional : {}", id);
        professionalRepository.deleteById(id);
    }
}
