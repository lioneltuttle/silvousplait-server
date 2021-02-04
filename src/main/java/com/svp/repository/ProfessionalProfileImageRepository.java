package com.svp.repository;

import com.svp.domain.ProfessionalProfileImage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the ProfessionalProfileImage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProfessionalProfileImageRepository extends JpaRepository<ProfessionalProfileImage, Long> {

    Optional<ProfessionalProfileImage> findByProId(Long proId);
}
