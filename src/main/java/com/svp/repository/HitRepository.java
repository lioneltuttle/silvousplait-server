package com.svp.repository;

import com.svp.domain.Hit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Hit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HitRepository extends JpaRepository<Hit, Long> {

}
