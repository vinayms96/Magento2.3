package com.magento.modules;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DatePicker {
    private static Date date;
    private static SimpleDateFormat simpleDateFormat;

    public static String getDateTime() {
        date = new Date();
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return simpleDateFormat.format(date);
    }
}
