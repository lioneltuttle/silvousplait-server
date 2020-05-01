package com.svp.service.impl;

import com.svp.service.SubscriptionTypeService;
import com.svp.domain.SubscriptionType;
import com.svp.repository.SubscriptionTypeRepository;
import com.svp.service.dto.SubscriptionTypeDTO;
import com.svp.service.mapper.SubscriptionTypeMapper;
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
 * Service Implementation for managing {@link SubscriptionType}.
 */
@Service
@Transactional
@Slf4j
public class SubscriptionTypeServiceImpl implements SubscriptionTypeService {

    private final SubscriptionTypeRepository subscriptionTypeRepository;

    private final SubscriptionTypeMapper subscriptionTypeMapper;

    public SubscriptionTypeServiceImpl(SubscriptionTypeRepository subscriptionTypeRepository, SubscriptionTypeMapper subscriptionTypeMapper) {
        this.subscriptionTypeRepository = subscriptionTypeRepository;
        this.subscriptionTypeMapper = subscriptionTypeMapper;
    }

    /**
     * Save a subscriptionType.
     *
     * @param subscriptionTypeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SubscriptionTypeDTO save(SubscriptionTypeDTO subscriptionTypeDTO) {
        log.debug("Request to save SubscriptionType : {}", subscriptionTypeDTO);
        SubscriptionType subscriptionType = subscriptionTypeMapper.toEntity(subscriptionTypeDTO);
        subscriptionType = subscriptionTypeRepository.save(subscriptionType);
        return subscriptionTypeMapper.toDto(subscriptionType);
    }

    /**
     * Get all the subscriptionTypes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<SubscriptionTypeDTO> findAll() {
        log.debug("Request to get all SubscriptionTypes");
        return subscriptionTypeRepository.findAll().stream()
            .map(subscriptionTypeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one subscriptionType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SubscriptionTypeDTO> findOne(Long id) {
        log.debug("Request to get SubscriptionType : {}", id);
        return subscriptionTypeRepository.findById(id)
            .map(subscriptionTypeMapper::toDto);
    }

    /**
     * Delete the subscriptionType by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SubscriptionType : {}", id);
        subscriptionTypeRepository.deleteById(id);
    }
}
