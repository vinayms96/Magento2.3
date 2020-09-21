package com.magento.pickers;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DatePicker {
    private static Date date;
    private static SimpleDateFormat simpleDateFormat;

    /**
     * Get the Date and Time
     *
     * @return String
     */
    public static String getDateTime() {
        date = new Date();
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return simpleDateFormat.format(date);
    }

    /**
     * Get the Day and Date for Mail
     *
     * @return String
     */
    public static String getDayDate() {
        date = new Date();
        simpleDateFormat = new SimpleDateFormat("E yyyy-MM-dd HH:mm");
        return simpleDateFormat.format(date);
    }
}
