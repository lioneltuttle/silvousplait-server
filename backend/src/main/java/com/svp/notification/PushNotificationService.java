package com.svp.notification;

public interface PushNotificationService {

    void sendToToken(String token, String title, String body);
}

