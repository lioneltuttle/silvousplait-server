package com.svp.repository;

import com.svp.domain.BillAudit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BillAudit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BillAuditRepository extends JpaRepository<BillAudit, Long> {

}
