package com.example.quickweather.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;

import androidx.core.net.ConnectivityManagerCompat;

import com.example.quickweather.R;

import java.util.Calendar;

public class WeatherUtils {

    public static int getIconResourceId(int weatherId, long timestamp) {
        if(200 <= weatherId && weatherId <= 232)
            return R.drawable.ic_11d;

        else if(300 <= weatherId && weatherId <= 321)
            return R.drawable.ic_09d;

        else if(500 <= weatherId && weatherId <= 504)
            return R.drawable.ic_10d;

        else if(weatherId == 511 || (600 <= weatherId && weatherId <= 622))
            return R.drawable.ic_13d;

        else if(520 <= weatherId && weatherId <= 531)
            return R.drawable.ic_09d;

        else if(701 <= weatherId && weatherId <= 781)
            return R.drawable.ic_50d;

        else if(weatherId == 800) {
            if(DateTimeUtil.isDay(timestamp))
                return R.drawable.ic_01d;
            else
                return R.drawable.ic_01n;
        }

        else if(weatherId == 801) {
            if(DateTimeUtil.isDay(timestamp))
                return R.drawable.ic_02d;
            else
                return R.drawable.ic_02n;
        }

        else if(weatherId == 802)
            return R.drawable.ic_03d;

        else
            return R.drawable.icons8_onedrive_96;
    }

    public static String getDescription(int weatherId) {

        if(200 <= weatherId && weatherId <= 232)
            return "Thunderstorm";

        else if(300 <= weatherId && weatherId <= 321)
            return "Drizzle";

        else if(500 <= weatherId && weatherId <= 531)
            return "Rain";

        else if(600 <= weatherId && weatherId <= 622)
            return "Snow";

        else if(701 <= weatherId && weatherId <= 781) {
            switch (weatherId) {
                case 711:
                    return "Smoke";
                case 721:
                    return "Haze";
                case 731:
                case 761:
                    return "Dust";
                case 741:
                    return "Fog";
                case 751:
                    return "Sand";
                case 762:
                    return "Ash";
                case 771:
                    return "Squall";
                case 781:
                    return "tornado";
                default:
                    return "Mist";
            }
        }

        else if(weatherId == 800)
            return "Clear Sky";

        else if(weatherId == 801)
            return "few clouds: 11-25%";

        else if(weatherId == 802)
            return "scattered clouds: 25-50%";

        else if(weatherId == 803)
            return "broken clouds: 51-84%";
        else
            return "overcast clouds: 85-100%";
    }

    public static String getCurrentMinMaxTemp (int maxTemp, int minTemp) {
        return maxTemp + "° / " + minTemp + "°";
    }

    public static String getLastWeatherUpdated(long timestamp) {
        return "Last updated: " + DateTimeUtil.getLocalTime(timestamp);
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

}
