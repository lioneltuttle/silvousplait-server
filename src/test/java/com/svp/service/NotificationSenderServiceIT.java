package com.svp.service;

import com.svp.SilvousplaitApp;
import com.svp.service.impl.NotificationSenderServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = SilvousplaitApp.class)
public class NotificationSenderServiceIT {

    private NotificationSenderService notifService;

    @Before
    public void setUp(){
        notifService = new NotificationSenderServiceImpl();
    }

    @Test
    public void sendPushNotification(){
        // "6e64fc95-7ba4-4e32-88cc-a864ed60ea78"
        notifService.sendPushNotification(2l,"Test","en fr","ths is the english mess", "this is the french mess");
    }
}
