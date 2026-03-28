package com.svp.api;

import com.svp.websocket.DemandDispatchSocket;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotBlank;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * Sprint 2 : envoi manuel de message sur le canal demand-dispatch (tous les artisans connectés).
 */
@Path("/api/dev/ws")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class WebSocketSmokeResource {

    @Inject
    DemandDispatchSocket demandDispatchSocket;

    public record BroadcastRequest(@NotBlank String message) {
    }

    @POST
    @Path("/demand-dispatch-broadcast")
    public Response broadcast(BroadcastRequest request) {
        demandDispatchSocket.sendDemandToAll(request.message());
        return Response.accepted().build();
    }
}
