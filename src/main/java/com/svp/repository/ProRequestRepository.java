package com.svp.repository;

import com.svp.domain.ProRequest;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProRequest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProRequestRepository extends JpaRepository<ProRequest, Long> {

}
