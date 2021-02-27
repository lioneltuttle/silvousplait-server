package com.svp.repository;

import com.svp.domain.Hit;
import com.svp.domain.Professional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


/**
 * Spring Data  repository for the Hit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HitRepository extends JpaRepository<Hit, Long> {

    List<Hit> findByProfessional(Professional professional);

    List<Hit> findByProfessionalAndDateBetween(Professional professional, LocalDate from, LocalDate to);

    List<Hit> findByDateBetween(LocalDate from, LocalDate to);

}
