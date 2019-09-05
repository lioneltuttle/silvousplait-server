package com.svp.web.rest;

import com.svp.service.ProfessionalDetailsService;
import com.svp.web.rest.errors.BadRequestAlertException;
import com.svp.service.dto.ProfessionalDetailsDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.svp.domain.ProfessionalDetails}.
 */
@RestController
@RequestMapping("/api")
public class ProfessionalDetailsResource {

    private final Logger log = LoggerFactory.getLogger(ProfessionalDetailsResource.class);

    private static final String ENTITY_NAME = "professionalDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProfessionalDetailsService professionalDetailsService;

    public ProfessionalDetailsResource(ProfessionalDetailsService professionalDetailsService) {
        this.professionalDetailsService = professionalDetailsService;
    }

    /**
     * {@code POST  /professional-details} : Create a new professionalDetails.
     *
     * @param professionalDetailsDTO the professionalDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new professionalDetailsDTO, or with status {@code 400 (Bad Request)} if the professionalDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/professional-details")
    public ResponseEntity<ProfessionalDetailsDTO> createProfessionalDetails(@RequestBody ProfessionalDetailsDTO professionalDetailsDTO) throws URISyntaxException {
        log.debug("REST request to save ProfessionalDetails : {}", professionalDetailsDTO);
        if (professionalDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new professionalDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProfessionalDetailsDTO result = professionalDetailsService.save(professionalDetailsDTO);
        return ResponseEntity.created(new URI("/api/professional-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /professional-details} : Updates an existing professionalDetails.
     *
     * @param professionalDetailsDTO the professionalDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated professionalDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the professionalDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the professionalDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/professional-details")
    public ResponseEntity<ProfessionalDetailsDTO> updateProfessionalDetails(@RequestBody ProfessionalDetailsDTO professionalDetailsDTO) throws URISyntaxException {
        log.debug("REST request to update ProfessionalDetails : {}", professionalDetailsDTO);
        if (professionalDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProfessionalDetailsDTO result = professionalDetailsService.save(professionalDetailsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, professionalDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /professional-details} : get all the professionalDetails.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of professionalDetails in body.
     */
    @GetMapping("/professional-details")
    public List<ProfessionalDetailsDTO> getAllProfessionalDetails() {
        log.debug("REST request to get all ProfessionalDetails");
        return professionalDetailsService.findAll();
    }

    /**
     * {@code GET  /professional-details/:id} : get the "id" professionalDetails.
     *
     * @param id the id of the professionalDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the professionalDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/professional-details/{id}")
    public ResponseEntity<ProfessionalDetailsDTO> getProfessionalDetails(@PathVariable Long id) {
        log.debug("REST request to get ProfessionalDetails : {}", id);
        Optional<ProfessionalDetailsDTO> professionalDetailsDTO = professionalDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(professionalDetailsDTO);
    }

    /**
     * {@code DELETE  /professional-details/:id} : delete the "id" professionalDetails.
     *
     * @param id the id of the professionalDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/professional-details/{id}")
    public ResponseEntity<Void> deleteProfessionalDetails(@PathVariable Long id) {
        log.debug("REST request to delete ProfessionalDetails : {}", id);
        professionalDetailsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
