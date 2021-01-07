package com.svp.web.rest;

import com.svp.service.ProChoiceService;
import com.svp.web.rest.errors.BadRequestAlertException;
import com.svp.service.dto.ProChoiceDTO;

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
 * REST controller for managing {@link com.svp.domain.ProChoice}.
 */
@RestController
@RequestMapping("/api")
public class ProChoiceResource {

    private final Logger log = LoggerFactory.getLogger(ProChoiceResource.class);

    private static final String ENTITY_NAME = "proChoice";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProChoiceService proChoiceService;

    public ProChoiceResource(ProChoiceService proChoiceService) {
        this.proChoiceService = proChoiceService;
    }

    /**
     * {@code POST  /pro-choices} : Create a new proChoice.
     *
     * @param proChoiceDTO the proChoiceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new proChoiceDTO, or with status {@code 400 (Bad Request)} if the proChoice has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pro-choices")
    public ResponseEntity<ProChoiceDTO> createProChoice(@RequestBody ProChoiceDTO proChoiceDTO) throws URISyntaxException {
        log.debug("REST request to save ProChoice : {}", proChoiceDTO);
        if (proChoiceDTO.getId() != null) {
            throw new BadRequestAlertException("A new proChoice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProChoiceDTO result = proChoiceService.save(proChoiceDTO);
        return ResponseEntity.created(new URI("/api/pro-choices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pro-choices} : Updates an existing proChoice.
     *
     * @param proChoiceDTO the proChoiceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated proChoiceDTO,
     * or with status {@code 400 (Bad Request)} if the proChoiceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the proChoiceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pro-choices")
    public ResponseEntity<ProChoiceDTO> updateProChoice(@RequestBody ProChoiceDTO proChoiceDTO) throws URISyntaxException {
        log.debug("REST request to update ProChoice : {}", proChoiceDTO);
        if (proChoiceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProChoiceDTO result = proChoiceService.save(proChoiceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, proChoiceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pro-choices} : get all the proChoices.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of proChoices in body.
     */
    @GetMapping("/pro-choices")
    public List<ProChoiceDTO> getAllProChoices() {
        log.debug("REST request to get all ProChoices");
        return proChoiceService.findAll();
    }

    /**
     * {@code GET  /pro-choices/:id} : get the "id" proChoice.
     *
     * @param id the id of the proChoiceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the proChoiceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pro-choices/{id}")
    public ResponseEntity<ProChoiceDTO> getProChoice(@PathVariable Long id) {
        log.debug("REST request to get ProChoice : {}", id);
        Optional<ProChoiceDTO> proChoiceDTO = proChoiceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(proChoiceDTO);
    }

    /**
     * {@code DELETE  /pro-choices/:id} : delete the "id" proChoice.
     *
     * @param id the id of the proChoiceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pro-choices/{id}")
    public ResponseEntity<Void> deleteProChoice(@PathVariable Long id) {
        log.debug("REST request to delete ProChoice : {}", id);
        proChoiceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
