package com.svp.notification;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PushNotificationServiceImpl implements PushNotificationService {

    @Override
    public void sendToToken(String token, String title, String body) {
        // Sprint 2 : implémentation minimale, on logue l'envoi.
        // L'appel HTTP réel vers FCM sera branché plus tard.
        Log.infof("FCM notification to token=%s title=%s body=%s", token, title, body);
    }
}

