package com.svp.service.impl;

import com.svp.service.ProfessionalAuditService;
import com.svp.domain.ProfessionalAudit;
import com.svp.repository.ProfessionalAuditRepository;
import com.svp.service.dto.ProfessionalAuditDTO;
import com.svp.service.mapper.ProfessionalAuditMapper;
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
 * Service Implementation for managing {@link ProfessionalAudit}.
 */
@Service
@Transactional
@Slf4j
public class ProfessionalAuditServiceImpl implements ProfessionalAuditService {

    private final ProfessionalAuditRepository professionalAuditRepository;

    private final ProfessionalAuditMapper professionalAuditMapper;

    public ProfessionalAuditServiceImpl(ProfessionalAuditRepository professionalAuditRepository, ProfessionalAuditMapper professionalAuditMapper) {
        this.professionalAuditRepository = professionalAuditRepository;
        this.professionalAuditMapper = professionalAuditMapper;
    }

    /**
     * Save a professionalAudit.
     *
     * @param professionalAuditDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProfessionalAuditDTO save(ProfessionalAuditDTO professionalAuditDTO) {
        log.debug("Request to save ProfessionalAudit : {}", professionalAuditDTO);
        ProfessionalAudit professionalAudit = professionalAuditMapper.toEntity(professionalAuditDTO);
        professionalAudit = professionalAuditRepository.save(professionalAudit);
        return professionalAuditMapper.toDto(professionalAudit);
    }

    /**
     * Get all the professionalAudits.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProfessionalAuditDTO> findAll() {
        log.debug("Request to get all ProfessionalAudits");
        return professionalAuditRepository.findAll().stream()
            .map(professionalAuditMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one professionalAudit by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProfessionalAuditDTO> findOne(Long id) {
        log.debug("Request to get ProfessionalAudit : {}", id);
        return professionalAuditRepository.findById(id)
            .map(professionalAuditMapper::toDto);
    }

    /**
     * Delete the professionalAudit by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProfessionalAudit : {}", id);
        professionalAuditRepository.deleteById(id);
    }
}
