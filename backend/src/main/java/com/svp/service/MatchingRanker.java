package com.svp.service;

import com.svp.service.MatchingService.MatchedArtisan;
import com.svp.service.SpatialProfessionalRepository.CandidateRow;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Classement déterministe des artisans (score CDC) — testable sans base.
 */
public final class MatchingRanker {

    private MatchingRanker() {
    }

    /**
     * @param maxDistanceMeters distance max observée pour normaliser la proximité (évite division par zéro)
     */
    public static List<MatchedArtisan> rankCandidates(List<CandidateRow> candidates, Demand demand, double maxDistanceMeters) {
        double maxD = maxDistanceMeters > 0 ? maxDistanceMeters : 1.0;
        List<MatchedArtisan> scored = new ArrayList<>(candidates.size());
        for (CandidateRow c : candidates) {
            double proximity = 1.0 - Math.min(1.0, c.distanceMeters() / maxD);
            double score = computeScore(c.rating(), proximity, c.responseRate());
            scored.add(new MatchedArtisan(c.id(), c.displayName(), score));
        }
        scored.sort(Comparator.comparingDouble(MatchedArtisan::score).reversed());
        return scored;
    }

    public static double computeScore(double rating, double proximityNormalized, double responseRate) {
        double clampedRating = clamp(rating / 5.0);
        double clampedProximity = clamp(proximityNormalized);
        double clampedResponse = clamp(responseRate);
        return (clampedRating * 0.4) + (clampedProximity * 0.4) + (clampedResponse * 0.2);
    }

    private static double clamp(double value) {
        return Math.max(0.0, Math.min(1.0, value));
    }
}
