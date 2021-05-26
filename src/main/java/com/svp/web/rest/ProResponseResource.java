package com.svp.web.rest;

import com.svp.service.ProResponseService;
import com.svp.web.rest.errors.BadRequestAlertException;
import com.svp.service.dto.ProResponseDTO;

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
 * REST controller for managing {@link com.svp.domain.ProResponse}.
 */
@RestController
@RequestMapping("/api")
public class ProResponseResource {

    private final Logger log = LoggerFactory.getLogger(ProResponseResource.class);

    private static final String ENTITY_NAME = "proResponse";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProResponseService proResponseService;

    public ProResponseResource(ProResponseService proResponseService) {
        this.proResponseService = proResponseService;
    }

    /**
     * {@code POST  /pro-responses} : Create a new proResponse.
     *
     * @param proResponseDTO the proResponseDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new proResponseDTO, or with status {@code 400 (Bad Request)} if the proResponse has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pro-responses")
    public ResponseEntity<ProResponseDTO> createProResponse(@RequestBody ProResponseDTO proResponseDTO) throws URISyntaxException {
        log.debug("REST request to save ProResponse : {}", proResponseDTO);
        if (proResponseDTO.getId() != null) {
            throw new BadRequestAlertException("A new proResponse cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProResponseDTO result = proResponseService.save(proResponseDTO);
        return ResponseEntity.created(new URI("/api/pro-responses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pro-responses} : Updates an existing proResponse.
     *
     * @param proResponseDTO the proResponseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated proResponseDTO,
     * or with status {@code 400 (Bad Request)} if the proResponseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the proResponseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pro-responses")
    public ResponseEntity<ProResponseDTO> updateProResponse(@RequestBody ProResponseDTO proResponseDTO) throws URISyntaxException {
        log.debug("REST request to update ProResponse : {}", proResponseDTO);
        if (proResponseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProResponseDTO result = proResponseService.save(proResponseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, proResponseDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pro-responses} : get all the proResponses.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of proResponses in body.
     */
    @GetMapping("/pro-responses")
    public List<ProResponseDTO> getAllProResponses() {
        log.debug("REST request to get all ProResponses");
        return proResponseService.findAll();
    }

    /**
     * {@code GET  /pro-responses/:id} : get the "id" proResponse.
     *
     * @param id the id of the proResponseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the proResponseDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pro-responses/{id}")
    public ResponseEntity<ProResponseDTO> getProResponse(@PathVariable Long id) {
        log.debug("REST request to get ProResponse : {}", id);
        Optional<ProResponseDTO> proResponseDTO = proResponseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(proResponseDTO);
    }

    /**
     * {@code DELETE  /pro-responses/:id} : delete the "id" proResponse.
     *
     * @param id the id of the proResponseDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pro-responses/{id}")
    public ResponseEntity<Void> deleteProResponse(@PathVariable Long id) {
        log.debug("REST request to delete ProResponse : {}", id);
        proResponseService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
