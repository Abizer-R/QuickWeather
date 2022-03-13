package com.example.quickweather.Ui;

import com.example.quickweather.Data.Model.NetworkWeatherDetails;

public interface WeatherRemoteApiCallback {

    void onSuccess(NetworkWeatherDetails weatherDetails);
}
