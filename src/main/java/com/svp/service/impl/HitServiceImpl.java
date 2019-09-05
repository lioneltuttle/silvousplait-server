package com.svp.service.impl;

import com.svp.service.HitService;
import com.svp.domain.Hit;
import com.svp.repository.HitRepository;
import com.svp.service.dto.HitDTO;
import com.svp.service.mapper.HitMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Hit}.
 */
@Service
@Transactional
public class HitServiceImpl implements HitService {

    private final Logger log = LoggerFactory.getLogger(HitServiceImpl.class);

    private final HitRepository hitRepository;

    private final HitMapper hitMapper;

    public HitServiceImpl(HitRepository hitRepository, HitMapper hitMapper) {
        this.hitRepository = hitRepository;
        this.hitMapper = hitMapper;
    }

    /**
     * Save a hit.
     *
     * @param hitDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public HitDTO save(HitDTO hitDTO) {
        log.debug("Request to save Hit : {}", hitDTO);
        Hit hit = hitMapper.toEntity(hitDTO);
        hit = hitRepository.save(hit);
        return hitMapper.toDto(hit);
    }

    /**
     * Get all the hits.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<HitDTO> findAll() {
        log.debug("Request to get all Hits");
        return hitRepository.findAll().stream()
            .map(hitMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one hit by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<HitDTO> findOne(Long id) {
        log.debug("Request to get Hit : {}", id);
        return hitRepository.findById(id)
            .map(hitMapper::toDto);
    }

    /**
     * Delete the hit by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Hit : {}", id);
        hitRepository.deleteById(id);
    }
}
