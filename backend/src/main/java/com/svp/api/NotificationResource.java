package com.svp.api;

import com.svp.notification.PushNotificationService;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotBlank;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * Sprint 2 : point d’entrée pour tester l’envoi FCM (wrapper + logs).
 */
@Path("/api/notifications")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class NotificationResource {

    @Inject
    PushNotificationService pushNotificationService;

    public record FcmTestRequest(
            @NotBlank String token,
            @NotBlank String title,
            @NotBlank String body
    ) {
    }

    @POST
    @Path("/test")
    public Response sendTest(FcmTestRequest request) {
        pushNotificationService.sendToToken(request.token(), request.title(), request.body());
        return Response.accepted().build();
    }
}
