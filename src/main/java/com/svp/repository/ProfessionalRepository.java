package com.svp.repository;

import com.svp.domain.Professional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the Professional entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProfessionalRepository extends JpaRepository<Professional, Long> {
    Optional<Professional> findByUserId(Long userId);

    static final String HAVERSINE_PART = "(6371 * acos(cos(radians(:latitude)) * cos(radians(m.lat)) * cos(radians(m.lng) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(m.lat))))";

    @Query("SELECT m FROM Professional m WHERE "+HAVERSINE_PART+" < :distance")
    public List<Professional> findByLocationAndDistance(@Param("latitude") final double latitude, @Param("longitude") final double longitude, @Param("distance") final double distance);

    @Query("SELECT m FROM Professional m WHERE "+HAVERSINE_PART+" < :distance and m.company.companyType.id = :type")
    public List<Professional> findByLocationAndDistanceAndType(@Param("latitude") final double latitude, @Param("longitude") final double longitude, @Param("distance") final double distance, @Param("type") final long type);

    @Query("SELECT m.id FROM Professional m WHERE "+HAVERSINE_PART+" < :distance and m.company.companyType.id = :type")
    public List<Long> findIdsByLocationAndDistanceAndType(@Param("latitude") final double latitude, @Param("longitude") final double longitude, @Param("distance") final double distance, @Param("type") final long type);

}
