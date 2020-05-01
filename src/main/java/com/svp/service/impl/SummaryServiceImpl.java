package com.svp.service.impl;

import com.svp.service.SummaryService;
import com.svp.domain.Summary;
import com.svp.repository.SummaryRepository;
import com.svp.service.dto.SummaryDTO;
import com.svp.service.mapper.SummaryMapper;
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
 * Service Implementation for managing {@link Summary}.
 */
@Service
@Transactional
@Slf4j
public class SummaryServiceImpl implements SummaryService {

    private final SummaryRepository summaryRepository;

    private final SummaryMapper summaryMapper;

    public SummaryServiceImpl(SummaryRepository summaryRepository, SummaryMapper summaryMapper) {
        this.summaryRepository = summaryRepository;
        this.summaryMapper = summaryMapper;
    }

    /**
     * Save a summary.
     *
     * @param summaryDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SummaryDTO save(SummaryDTO summaryDTO) {
        log.debug("Request to save Summary : {}", summaryDTO);
        Summary summary = summaryMapper.toEntity(summaryDTO);
        summary = summaryRepository.save(summary);
        return summaryMapper.toDto(summary);
    }

    /**
     * Get all the summaries.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<SummaryDTO> findAll() {
        log.debug("Request to get all Summaries");
        return summaryRepository.findAll().stream()
            .map(summaryMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one summary by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SummaryDTO> findOne(Long id) {
        log.debug("Request to get Summary : {}", id);
        return summaryRepository.findById(id)
            .map(summaryMapper::toDto);
    }

    /**
     * Delete the summary by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Summary : {}", id);
        summaryRepository.deleteById(id);
    }
}
