package com.svp.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@ApplicationScoped
public class MatchingServiceImpl implements MatchingService {

    @Inject
    IsochroneProvider isochroneProvider;

    @Inject
    SpatialProfessionalRepository spatialProfessionalRepository;

    @Override
    public List<MatchedArtisan> findAndRankForDemand(Demand demand) {
        String wkt = isochroneProvider.buildIsochronePolygonWkt(
                demand.getClientLongitude(),
                demand.getClientLatitude());
        List<SpatialProfessionalRepository.CandidateRow> candidates = spatialProfessionalRepository
                .findAvailableInPolygon(demand.getServiceType(), wkt, demand.getClientLongitude(), demand.getClientLatitude());
        if (candidates.isEmpty()) {
            return List.of();
        }
        double maxDist = candidates.stream().mapToDouble(SpatialProfessionalRepository.CandidateRow::distanceMeters).max().orElse(1.0);
        return MatchingRanker.rankCandidates(candidates, maxDist);
    }

    /**
     * Score indicatif basé sur le CDC :
     * - note moyenne (0.0 - 5.0) poids 0.4
     * - proximité normalisée (0.0 - 1.0, plus proche = 1.0) poids 0.4
     * - taux de réponse (0.0 - 1.0) poids 0.2
     */
    public double computeScore(double rating, double proximityNormalized, double responseRate) {
        double clampedRating = clamp(rating / 5.0);
        double clampedProximity = clamp(proximityNormalized);
        double clampedResponse = clamp(responseRate);

        return (clampedRating * 0.4)
                + (clampedProximity * 0.4)
                + (clampedResponse * 0.2);
    }

    public List<MatchedArtisan> sortByScore(List<MatchedArtisan> artisans) {
        List<MatchedArtisan> copy = new ArrayList<>(artisans);
        copy.sort(Comparator.comparingDouble(MatchedArtisan::score).reversed());
        return copy;
    }

}

