package com.example.quickweather.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.core.app.ActivityCompat;
import androidx.core.content.SharedPreferencesKt;

import com.example.quickweather.R;

public class SharedPrefsUtil {

    public static void setSharedPrefLocation(Activity activity, double lati, double longi) {
        SharedPreferences sharedPrefs = activity.getSharedPreferences(
                activity.getString(R.string.location_preferences), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putLong(activity.getString(R.string.location_latitude_key), Double.doubleToLongBits(lati));
        editor.putLong(activity.getString(R.string.location_longitude_key), Double.doubleToLongBits(longi));
    }

    public static double getSharedPrefLatitude(Activity activity) {
        SharedPreferences prefs = activity.getSharedPreferences(
                activity.getString(R.string.location_preferences), Context.MODE_PRIVATE);
        long latitude = prefs.getLong(activity.getString(R.string.location_latitude_key), Double.doubleToLongBits(22.7196));
        return Double.longBitsToDouble(latitude);
    }

    public static double getSharedPrefLongitude(Activity activity) {
        SharedPreferences prefs = activity.getSharedPreferences(
                activity.getString(R.string.location_preferences), Context.MODE_PRIVATE);
        long longitude = prefs.getLong(activity.getString(R.string.location_longitude_key), Double.doubleToLongBits(75.8577));
        return Double.longBitsToDouble(longitude);
    }
}
