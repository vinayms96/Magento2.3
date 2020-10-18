package com.magento.modules;

import com.magento.loggers.Loggers;

import java.net.HttpURLConnection;
import java.net.URL;

public class LinkCheck {

    public static boolean checkUrl(String link) {
        try {
            URL url = new URL(link);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            int responseCode = httpURLConnection.getResponseCode();
            return responseCode == 200;
        } catch (Exception e) {
            Loggers.getLogger().error(e.getStackTrace());
        }
        return false;
    }

}
