package com.svp.websocket;

import io.quarkus.logging.Log;
import io.quarkus.websockets.next.OnClose;
import io.quarkus.websockets.next.OnOpen;
import io.quarkus.websockets.next.OnTextMessage;
import io.quarkus.websockets.next.Session;
import io.quarkus.websockets.next.WebSocket;

/**
 * Canal WebSocket "gps-update" : réception des positions artisans.
 * (artisan → serveur)
 */
@WebSocket(path = "/ws/gps-update")
public class GpsUpdateSocket {

    @OnOpen
    public void onOpen(Session session) {
        Log.infof("gps-update WebSocket connecté session=%s", session.id());
    }

    @OnClose
    public void onClose(Session session) {
        Log.infof("gps-update WebSocket fermé session=%s", session.id());
    }

    @OnTextMessage
    public void onGpsUpdate(String payload, Session session) {
        // Sprint 2 : on logue simplement la position, le stockage Redis/PostGIS viendra ensuite.
        Log.infof("gps-update from %s: %s", session.id(), payload);
    }
}

