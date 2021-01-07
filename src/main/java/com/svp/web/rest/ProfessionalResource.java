package com.svp.web.rest;

import com.svp.service.ProfessionalService;
import com.svp.service.dto.ProfessionalDTO;
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
 * REST controller for managing {@link com.svp.domain.Professional}.
 */
@RestController
@RequestMapping("/api")
public class ProfessionalResource {

    private final Logger log = LoggerFactory.getLogger(ProfessionalResource.class);

    private static final String ENTITY_NAME = "professional";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProfessionalService professionalService;

    public ProfessionalResource(ProfessionalService professionalService) {
        this.professionalService = professionalService;
    }

    /**
     * {@code POST  /professionals} : Create a new professional.
     *
     * @param professionalDTO the professionalDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new professionalDTO, or with status {@code 400 (Bad Request)} if the professional has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/professionals")
    public ResponseEntity<ProfessionalDTO> createProfessional(@RequestBody ProfessionalDTO professionalDTO) throws URISyntaxException {
        log.debug("REST request to save Professional : {}", professionalDTO);
        if (professionalDTO.getId() != null) {
            throw new BadRequestAlertException("A new professional cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProfessionalDTO result = professionalService.save(professionalDTO);
        return ResponseEntity.created(new URI("/api/professionals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /professionals} : Updates an existing professional.
     *
     * @param professionalDTO the professionalDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated professionalDTO,
     * or with status {@code 400 (Bad Request)} if the professionalDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the professionalDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/professionals")
    public ResponseEntity<ProfessionalDTO> updateProfessional(@RequestBody ProfessionalDTO professionalDTO) throws URISyntaxException {
        log.debug("REST request to update Professional : {}", professionalDTO);
        if (professionalDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProfessionalDTO result = professionalService.save(professionalDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, professionalDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /professionals} : get all the professionals.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of professionals in body.
     */
    @GetMapping("/professionals")
    public List<ProfessionalDTO> getAllProfessionals() {
        log.debug("REST request to get all Professionals");
        return professionalService.findAll();
    }

    /**
     * {@code GET  /professionals/:id} : get the "id" professional.
     *
     * @param id the id of the professionalDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the professionalDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/professionals/{id}")
    public ResponseEntity<ProfessionalDTO> getProfessional(@PathVariable Long id) {
        log.debug("REST request to get Professional : {}", id);
        Optional<ProfessionalDTO> professionalDTO = professionalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(professionalDTO);
    }

    /**
     * {@code DELETE  /professionals/:id} : delete the "id" professional.
     *
     * @param id the id of the professionalDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/professionals/{id}")
    public ResponseEntity<Void> deleteProfessional(@PathVariable Long id) {
        log.debug("REST request to delete Professional : {}", id);
        professionalService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
