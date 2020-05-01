package com.svp.web.rest;

import com.svp.service.ProRequestService;
import com.svp.web.rest.errors.BadRequestAlertException;
import com.svp.service.dto.ProRequestDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
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
 * REST controller for managing {@link com.svp.domain.ProRequest}.
 */
@RestController
@RequestMapping("/api")
@Slf4j
public class ProRequestResource {

    private static final String ENTITY_NAME = "proRequest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProRequestService proRequestService;

    public ProRequestResource(ProRequestService proRequestService) {
        this.proRequestService = proRequestService;
    }

    /**
     * {@code POST  /pro-requests} : Create a new proRequest.
     *
     * @param proRequestDTO the proRequestDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new proRequestDTO, or with status {@code 400 (Bad Request)} if the proRequest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pro-requests")
    public ResponseEntity<ProRequestDTO> createProRequest(@RequestBody ProRequestDTO proRequestDTO) throws URISyntaxException {
        log.debug("REST request to save ProRequest : {}", proRequestDTO);
        if (proRequestDTO.getId() != null) {
            throw new BadRequestAlertException("A new proRequest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProRequestDTO result = proRequestService.save(proRequestDTO);
        return ResponseEntity.created(new URI("/api/pro-requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pro-requests} : Updates an existing proRequest.
     *
     * @param proRequestDTO the proRequestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated proRequestDTO,
     * or with status {@code 400 (Bad Request)} if the proRequestDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the proRequestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pro-requests")
    public ResponseEntity<ProRequestDTO> updateProRequest(@RequestBody ProRequestDTO proRequestDTO) throws URISyntaxException {
        log.debug("REST request to update ProRequest : {}", proRequestDTO);
        if (proRequestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProRequestDTO result = proRequestService.save(proRequestDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, proRequestDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pro-requests} : get all the proRequests.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of proRequests in body.
     */
    @GetMapping("/pro-requests")
    public List<ProRequestDTO> getAllProRequests() {
        log.debug("REST request to get all ProRequests");
        return proRequestService.findAll();
    }

    /**
     * {@code GET  /pro-requests/:id} : get the "id" proRequest.
     *
     * @param id the id of the proRequestDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the proRequestDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pro-requests/{id}")
    public ResponseEntity<ProRequestDTO> getProRequest(@PathVariable Long id) {
        log.debug("REST request to get ProRequest : {}", id);
        Optional<ProRequestDTO> proRequestDTO = proRequestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(proRequestDTO);
    }

    /**
     * {@code DELETE  /pro-requests/:id} : delete the "id" proRequest.
     *
     * @param id the id of the proRequestDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pro-requests/{id}")
    public ResponseEntity<Void> deleteProRequest(@PathVariable Long id) {
        log.debug("REST request to delete ProRequest : {}", id);
        proRequestService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
