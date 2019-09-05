package com.svp.repository;

import com.svp.domain.ProfessionalAudit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProfessionalAudit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProfessionalAuditRepository extends JpaRepository<ProfessionalAudit, Long> {

}
