package com.svp.web.rest;

import com.svp.service.CompanyTypeService;
import com.svp.web.rest.errors.BadRequestAlertException;
import com.svp.service.dto.CompanyTypeDTO;

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
 * REST controller for managing {@link com.svp.domain.CompanyType}.
 */
@RestController
@RequestMapping("/api")
@Slf4j
public class CompanyTypeResource {

    private static final String ENTITY_NAME = "companyType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompanyTypeService companyTypeService;

    public CompanyTypeResource(CompanyTypeService companyTypeService) {
        this.companyTypeService = companyTypeService;
    }

    /**
     * {@code POST  /company-types} : Create a new companyType.
     *
     * @param companyTypeDTO the companyTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new companyTypeDTO, or with status {@code 400 (Bad Request)} if the companyType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/company-types")
    public ResponseEntity<CompanyTypeDTO> createCompanyType(@RequestBody CompanyTypeDTO companyTypeDTO) throws URISyntaxException {
        log.debug("REST request to save CompanyType : {}", companyTypeDTO);
        if (companyTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new companyType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompanyTypeDTO result = companyTypeService.save(companyTypeDTO);
        return ResponseEntity.created(new URI("/api/company-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /company-types} : Updates an existing companyType.
     *
     * @param companyTypeDTO the companyTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated companyTypeDTO,
     * or with status {@code 400 (Bad Request)} if the companyTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the companyTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/company-types")
    public ResponseEntity<CompanyTypeDTO> updateCompanyType(@RequestBody CompanyTypeDTO companyTypeDTO) throws URISyntaxException {
        log.debug("REST request to update CompanyType : {}", companyTypeDTO);
        if (companyTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CompanyTypeDTO result = companyTypeService.save(companyTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, companyTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /company-types} : get all the companyTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of companyTypes in body.
     */
    @GetMapping("/company-types")
    public List<CompanyTypeDTO> getAllCompanyTypes() {
        log.debug("REST request to get all CompanyTypes");
        return companyTypeService.findAll();
    }

    /**
     * {@code GET  /company-types/:id} : get the "id" companyType.
     *
     * @param id the id of the companyTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the companyTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/company-types/{id}")
    public ResponseEntity<CompanyTypeDTO> getCompanyType(@PathVariable Long id) {
        log.debug("REST request to get CompanyType : {}", id);
        Optional<CompanyTypeDTO> companyTypeDTO = companyTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(companyTypeDTO);
    }

    /**
     * {@code DELETE  /company-types/:id} : delete the "id" companyType.
     *
     * @param id the id of the companyTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/company-types/{id}")
    public ResponseEntity<Void> deleteCompanyType(@PathVariable Long id) {
        log.debug("REST request to delete CompanyType : {}", id);
        companyTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
