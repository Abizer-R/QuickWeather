package com.example.quickweather.Utils;

import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtil {

    public static String getLocalTime(long timestamp) {

        Date timeD = new Date(timestamp * 1000);
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm aa");

        return dateFormat.format(timeD);
    }

    public static String getLocalDate(long timestamp) {

        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(timestamp * 1000);

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM dd");

        return dateFormat.format(cal.getTime());
    }

    public static boolean isDay(long timestamp) {

        Date timeD = new Date(timestamp * 1000);
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH");

        int t = Integer.valueOf(dateFormat.format(timeD));

        if(6 <= t && t <= 16)
            return true;
        else
            return false;
    }
//    public static long getCurrentTimeInMillis() {
//        return System.currentTimeMillis();
//    }
}
