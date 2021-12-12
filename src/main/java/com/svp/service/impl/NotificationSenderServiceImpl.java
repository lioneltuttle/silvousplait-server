package com.svp.service.impl;

import com.svp.domain.ProRequest;
import com.svp.service.NotificationSenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

@Service
@Transactional
public class NotificationSenderServiceImpl implements NotificationSenderService {

    private final Logger log = LoggerFactory.getLogger(NotificationSenderServiceImpl.class);


    @Override
    public void sendPushNotification(Long userId, String englishTitle, String frenchTitle, String englishMessage, String frenchMessage) {
        try {


            String strJsonBody = "{"
                +   "\"app_id\": \"5b325129-7920-495f-9fe4-737c299bda29\","
                +   "\"channel_for_external_user_ids\": \"push\","
                +   "\"data\": {\"foo\": \"bar\"},"
                +   "\"action\": \"OK\","
                + "\"filters\": [{\"field\": \"tag\", \"key\": \"admin\", \"relation\": \"=\", \"value\":\""+3+"\"} ],"
                +   "\"buttons\": [{\"id\": \"okId\", \"action\": \"OK\", \"text\": \"OK\", \"icon\":\"https://www.123-stickers.com/6579-6950-thickbox/sticker-toad-youpi.jpg\"} ],"
                +   "\"contents\": {\"en\": \""+englishMessage + "\",\"fr\": \""+frenchMessage + "\"},"
                +   "\"headings\": {\"en\": \""+englishTitle+"\",\"fr\": \""+frenchTitle+"\"},"
                +   "\"large_icon\": \"https://www.123-stickers.com/6579-6950-thickbox/sticker-toad-youpi.jpg\""
                + "}";

            System.out.println("strJsonBody:\n" + strJsonBody);

            byte[] sendBytes = strJsonBody.getBytes("UTF-8");

            HttpURLConnection con = getConnection();
            con.setFixedLengthStreamingMode(sendBytes.length);

            OutputStream outputStream = con.getOutputStream();
            outputStream.write(sendBytes);

            manageReturn(con);

        } catch(Throwable t) {
            t.printStackTrace();
        }
    }

    public void sendWorkRequest(List<Long> proIds){

        final String ENG_MESSAGE = "A new work request ";
        final String ENG_TITLE = "Work Request";
        final String FR_MESSAGE = "Une nouvelle demande de travail près de vous, dispo?";
        final String FR_TITLE = "Nouveau Job";
        try {
            String strJsonBody = new StringBuilder("{")
                .append("\"app_id\": \"5b325129-7920-495f-9fe4-737c299bda29\", \"channel_for_external_user_ids\": \"push\",")
                .append("\"data\": {\"foo\": \"bar\"},")
                .append("\"action\": \"OK\",")
                .append(filterOnIds(proIds))
                // buttons
                .append("\"buttons\": [{\"id\": \"okId\", \"action\": \"OK\", \"text\": \"OK\", \"icon\":\"https://icon-library.net/images/ok-icon/ok-icon-9.jpg\"}, ")
                .append("{\"id\": \"nokId\", \"action\": \"NOK\", \"text\": \"KO\", \"icon\":\"https://icon-library.net/images/ko-icon/ko-icon-4.jpg\"}],")
                //messages
                .append("\"contents\": {\"en\": \""+ENG_MESSAGE + "\",\"fr\": \""+FR_MESSAGE + "\"},")
                //headings
                .append("\"headings\": {\"en\": \""+ENG_TITLE+"\",\"fr\": \""+FR_TITLE+"\"},")
                //icons
                .append("\"large_icon\": \"https://www.123-stickers.com/6579-6950-thickbox/sticker-toad-youpi.jpg\"")
                .append("}").toString();

            System.out.println("strJsonBody:\n" + strJsonBody);

            byte[] sendBytes = strJsonBody.getBytes("UTF-8");

            HttpURLConnection con = getConnection();
            con.setFixedLengthStreamingMode(sendBytes.length);

            OutputStream outputStream = con.getOutputStream();
            outputStream.write(sendBytes);

            manageReturn(con);

        } catch(Throwable t) {
            t.printStackTrace();
        }
    }

    private HttpURLConnection getConnection() throws MalformedURLException, IOException {
        URL url = new URL("https://onesignal.com/api/v1/notifications");
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setUseCaches(false);
        con.setDoOutput(true);
        con.setDoInput(true);

        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        con.setRequestProperty("Authorization", "Basic \u003cZGQ4NDhiZjktNDFlZS00YTE5LWEwYWYtN2I3YTdhMDVkMGI2\u003e");
        con.setRequestMethod("POST");
        return con;
    }

    private void manageReturn(HttpURLConnection con) throws IOException{
        String jsonResponse;
        int httpResponse = con.getResponseCode();
        System.out.println("httpResponse: " + httpResponse);

        if (  httpResponse >= HttpURLConnection.HTTP_OK
            && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
            Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
            scanner.close();
        } else {
            Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
            scanner.close();
        }
        System.out.println("jsonResponse:\n" + jsonResponse);
    }

    private String filterOnIds(List<Long> ids){
        final StringBuilder sb = new StringBuilder("\"filters\": [");
        ids.forEach( id ->  sb.append("{\"field\": \"tag\", \"key\": \"work\", \"relation\": \"=\", \"value\":\"").append(id).append("\"},{\"operator\": \"OR\"},  ") );
        sb.append("],");
        return sb.toString();
    }

}
