package com.svp.service;

import com.svp.domain.ProRequest;

import java.util.List;

public interface NotificationSenderService {

    public void sendPushNotification(Long userId, String englishTitle, String frenchTitle, String englishMessage, String frenchMessage);

    public void sendWorkRequest(List<Long> proIds);

    }
