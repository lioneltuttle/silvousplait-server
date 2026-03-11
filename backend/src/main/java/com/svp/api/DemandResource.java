package com.svp.api;

import com.svp.domain.Demand;
import com.svp.domain.DemandRepository;
import com.svp.service.MatchingService;
import com.svp.websocket.DemandDispatchSocket;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/demands")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DemandResource {

    @Inject
    DemandRepository demandRepository;

    @Inject
    MatchingService matchingService;

    @Inject
    DemandDispatchSocket demandDispatchSocket;

    public record DemandRequest(
            @NotBlank String serviceType,
            @NotNull Double clientLatitude,
            @NotNull Double clientLongitude
    ) {
    }

    public record DemandResponse(Long id) {
    }

    @POST
    @Transactional
    public Response createDemand(DemandRequest request) {
        Demand demand = new Demand();
        demand.setServiceType(request.serviceType());
        demand.setClientLatitude(request.clientLatitude());
        demand.setClientLongitude(request.clientLongitude());

        demandRepository.persist(demand);

        // Déclenchement du matching (pour l'instant, implémentation minimale).
        var matched = matchingService.findAndRankForDemand(demand);

        // Sprint 2 : si des artisans sont retournés, on pousse un message simple sur le canal demand-dispatch.
        if (!matched.isEmpty()) {
            String message = "Nouvelle demande: %s (#%d)".formatted(demand.getServiceType(), demand.getId());
            demandDispatchSocket.sendDemandToAll(message);
        }

        return Response
                .status(Response.Status.CREATED)
                .entity(new DemandResponse(demand.getId()))
                .build();
    }
}

