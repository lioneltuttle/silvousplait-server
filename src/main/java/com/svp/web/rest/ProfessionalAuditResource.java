package com.svp.web.rest;

import com.svp.service.ProfessionalAuditService;
import com.svp.service.dto.ProfessionalAuditDTO;
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
 * REST controller for managing {@link com.svp.domain.ProfessionalAudit}.
 */
@RestController
@RequestMapping("/api")
public class ProfessionalAuditResource {

    private final Logger log = LoggerFactory.getLogger(ProfessionalAuditResource.class);

    private static final String ENTITY_NAME = "professionalAudit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProfessionalAuditService professionalAuditService;

    public ProfessionalAuditResource(ProfessionalAuditService professionalAuditService) {
        this.professionalAuditService = professionalAuditService;
    }

    /**
     * {@code POST  /professional-audits} : Create a new professionalAudit.
     *
     * @param professionalAuditDTO the professionalAuditDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new professionalAuditDTO, or with status {@code 400 (Bad Request)} if the professionalAudit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/professional-audits")
    public ResponseEntity<ProfessionalAuditDTO> createProfessionalAudit(@RequestBody ProfessionalAuditDTO professionalAuditDTO) throws URISyntaxException {
        log.debug("REST request to save ProfessionalAudit : {}", professionalAuditDTO);
        if (professionalAuditDTO.getId() != null) {
            throw new BadRequestAlertException("A new professionalAudit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProfessionalAuditDTO result = professionalAuditService.save(professionalAuditDTO);
        return ResponseEntity.created(new URI("/api/professional-audits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /professional-audits} : Updates an existing professionalAudit.
     *
     * @param professionalAuditDTO the professionalAuditDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated professionalAuditDTO,
     * or with status {@code 400 (Bad Request)} if the professionalAuditDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the professionalAuditDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/professional-audits")
    public ResponseEntity<ProfessionalAuditDTO> updateProfessionalAudit(@RequestBody ProfessionalAuditDTO professionalAuditDTO) throws URISyntaxException {
        log.debug("REST request to update ProfessionalAudit : {}", professionalAuditDTO);
        if (professionalAuditDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProfessionalAuditDTO result = professionalAuditService.save(professionalAuditDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, professionalAuditDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /professional-audits} : get all the professionalAudits.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of professionalAudits in body.
     */
    @GetMapping("/professional-audits")
    public List<ProfessionalAuditDTO> getAllProfessionalAudits() {
        log.debug("REST request to get all ProfessionalAudits");
        return professionalAuditService.findAll();
    }

    /**
     * {@code GET  /professional-audits/:id} : get the "id" professionalAudit.
     *
     * @param id the id of the professionalAuditDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the professionalAuditDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/professional-audits/{id}")
    public ResponseEntity<ProfessionalAuditDTO> getProfessionalAudit(@PathVariable Long id) {
        log.debug("REST request to get ProfessionalAudit : {}", id);
        Optional<ProfessionalAuditDTO> professionalAuditDTO = professionalAuditService.findOne(id);
        return ResponseUtil.wrapOrNotFound(professionalAuditDTO);
    }

    /**
     * {@code DELETE  /professional-audits/:id} : delete the "id" professionalAudit.
     *
     * @param id the id of the professionalAuditDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/professional-audits/{id}")
    public ResponseEntity<Void> deleteProfessionalAudit(@PathVariable Long id) {
        log.debug("REST request to delete ProfessionalAudit : {}", id);
        professionalAuditService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
