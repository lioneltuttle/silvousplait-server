package com.svp.billing;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

@ApplicationScoped
public class BillingInteractionRepository implements PanacheRepositoryBase<BillingInteraction, Long> {

    public long countForArtisanInMonth(Long artisanId, int year, int month) {
        Instant start = LocalDate.of(year, month, 1).atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant end = start.plusSeconds(60L * 60 * 24 * 32); // borne large, filtrée par mois au-dessus
        return count("artisanId = ?1 and createdAt >= ?2 and createdAt < ?3", artisanId, start, end);
    }
}

