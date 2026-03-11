package com.svp.websocket;

import io.quarkus.logging.Log;
import io.quarkus.websockets.next.OnTextMessage;
import io.quarkus.websockets.next.Session;
import io.quarkus.websockets.next.WebSocket;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Canal WebSocket "demand-dispatch" : envoi des demandes aux artisans.
 * (serveur → artisan)
 */
@WebSocket(path = "/ws/demand-dispatch")
@ApplicationScoped
public class DemandDispatchSocket {

    private final Map<String, Session> sessionsById = new ConcurrentHashMap<>();

    @OnTextMessage
    public void onSubscribe(String payload, Session session) {
        // Pour l'instant, on utilise l'ID de session comme identifiant artisan.
        sessionsById.put(session.id(), session);
        Log.infof("Artisan connecté sur demand-dispatch, session=%s payload=%s", session.id(), payload);
    }

    public void sendDemandToAll(String message) {
        sessionsById.values().forEach(session -> {
            session.sendTextAndAwait(message);
        });
    }
}

