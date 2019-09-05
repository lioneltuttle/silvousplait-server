package com.svp.repository;

import com.svp.domain.ProfessionalDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProfessionalDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProfessionalDetailsRepository extends JpaRepository<ProfessionalDetails, Long> {

}
