package com.example.quickweather.Data.Source.Remote;

import com.example.quickweather.Data.Model.NetworkWeatherDetails;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

// https://api.openweathermap.org/data/2.5/onecall?lat=22.7196&lon=75.8577&units=metric&exclude=minutely,alerts&appid=6b40419e55e87b4c7eb082eca5f50dab

// BASE URL: https://api.openweathermap.org/data/2.5/

// VAR: onecall?lat=22.7196&lon=75.8577&units=metric&exclude=minutely,alerts&appid=6b40419e55e87b4c7eb082eca5f50dab

public interface WeatherDetailsApi {

    @GET("onecall")
    Call<NetworkWeatherDetails> getWeatherDetails(
            @Query("lat") double lat,
            @Query("lon") double lon,
            @Query("units") String unit,
            @Query("exclude") String exclude,
            @Query("appid") String API_KEY
    );
}
