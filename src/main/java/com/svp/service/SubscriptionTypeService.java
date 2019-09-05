package com.svp.service;

import com.svp.service.dto.SubscriptionTypeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.svp.domain.SubscriptionType}.
 */
public interface SubscriptionTypeService {

    /**
     * Save a subscriptionType.
     *
     * @param subscriptionTypeDTO the entity to save.
     * @return the persisted entity.
     */
    SubscriptionTypeDTO save(SubscriptionTypeDTO subscriptionTypeDTO);

    /**
     * Get all the subscriptionTypes.
     *
     * @return the list of entities.
     */
    List<SubscriptionTypeDTO> findAll();


    /**
     * Get the "id" subscriptionType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SubscriptionTypeDTO> findOne(Long id);

    /**
     * Delete the "id" subscriptionType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
