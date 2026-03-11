package com.svp.service;

import com.svp.domain.Demand;

import java.util.List;

/**
 * Service de matching artisans pour une demande donnée.
 * <p>
 * NOTE Sprint 1 : l'intégration réelle ORS + PostGIS sera branchée plus tard.
 * Ici, on prépare simplement la signature et la logique de scoring.
 */
public interface MatchingService {

    List<MatchedArtisan> findAndRankForDemand(Demand demand);

    record MatchedArtisan(
            Long professionalId,
            String displayName,
            double score
    ) {
    }
}

