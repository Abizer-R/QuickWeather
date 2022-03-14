package com.example.quickweather.Data.Source.Remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {

    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";

    private static WeatherDetailsApi weatherDetailsApi = null;

    public static WeatherDetailsApi getWeatherApiClient() {

        if(weatherDetailsApi == null) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            weatherDetailsApi = retrofit.create(WeatherDetailsApi.class);
        }

        return weatherDetailsApi;
    }
}
