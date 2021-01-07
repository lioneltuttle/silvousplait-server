package com.svp.web.rest;

import com.svp.service.SummaryService;
import com.svp.service.dto.SummaryDTO;
import com.svp.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.svp.domain.Summary}.
 */
@RestController
@RequestMapping("/api")
public class SummaryResource {

    private final Logger log = LoggerFactory.getLogger(SummaryResource.class);

    private static final String ENTITY_NAME = "summary";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SummaryService summaryService;

    public SummaryResource(SummaryService summaryService) {
        this.summaryService = summaryService;
    }

    /**
     * {@code POST  /summaries} : Create a new summary.
     *
     * @param summaryDTO the summaryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new summaryDTO, or with status {@code 400 (Bad Request)} if the summary has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/summaries")
    public ResponseEntity<SummaryDTO> createSummary(@RequestBody SummaryDTO summaryDTO) throws URISyntaxException {
        log.debug("REST request to save Summary : {}", summaryDTO);
        if (summaryDTO.getId() != null) {
            throw new BadRequestAlertException("A new summary cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SummaryDTO result = summaryService.save(summaryDTO);
        return ResponseEntity.created(new URI("/api/summaries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /summaries} : Updates an existing summary.
     *
     * @param summaryDTO the summaryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated summaryDTO,
     * or with status {@code 400 (Bad Request)} if the summaryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the summaryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/summaries")
    public ResponseEntity<SummaryDTO> updateSummary(@RequestBody SummaryDTO summaryDTO) throws URISyntaxException {
        log.debug("REST request to update Summary : {}", summaryDTO);
        if (summaryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SummaryDTO result = summaryService.save(summaryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, summaryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /summaries} : get all the summaries.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of summaries in body.
     */
    @GetMapping("/summaries")
    public List<SummaryDTO> getAllSummaries() {
        log.debug("REST request to get all Summaries");
        return summaryService.findAll();
    }

    /**
     * {@code GET  /summaries/:id} : get the "id" summary.
     *
     * @param id the id of the summaryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the summaryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/summaries/{id}")
    public ResponseEntity<SummaryDTO> getSummary(@PathVariable Long id) {
        log.debug("REST request to get Summary : {}", id);
        Optional<SummaryDTO> summaryDTO = summaryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(summaryDTO);
    }

    /**
     * {@code DELETE  /summaries/:id} : delete the "id" summary.
     *
     * @param id the id of the summaryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/summaries/{id}")
    public ResponseEntity<Void> deleteSummary(@PathVariable Long id) {
        log.debug("REST request to delete Summary : {}", id);
        summaryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
