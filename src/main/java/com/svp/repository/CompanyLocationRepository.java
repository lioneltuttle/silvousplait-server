package com.svp.repository;

import com.svp.domain.CompanyLocation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CompanyLocation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyLocationRepository extends JpaRepository<CompanyLocation, Long> {

}
