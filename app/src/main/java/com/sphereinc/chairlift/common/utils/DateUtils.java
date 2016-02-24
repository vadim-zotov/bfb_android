package com.sphereinc.chairlift.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);

    public static String getDateString(Date date) {
        if (date != null) {
            return dateFormat.format(date);
        } else {
            return "";
        }
    }

}
