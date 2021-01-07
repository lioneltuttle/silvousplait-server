package com.svp.service.impl;

import com.svp.domain.ProRequest;
import com.svp.repository.ProRequestRepository;
import com.svp.service.ProRequestService;
import com.svp.service.dto.ProRequestDTO;
import com.svp.service.mapper.ProRequestMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ProRequest}.
 */
@Service
@Transactional
public class ProRequestServiceImpl implements ProRequestService {

    private final Logger log = LoggerFactory.getLogger(ProRequestServiceImpl.class);

    private final ProRequestRepository proRequestRepository;

    private final ProRequestMapper proRequestMapper;

    public ProRequestServiceImpl(ProRequestRepository proRequestRepository, ProRequestMapper proRequestMapper) {
        this.proRequestRepository = proRequestRepository;
        this.proRequestMapper = proRequestMapper;
    }

    /**
     * Save a proRequest.
     *
     * @param proRequestDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProRequestDTO save(ProRequestDTO proRequestDTO) {
        log.debug("Request to save ProRequest : {}", proRequestDTO);
        ProRequest proRequest = proRequestMapper.toEntity(proRequestDTO);
        proRequest = proRequestRepository.save(proRequest);
        return proRequestMapper.toDto(proRequest);
    }

    /**
     * Get all the proRequests.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProRequestDTO> findAll() {
        log.debug("Request to get all ProRequests");
        return proRequestRepository.findAll().stream()
            .map(proRequestMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one proRequest by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProRequestDTO> findOne(Long id) {
        log.debug("Request to get ProRequest : {}", id);
        return proRequestRepository.findById(id)
            .map(proRequestMapper::toDto);
    }

    /**
     * Delete the proRequest by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProRequest : {}", id);
        proRequestRepository.deleteById(id);
    }
}
