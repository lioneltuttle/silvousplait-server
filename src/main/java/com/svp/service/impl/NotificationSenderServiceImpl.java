package com.svp.service.impl;

import com.svp.service.NotificationSenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

@Service
@Transactional
public class NotificationSenderServiceImpl implements NotificationSenderService {

    private final Logger log = LoggerFactory.getLogger(NotificationSenderServiceImpl.class);


    @Override
    public void sendPushNotification(Long userId, String englishTitle, String frenchTitle, String englishMessage, String frenchMessage, boolean isAdmin) {
        try {
            String jsonResponse;

            URL url = new URL("https://onesignal.com/api/v1/notifications");
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setDoInput(true);

            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Authorization", "Basic \u003cZGQ4NDhiZjktNDFlZS00YTE5LWEwYWYtN2I3YTdhMDVkMGI2\u003e");
            con.setRequestMethod("POST");


            String strJsonBody = "{"
                +   "\"app_id\": \"5b325129-7920-495f-9fe4-737c299bda29\","
                +   (isAdmin?"": "\"include_external_user_ids\": [\""+ userId+"\"],")
                +   "\"channel_for_external_user_ids\": \"push\","
                +   "\"data\": {\"foo\": \"bar\"},"
                +   "\"action\": \"OK\","
                + (isAdmin?"\"filters\": [{\"field\": \"tag\", \"key\": \"admin\", \"relation\": \"exists\"}" +
                "  ],":"")
                +   "\"buttons\": [{\"id\": \"okId\", \"action\": \"OK\", \"text\": \"OK\", \"icon\":\"https://www.123-stickers.com/6579-6950-thickbox/sticker-toad-youpi.jpg\"} ],"
                +   "\"contents\": {\"en\": \""+englishMessage + "\",\"fr\": \""+frenchMessage + "\"},"
                +   "\"headings\": {\"en\": \""+englishTitle+"\",\"fr\": \""+frenchTitle+"\"},"
                +   "\"large_icon\": \"https://www.123-stickers.com/6579-6950-thickbox/sticker-toad-youpi.jpg\""
                + "}";

            System.out.println("strJsonBody:\n" + strJsonBody);

            byte[] sendBytes = strJsonBody.getBytes("UTF-8");
            con.setFixedLengthStreamingMode(sendBytes.length);

            OutputStream outputStream = con.getOutputStream();
            outputStream.write(sendBytes);

            int httpResponse = con.getResponseCode();
            System.out.println("httpResponse: " + httpResponse);

            if (  httpResponse >= HttpURLConnection.HTTP_OK
                && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                scanner.close();
            }
            else {
                Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
                jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                scanner.close();
            }
            System.out.println("jsonResponse:\n" + jsonResponse);

        } catch(Throwable t) {
            t.printStackTrace();
        }
    }

    public void sendPushNotification(Long userId, String englishTitle, String frenchTitle, String englishMessage, String frenchMessage ) {
        sendPushNotification(userId,englishTitle,frenchTitle,englishMessage,frenchMessage,false);
    }

    public void switchTagAdmin(Long userId, boolean isAdmin ) {
        try {
            String jsonResponse;

            URL url = new URL("https://onesignal.com/api/v1/apps/aa9cbc7f-2910-4afe-9cec-ac799f760b8f/users/"+userId+"");
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setDoInput(true);

            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Authorization", "Basic \u003cNTZkMWY5NGMtOWRiYy00NjRiLTk5ZTYtYmJiNzIzZmU2YThj\u003e");
            con.setRequestMethod("PUT");



            String strJsonBody = "{"
                +   "\"tags\": {\"admin\": \""+(isAdmin?isAdmin:"")+"\"}"
                + "}";

            System.out.println("strJsonBody:\n" + strJsonBody);

            byte[] sendBytes = strJsonBody.getBytes("UTF-8");
            con.setFixedLengthStreamingMode(sendBytes.length);

            OutputStream outputStream = con.getOutputStream();
            outputStream.write(sendBytes);

            int httpResponse = con.getResponseCode();
            System.out.println("httpResponse: " + httpResponse);

            if (  httpResponse >= HttpURLConnection.HTTP_OK
                && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                scanner.close();
            }
            else {
                Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
                jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                scanner.close();
            }
            System.out.println("jsonResponse:\n" + jsonResponse);

        } catch(Throwable t) {
            t.printStackTrace();
        }
    }

}
