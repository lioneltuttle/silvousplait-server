package com.svp.service.impl;

import com.svp.domain.ProChoice;
import com.svp.repository.ProChoiceRepository;
import com.svp.service.ProChoiceService;
import com.svp.service.dto.ProChoiceDTO;
import com.svp.service.mapper.ProChoiceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ProChoice}.
 */
@Service
@Transactional
public class ProChoiceServiceImpl implements ProChoiceService {

    private final Logger log = LoggerFactory.getLogger(ProChoiceServiceImpl.class);

    private final ProChoiceRepository proChoiceRepository;

    private final ProChoiceMapper proChoiceMapper;

    public ProChoiceServiceImpl(ProChoiceRepository proChoiceRepository, ProChoiceMapper proChoiceMapper) {
        this.proChoiceRepository = proChoiceRepository;
        this.proChoiceMapper = proChoiceMapper;
    }

    /**
     * Save a proChoice.
     *
     * @param proChoiceDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProChoiceDTO save(ProChoiceDTO proChoiceDTO) {
        log.debug("Request to save ProChoice : {}", proChoiceDTO);
        ProChoice proChoice = proChoiceMapper.toEntity(proChoiceDTO);
        proChoice = proChoiceRepository.save(proChoice);
        return proChoiceMapper.toDto(proChoice);
    }

    /**
     * Get all the proChoices.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProChoiceDTO> findAll() {
        log.debug("Request to get all ProChoices");
        return proChoiceRepository.findAll().stream()
            .map(proChoiceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one proChoice by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProChoiceDTO> findOne(Long id) {
        log.debug("Request to get ProChoice : {}", id);
        return proChoiceRepository.findById(id)
            .map(proChoiceMapper::toDto);
    }

    /**
     * Delete the proChoice by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProChoice : {}", id);
        proChoiceRepository.deleteById(id);
    }
}
