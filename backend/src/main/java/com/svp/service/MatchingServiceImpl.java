package com.svp.service;

import com.svp.domain.Demand;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@ApplicationScoped
public class MatchingServiceImpl implements MatchingService {

    @Override
    public List<MatchedArtisan> findAndRankForDemand(Demand demand) {
        // Sprint 1 : implémentation minimale, sans appel réel ORS/PostGIS.
        // Cette méthode sera branchée plus tard sur la base artisans + ORS.
        return List.of();
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

    private double clamp(double value) {
        if (value < 0.0) {
            return 0.0;
        }
        if (value > 1.0) {
            return 1.0;
        }
        return value;
    }
}

