package com.svp.service.impl;

import com.svp.service.ProResponseService;
import com.svp.domain.ProResponse;
import com.svp.repository.ProResponseRepository;
import com.svp.service.dto.ProResponseDTO;
import com.svp.service.mapper.ProResponseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ProResponse}.
 */
@Service
@Transactional
public class ProResponseServiceImpl implements ProResponseService {

    private final Logger log = LoggerFactory.getLogger(ProResponseServiceImpl.class);

    private final ProResponseRepository proResponseRepository;

    private final ProResponseMapper proResponseMapper;

    public ProResponseServiceImpl(ProResponseRepository proResponseRepository, ProResponseMapper proResponseMapper) {
        this.proResponseRepository = proResponseRepository;
        this.proResponseMapper = proResponseMapper;
    }

    /**
     * Save a proResponse.
     *
     * @param proResponseDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProResponseDTO save(ProResponseDTO proResponseDTO) {
        log.debug("Request to save ProResponse : {}", proResponseDTO);
        ProResponse proResponse = proResponseMapper.toEntity(proResponseDTO);
        proResponse = proResponseRepository.save(proResponse);
        return proResponseMapper.toDto(proResponse);
    }

    /**
     * Get all the proResponses.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProResponseDTO> findAll() {
        log.debug("Request to get all ProResponses");
        return proResponseRepository.findAll().stream()
            .map(proResponseMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one proResponse by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProResponseDTO> findOne(Long id) {
        log.debug("Request to get ProResponse : {}", id);
        return proResponseRepository.findById(id)
            .map(proResponseMapper::toDto);
    }

    /**
     * Delete the proResponse by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProResponse : {}", id);
        proResponseRepository.deleteById(id);
    }
}
