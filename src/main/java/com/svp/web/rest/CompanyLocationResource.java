package com.svp.web.rest;

import com.svp.service.CompanyLocationService;
import com.svp.web.rest.errors.BadRequestAlertException;
import com.svp.service.dto.CompanyLocationDTO;

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
 * REST controller for managing {@link com.svp.domain.CompanyLocation}.
 */
@RestController
@RequestMapping("/api")
public class CompanyLocationResource {

    private final Logger log = LoggerFactory.getLogger(CompanyLocationResource.class);

    private static final String ENTITY_NAME = "companyLocation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompanyLocationService companyLocationService;

    public CompanyLocationResource(CompanyLocationService companyLocationService) {
        this.companyLocationService = companyLocationService;
    }

    /**
     * {@code POST  /company-locations} : Create a new companyLocation.
     *
     * @param companyLocationDTO the companyLocationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new companyLocationDTO, or with status {@code 400 (Bad Request)} if the companyLocation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/company-locations")
    public ResponseEntity<CompanyLocationDTO> createCompanyLocation(@RequestBody CompanyLocationDTO companyLocationDTO) throws URISyntaxException {
        log.debug("REST request to save CompanyLocation : {}", companyLocationDTO);
        if (companyLocationDTO.getId() != null) {
            throw new BadRequestAlertException("A new companyLocation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompanyLocationDTO result = companyLocationService.save(companyLocationDTO);
        return ResponseEntity.created(new URI("/api/company-locations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /company-locations} : Updates an existing companyLocation.
     *
     * @param companyLocationDTO the companyLocationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated companyLocationDTO,
     * or with status {@code 400 (Bad Request)} if the companyLocationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the companyLocationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/company-locations")
    public ResponseEntity<CompanyLocationDTO> updateCompanyLocation(@RequestBody CompanyLocationDTO companyLocationDTO) throws URISyntaxException {
        log.debug("REST request to update CompanyLocation : {}", companyLocationDTO);
        if (companyLocationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CompanyLocationDTO result = companyLocationService.save(companyLocationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, companyLocationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /company-locations} : get all the companyLocations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of companyLocations in body.
     */
    @GetMapping("/company-locations")
    public List<CompanyLocationDTO> getAllCompanyLocations() {
        log.debug("REST request to get all CompanyLocations");
        return companyLocationService.findAll();
    }

    /**
     * {@code GET  /company-locations/:id} : get the "id" companyLocation.
     *
     * @param id the id of the companyLocationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the companyLocationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/company-locations/{id}")
    public ResponseEntity<CompanyLocationDTO> getCompanyLocation(@PathVariable Long id) {
        log.debug("REST request to get CompanyLocation : {}", id);
        Optional<CompanyLocationDTO> companyLocationDTO = companyLocationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(companyLocationDTO);
    }

    /**
     * {@code DELETE  /company-locations/:id} : delete the "id" companyLocation.
     *
     * @param id the id of the companyLocationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/company-locations/{id}")
    public ResponseEntity<Void> deleteCompanyLocation(@PathVariable Long id) {
        log.debug("REST request to delete CompanyLocation : {}", id);
        companyLocationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
