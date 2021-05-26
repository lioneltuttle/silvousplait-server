package com.svp.service;

public interface NotificationSenderService {

    public void sendPushNotification(Long userId, String englishTitle, String frenchTitle, String englishMessage, String frenchMessage, boolean isAdmin);

    }
