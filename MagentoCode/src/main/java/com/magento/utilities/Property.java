package com.magento.utilities;

import com.magento.interfaces.Constants;
import com.magento.loggers.Loggers;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class Property implements Constants {

    /**
     * Getting the Property data by using the key
     *
     * @param key
     * @return String
     */
    public static String getProperty(String key) {

        try {
            // Creating the Property Object and loading the properties file
            Properties prop = new Properties();
            prop.load(new FileInputStream(new File(PROP_PATH)));

            // Returning the data fetched
            return prop.getProperty(key);
        } catch (Exception e) {
            Loggers.getLogger().error(e.getMessage());
        }
        return null;
    }
}
