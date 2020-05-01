package com.svp.web.rest;

import com.svp.service.HitService;
import com.svp.web.rest.errors.BadRequestAlertException;
import com.svp.service.dto.HitDTO;

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
 * REST controller for managing {@link com.svp.domain.Hit}.
 */
@RestController
@RequestMapping("/api")
@Slf4j
public class HitResource {

    private static final String ENTITY_NAME = "hit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HitService hitService;

    public HitResource(HitService hitService) {
        this.hitService = hitService;
    }

    /**
     * {@code POST  /hits} : Create a new hit.
     *
     * @param hitDTO the hitDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hitDTO, or with status {@code 400 (Bad Request)} if the hit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/hits")
    public ResponseEntity<HitDTO> createHit(@RequestBody HitDTO hitDTO) throws URISyntaxException {
        log.debug("REST request to save Hit : {}", hitDTO);
        if (hitDTO.getId() != null) {
            throw new BadRequestAlertException("A new hit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HitDTO result = hitService.save(hitDTO);
        return ResponseEntity.created(new URI("/api/hits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /hits} : Updates an existing hit.
     *
     * @param hitDTO the hitDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hitDTO,
     * or with status {@code 400 (Bad Request)} if the hitDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hitDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/hits")
    public ResponseEntity<HitDTO> updateHit(@RequestBody HitDTO hitDTO) throws URISyntaxException {
        log.debug("REST request to update Hit : {}", hitDTO);
        if (hitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        HitDTO result = hitService.save(hitDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hitDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /hits} : get all the hits.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hits in body.
     */
    @GetMapping("/hits")
    public List<HitDTO> getAllHits() {
        log.debug("REST request to get all Hits");
        return hitService.findAll();
    }

    /**
     * {@code GET  /hits/:id} : get the "id" hit.
     *
     * @param id the id of the hitDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hitDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/hits/{id}")
    public ResponseEntity<HitDTO> getHit(@PathVariable Long id) {
        log.debug("REST request to get Hit : {}", id);
        Optional<HitDTO> hitDTO = hitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(hitDTO);
    }

    /**
     * {@code DELETE  /hits/:id} : delete the "id" hit.
     *
     * @param id the id of the hitDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/hits/{id}")
    public ResponseEntity<Void> deleteHit(@PathVariable Long id) {
        log.debug("REST request to delete Hit : {}", id);
        hitService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
