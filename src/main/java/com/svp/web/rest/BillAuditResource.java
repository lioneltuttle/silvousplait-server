package com.svp.web.rest;

import com.svp.service.BillAuditService;
import com.svp.web.rest.errors.BadRequestAlertException;
import com.svp.service.dto.BillAuditDTO;

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
 * REST controller for managing {@link com.svp.domain.BillAudit}.
 */
@RestController
@RequestMapping("/api")
public class BillAuditResource {

    private final Logger log = LoggerFactory.getLogger(BillAuditResource.class);

    private static final String ENTITY_NAME = "billAudit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BillAuditService billAuditService;

    public BillAuditResource(BillAuditService billAuditService) {
        this.billAuditService = billAuditService;
    }

    /**
     * {@code POST  /bill-audits} : Create a new billAudit.
     *
     * @param billAuditDTO the billAuditDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new billAuditDTO, or with status {@code 400 (Bad Request)} if the billAudit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bill-audits")
    public ResponseEntity<BillAuditDTO> createBillAudit(@RequestBody BillAuditDTO billAuditDTO) throws URISyntaxException {
        log.debug("REST request to save BillAudit : {}", billAuditDTO);
        if (billAuditDTO.getId() != null) {
            throw new BadRequestAlertException("A new billAudit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BillAuditDTO result = billAuditService.save(billAuditDTO);
        return ResponseEntity.created(new URI("/api/bill-audits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bill-audits} : Updates an existing billAudit.
     *
     * @param billAuditDTO the billAuditDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated billAuditDTO,
     * or with status {@code 400 (Bad Request)} if the billAuditDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the billAuditDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bill-audits")
    public ResponseEntity<BillAuditDTO> updateBillAudit(@RequestBody BillAuditDTO billAuditDTO) throws URISyntaxException {
        log.debug("REST request to update BillAudit : {}", billAuditDTO);
        if (billAuditDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BillAuditDTO result = billAuditService.save(billAuditDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, billAuditDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /bill-audits} : get all the billAudits.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of billAudits in body.
     */
    @GetMapping("/bill-audits")
    public List<BillAuditDTO> getAllBillAudits() {
        log.debug("REST request to get all BillAudits");
        return billAuditService.findAll();
    }

    /**
     * {@code GET  /bill-audits/:id} : get the "id" billAudit.
     *
     * @param id the id of the billAuditDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the billAuditDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bill-audits/{id}")
    public ResponseEntity<BillAuditDTO> getBillAudit(@PathVariable Long id) {
        log.debug("REST request to get BillAudit : {}", id);
        Optional<BillAuditDTO> billAuditDTO = billAuditService.findOne(id);
        return ResponseUtil.wrapOrNotFound(billAuditDTO);
    }

    /**
     * {@code DELETE  /bill-audits/:id} : delete the "id" billAudit.
     *
     * @param id the id of the billAuditDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bill-audits/{id}")
    public ResponseEntity<Void> deleteBillAudit(@PathVariable Long id) {
        log.debug("REST request to delete BillAudit : {}", id);
        billAuditService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
