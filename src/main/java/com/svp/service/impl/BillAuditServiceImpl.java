package com.svp.service.impl;

import com.svp.service.BillAuditService;
import com.svp.domain.BillAudit;
import com.svp.repository.BillAuditRepository;
import com.svp.service.dto.BillAuditDTO;
import com.svp.service.mapper.BillAuditMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link BillAudit}.
 */
@Service
@Transactional
public class BillAuditServiceImpl implements BillAuditService {

    private final Logger log = LoggerFactory.getLogger(BillAuditServiceImpl.class);

    private final BillAuditRepository billAuditRepository;

    private final BillAuditMapper billAuditMapper;

    public BillAuditServiceImpl(BillAuditRepository billAuditRepository, BillAuditMapper billAuditMapper) {
        this.billAuditRepository = billAuditRepository;
        this.billAuditMapper = billAuditMapper;
    }

    /**
     * Save a billAudit.
     *
     * @param billAuditDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public BillAuditDTO save(BillAuditDTO billAuditDTO) {
        log.debug("Request to save BillAudit : {}", billAuditDTO);
        BillAudit billAudit = billAuditMapper.toEntity(billAuditDTO);
        billAudit = billAuditRepository.save(billAudit);
        return billAuditMapper.toDto(billAudit);
    }

    /**
     * Get all the billAudits.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<BillAuditDTO> findAll() {
        log.debug("Request to get all BillAudits");
        return billAuditRepository.findAll().stream()
            .map(billAuditMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one billAudit by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BillAuditDTO> findOne(Long id) {
        log.debug("Request to get BillAudit : {}", id);
        return billAuditRepository.findById(id)
            .map(billAuditMapper::toDto);
    }

    /**
     * Delete the billAudit by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BillAudit : {}", id);
        billAuditRepository.deleteById(id);
    }
}
