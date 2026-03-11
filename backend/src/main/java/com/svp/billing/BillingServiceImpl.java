package com.svp.billing;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.YearMonth;

@ApplicationScoped
public class BillingServiceImpl implements BillingService {

    @Inject
    BillingInteractionRepository interactionRepository;

    // Valeur indicative, sera alignée plus tard avec la configuration métier.
    private static final int DEFAULT_AMOUNT_CENTS = 500; // 5,00 € HT par mise en relation

    @Override
    public MonthlyCounter getMonthlyCounter(Long artisanId, YearMonth period) {
        long count = interactionRepository.countForArtisanInMonth(
                artisanId,
                period.getYear(),
                period.getMonthValue()
        );

        long totalCents = count * DEFAULT_AMOUNT_CENTS;

        return new MonthlyCounter(
                artisanId,
                period.getYear(),
                period.getMonthValue(),
                count,
                totalCents
        );
    }
}

