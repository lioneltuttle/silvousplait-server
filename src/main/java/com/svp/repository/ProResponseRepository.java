package com.svp.repository;

import com.svp.domain.ProResponse;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProResponse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProResponseRepository extends JpaRepository<ProResponse, Long> {

}
