package com.svp.repository;

import com.svp.domain.CompanyLocation;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the CompanyLocation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyLocationRepository extends JpaRepository<CompanyLocation, Long> {
    static final String HAVERSINE_PART = "(6371 * acos(cos(radians(:latitude)) * cos(radians(m.lat)) * cos(radians(m.lng) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(m.lat))))";

    @Query("SELECT m FROM CompanyLocation m WHERE "+HAVERSINE_PART+" < :distance")
    public List<CompanyLocation> findByLocationAndDistance(@Param("latitude") final double latitude, @Param("longitude") final double longitude, @Param("distance") final double distance);

    @Query("SELECT m FROM CompanyLocation m WHERE "+HAVERSINE_PART+" < :distance and m.company.companyType.id = :type")
    public List<CompanyLocation> findByLocationAndDistanceAndType(@Param("latitude") final double latitude, @Param("longitude") final double longitude, @Param("distance") final double distance, @Param("type") final long type);

}
