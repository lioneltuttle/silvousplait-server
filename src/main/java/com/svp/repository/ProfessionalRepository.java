package com.svp.repository;

import com.svp.domain.Professional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the Professional entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProfessionalRepository extends JpaRepository<Professional, Long> {
    Optional<Professional> findByUserId(Long userId);
}
