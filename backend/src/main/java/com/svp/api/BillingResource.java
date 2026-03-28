package com.svp.api;

import com.svp.billing.BillingService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.time.YearMonth;

@Path("/api/billing")
@Produces(MediaType.APPLICATION_JSON)
public class BillingResource {

    @Inject
    BillingService billingService;

    public record MonthlyCounterResponse(
            Long artisanId,
            int year,
            int month,
            long interactionsCount,
            long totalAmountCents
    ) {
    }

    @GET
    @Path("/{artisanId}/monthly-counter")
    public MonthlyCounterResponse getMonthlyCounter(@PathParam("artisanId") Long artisanId) {
        var period = YearMonth.now();
        var counter = billingService.getMonthlyCounter(artisanId, period);
        return new MonthlyCounterResponse(
                counter.artisanId(),
                counter.year(),
                counter.month(),
                counter.interactionsCount(),
                counter.totalAmountCents()
        );
    }
}

