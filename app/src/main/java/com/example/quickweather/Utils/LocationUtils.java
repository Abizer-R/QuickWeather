package com.example.quickweather.Utils;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.location.LocationManagerCompat;

import com.example.quickweather.Ui.MainActivity;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;

public class LocationUtils {

    public static final int REQUEST_LOCATION = 825;
    private static final String TAG = LocationUtils.class.getSimpleName();

    public static void turnOnGps(Activity context) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("To get weather data for your current location, you need to enable gps.").setCancelable(false);

        builder.setPositiveButton("Enable gps", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                SharedPrefsUtil.ignoreGps = false;
            }
        });

        builder.setNegativeButton("Ignore", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                SharedPrefsUtil.ignoreGps = true;
                Snackbar snackbar = Snackbar.make(context.findViewById(android.R.id.content),
                        "Weather data of the last known location will be shown.", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    // TODO: This one has to update the SharedPrefs lat and long, So later it will return "void".
    public static void updateLocation(Activity activity, FusedLocationProviderClient fusedLocationProviderClient) {
        if(ActivityCompat.checkSelfPermission(
                activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                        activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
            return;
        }

        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(activity, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null) {
                    SharedPrefsUtil.setSharedPrefLocation(activity, location.getLatitude(), location.getLongitude());
                }
            }
        });
    }



    public static String getAddress(Context context, double lati, double longi) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(context, Locale.getDefault());

        String city = "";
        try {
            addresses = geocoder.getFromLocation(lati, longi, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            city = addresses.get(0).getLocality();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.e(TAG, "getAddress: " + city);
        return city;
    }
}
