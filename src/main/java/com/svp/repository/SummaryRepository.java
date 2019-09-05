package com.svp.repository;

import com.svp.domain.Summary;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Summary entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SummaryRepository extends JpaRepository<Summary, Long> {

}
