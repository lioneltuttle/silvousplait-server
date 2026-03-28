package com.svp.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Requêtes PostGIS : artisans dans le polygone isochrone (ST_Within).
 */
@ApplicationScoped
public class SpatialProfessionalRepository {

    @Inject
    EntityManager entityManager;

    public record CandidateRow(
            long id,
            String displayName,
            double rating,
            double responseRate,
            double distanceMeters
    ) {
    }

    @SuppressWarnings("unchecked")
    public List<CandidateRow> findAvailableInPolygon(String serviceType, String polygonWkt, double demandLon, double demandLat) {
        String pattern = "%" + serviceType.trim() + "%";
        Query q = entityManager.createNativeQuery("""
                SELECT p.id, p.display_name, p.rating, p.response_rate,
                       ST_Distance(p.location, ST_SetSRID(ST_MakePoint(:lon, :lat), 4326)::geography) AS dist_m
                FROM professionals p
                WHERE p.available = true
                  AND p.service_type ILIKE :svc
                  AND ST_Within(p.location::geometry, ST_GeomFromText(:wkt, 4326))
                """);
        q.setParameter("lon", demandLon);
        q.setParameter("lat", demandLat);
        q.setParameter("svc", pattern);
        q.setParameter("wkt", polygonWkt);

        List<Object[]> rows = q.getResultList();
        List<CandidateRow> out = new ArrayList<>(rows.size());
        for (Object[] row : rows) {
            out.add(new CandidateRow(
                    ((Number) row[0]).longValue(),
                    (String) row[1],
                    ((Number) row[2]).doubleValue(),
                    ((Number) row[3]).doubleValue(),
                    ((Number) row[4]).doubleValue()
            ));
        }
        return out;
    }
}
