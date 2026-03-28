package com.svp.billing;

import java.time.YearMonth;

public interface BillingService {

    MonthlyCounter getMonthlyCounter(Long artisanId, YearMonth period);

    record MonthlyCounter(
            Long artisanId,
            int year,
            int month,
            long interactionsCount,
            long totalAmountCents
    ) {
    }
}

