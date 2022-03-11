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

//    private static WeatherDetails weatherDetails = null;
//
//    //      TODO: Remove context if you want to, Context is given to give toast
//    public static WeatherDetails getWeatherDetails(Context context) {
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        WeatherDetailsApi weatherDetailsApi = retrofit.create(WeatherDetailsApi.class);
//
//        // lat=22.7196&lon=75.8577&units=metric&exclude=minutely,alerts&appid=6b40419e55e87b4c7eb082eca5f50dab
//        Call<WeatherDetails> weatherDetailCall = weatherDetailsApi.getWeatherDetails(
//                22.7196,
//                75.8577,
//                "metric",
//                "minutely,alerts",
//                "6b40419e55e87b4c7eb082eca5f50dab"
//        );
//
//        weatherDetailCall.enqueue(new Callback<WeatherDetails>() {
//            @Override
//            public void onResponse(Call<WeatherDetails> call, Response<WeatherDetails> response) {
//
//                if(!response.isSuccessful()) {
//                    // TODO: Remove Toast in the end
//                    Toast.makeText(context, "Response code: " + response.code(), Toast.LENGTH_SHORT).show();
//                    weatherDetails = null;
//                    return;
//                }
//
//                weatherDetails = response.body();
//            }
//
//            @Override
//            public void onFailure(Call<WeatherDetails> call, Throwable t) {
//                weatherDetails = null;
//                // TODO: Remove Toast in the end
//                Toast.makeText(context, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        return weatherDetails;
//    }
}
