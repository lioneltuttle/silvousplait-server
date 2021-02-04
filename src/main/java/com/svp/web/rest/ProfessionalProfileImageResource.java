package com.svp.web.rest;

import com.svp.service.ProfessionalProfileImageService;
import com.svp.web.rest.errors.BadRequestAlertException;
import com.svp.service.dto.ProfessionalProfileImageDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.svp.domain.ProfessionalProfileImage}.
 */
@RestController
@RequestMapping("/api")
public class ProfessionalProfileImageResource {

    private final Logger log = LoggerFactory.getLogger(ProfessionalProfileImageResource.class);

    private static final String ENTITY_NAME = "professionalProfileImage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProfessionalProfileImageService professionalProfileImageService;

    public ProfessionalProfileImageResource(ProfessionalProfileImageService professionalProfileImageService) {
        this.professionalProfileImageService = professionalProfileImageService;
    }

    /**
     * {@code POST  /professional-profile-images} : Create a new professionalProfileImage.
     *
     * @param proId the professional id
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new professionalProfileImageDTO, or with status {@code 400 (Bad Request)} if the professionalProfileImage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/professional-profile-images")
    public boolean createProfessionalProfileImage(@RequestParam("proId") Long proId, @RequestParam("image")MultipartFile file) throws URISyntaxException {
        log.debug("REST request to save ProfessionalProfileImage : {}", file.getName());

        log.debug(file.getName());
        log.debug(file.getOriginalFilename());
        log.debug(""+file.getSize());

        try {
            ProfessionalProfileImageDTO professionalProfileImageDTO = new ProfessionalProfileImageDTO();
            professionalProfileImageDTO.setImage(file.getBytes());
            professionalProfileImageDTO.setProId(proId);
            ProfessionalProfileImageDTO result = professionalProfileImageService.save(professionalProfileImageDTO);

            return true;
        }
        catch (IOException e) {
            LoggerFactory.getLogger(this.getClass()).error("pictureupload", e);
            return false;
        }
    }

    /**
     * {@code PUT  /professional-profile-images} : Updates an existing professionalProfileImage.
     *
     * @param professionalProfileImageDTO the professionalProfileImageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated professionalProfileImageDTO,
     * or with status {@code 400 (Bad Request)} if the professionalProfileImageDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the professionalProfileImageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/professional-profile-images")
    public ResponseEntity<ProfessionalProfileImageDTO> updateProfessionalProfileImage(@RequestBody ProfessionalProfileImageDTO professionalProfileImageDTO) throws URISyntaxException {
        log.debug("REST request to update ProfessionalProfileImage : {}", professionalProfileImageDTO);
        if (professionalProfileImageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProfessionalProfileImageDTO result = professionalProfileImageService.save(professionalProfileImageDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, professionalProfileImageDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /professional-profile-images} : get all the professionalProfileImages.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of professionalProfileImages in body.
     */
    @GetMapping("/professional-profile-images")
    public List<ProfessionalProfileImageDTO> getAllProfessionalProfileImages() {
        log.debug("REST request to get all ProfessionalProfileImages");
        return professionalProfileImageService.findAll();
    }

    /**
     * {@code GET  /professional-profile-images/:id} : get the "id" of the pro professionalProfileImage.
     *
     * @param id the id of the professionalProfileImageDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the professionalProfileImageDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping(value = "/professional-profile-images/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getProfessionalProfileImage(@PathVariable Long id) {
        log.debug("REST request to get ProfessionalProfileImage : {}", id);
        Optional<ProfessionalProfileImageDTO> professionalProfileImageDTO = professionalProfileImageService.findOneByProId(id);
        return professionalProfileImageDTO.orElse(new ProfessionalProfileImageDTO()).getImage();
    }

    /**
     * {@code DELETE  /professional-profile-images/:id} : delete the "id" professionalProfileImage.
     *
     * @param id the id of the professionalProfileImageDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/professional-profile-images/{id}")
    public ResponseEntity<Void> deleteProfessionalProfileImage(@PathVariable Long id) {
        log.debug("REST request to delete ProfessionalProfileImage : {}", id);
        professionalProfileImageService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
