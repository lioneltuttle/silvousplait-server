package com.svp.service;

import com.svp.service.MatchingService.MatchedArtisan;
import com.svp.service.SpatialProfessionalRepository.CandidateRow;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MatchingRankerTest {

    @Test
    void rankOrdersByScoreDescending() {
        List<CandidateRow> candidates = List.of(
                new CandidateRow(1L, "A", 5.0, 1.0, 1000),
                new CandidateRow(2L, "B", 3.0, 0.5, 500)
        );
        List<MatchedArtisan> ranked = MatchingRanker.rankCandidates(candidates, 2000);
        assertEquals(2, ranked.size());
        assertTrue(ranked.get(0).score() >= ranked.get(1).score());
    }

    @Test
    void computeScoreIsDeterministic() {
        double s1 = MatchingRanker.computeScore(5.0, 1.0, 1.0);
        double s2 = MatchingRanker.computeScore(5.0, 1.0, 1.0);
        assertEquals(s1, s2);
    }
}
