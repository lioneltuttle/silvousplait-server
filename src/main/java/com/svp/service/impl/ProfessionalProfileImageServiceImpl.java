package com.svp.service.impl;

import com.svp.service.ProfessionalProfileImageService;
import com.svp.domain.ProfessionalProfileImage;
import com.svp.repository.ProfessionalProfileImageRepository;
import com.svp.service.dto.ProfessionalProfileImageDTO;
import com.svp.service.mapper.ProfessionalProfileImageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ProfessionalProfileImage}.
 */
@Service
@Transactional
public class ProfessionalProfileImageServiceImpl implements ProfessionalProfileImageService {

    private final Logger log = LoggerFactory.getLogger(ProfessionalProfileImageServiceImpl.class);

    private final ProfessionalProfileImageRepository professionalProfileImageRepository;

    private final ProfessionalProfileImageMapper professionalProfileImageMapper;

    public ProfessionalProfileImageServiceImpl(ProfessionalProfileImageRepository professionalProfileImageRepository, ProfessionalProfileImageMapper professionalProfileImageMapper) {
        this.professionalProfileImageRepository = professionalProfileImageRepository;
        this.professionalProfileImageMapper = professionalProfileImageMapper;
    }

    /**
     * Save a professionalProfileImage.
     *
     * @param professionalProfileImageDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProfessionalProfileImageDTO save(ProfessionalProfileImageDTO professionalProfileImageDTO) {
        log.debug("Request to save ProfessionalProfileImage : {}", professionalProfileImageDTO);
        ProfessionalProfileImage professionalProfileImage = professionalProfileImageMapper.toEntity(professionalProfileImageDTO);
        //check si ca n'existe pas déjà
        professionalProfileImage = professionalProfileImageRepository.save(professionalProfileImage);
        return professionalProfileImageMapper.toDto(professionalProfileImage);
    }

    /**
     * Get all the professionalProfileImages.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProfessionalProfileImageDTO> findAll() {
        log.debug("Request to get all ProfessionalProfileImages");
        return professionalProfileImageRepository.findAll().stream()
            .map(professionalProfileImageMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one professionalProfileImage by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProfessionalProfileImageDTO> findOneByProId(Long id) {
        log.debug("Request to get ProfessionalProfileImage : {}", id);
        return professionalProfileImageRepository.findByProId(id)
            .map(professionalProfileImageMapper::toDto);
    }

    /**
     * Delete the professionalProfileImage by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProfessionalProfileImage : {}", id);
        professionalProfileImageRepository.deleteById(id);
    }
}
