package com.example.quickweather.Ui;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.example.quickweather.Data.Model.NetworkWeatherDetails;
import com.example.quickweather.Data.Model.NetworkWeatherDetails.Current;
import com.example.quickweather.Data.Model.NetworkWeatherDetails.Hourly;
import com.example.quickweather.Data.Model.NetworkWeatherDetails.Daily;
import com.example.quickweather.Data.Source.Local.Entity.DBWeatherDetails;
import com.example.quickweather.Data.Source.Repository.WeatherRepository;

import java.util.ArrayList;
import java.util.List;


public class WeatherViewModel extends AndroidViewModel  {

    private static final String TAG = WeatherViewModel.class.getSimpleName();

    private WeatherRepository repository;

    private LiveData<DBWeatherDetails> currDBData;
    private LiveData<List<DBWeatherDetails>> hourlyDBData;
    private LiveData<List<DBWeatherDetails>> dailyDBData;

    public WeatherViewModel(@NonNull Application application) {
        super(application);
        repository = new WeatherRepository(application);

        currDBData = repository.getCurrDBData();
        hourlyDBData = repository.getHourlyDBData();
        dailyDBData = repository.getDailyDBData();
    }

    public void updateDBWeatherData()  {

        repository.fetchRemoteWeatherData(new WeatherRemoteApiCallback() {
            @Override
            public void onSuccess(NetworkWeatherDetails weatherDetails) {
                Log.e(TAG, "onSuccess: GOTTTT DATAAAAAA");
                deleteAllWeatherData();
                insertDBWeatherData(weatherDetails);
            }
        });

    }

    private void insertDBWeatherData(NetworkWeatherDetails weatherDetails) {

        List<DBWeatherDetails> dbWeatherDetailsList = new ArrayList<>();
//        long timestamp, int weatherId, Boolean isHourly, int temp, int maxTemp, int minTemp)

        Current currRemoteData = weatherDetails.getCurrent();
        List<Hourly> hourlyRemoteData = weatherDetails.getHourly();
        List<Daily> dailyRemoteData = weatherDetails.getDaily();

        // Adding "current" data into dbWeatherDetailsList
        dbWeatherDetailsList.add(new DBWeatherDetails(
                currRemoteData.getDt(),
                currRemoteData.getWeather().get(0).getId(),
                false,
                (int) currRemoteData.getTemp(),
                (int) dailyRemoteData.get(0).getDailyTemp().getMax(),
                (int) dailyRemoteData.get(0).getDailyTemp().getMin()));

        // Adding "Hourly" data into dbWeatherDetailsList
        for(int i=1; i<hourlyRemoteData.size(); i++) {
            Hourly currHour = hourlyRemoteData.get(i);
            dbWeatherDetailsList.add(new DBWeatherDetails(
                    currHour.getDt(),
                    currHour.getWeather().get(0).getId(),
                    true,
                    (int) currHour.getTemp(),
                    -300,
                    -300
            ));
        }

        // Adding "Daily" data into dbWeatherDetailsList
        for(int i=1; i<dailyRemoteData.size(); i++) {
            Daily currDay = dailyRemoteData.get(i);
            dbWeatherDetailsList.add(new DBWeatherDetails(
                    currDay.getDt(),
                    currDay.getWeather().get(0).getId(),
                    false,
                    -300,
                    (int) currDay.getDailyTemp().getMax(),
                    (int) currDay.getDailyTemp().getMin()));
        }

        repository.insertDBWeatherData(dbWeatherDetailsList);
    }

    private void deleteAllWeatherData() {
        repository.deleteAllWeatherData();
    }

    public LiveData<DBWeatherDetails> getCurrDBData() {
        if(currDBData == null) {
            repository.getCurrDBData();
        }
        return currDBData;
    }

    public LiveData<List<DBWeatherDetails>> getHourlyDBData() {
        return hourlyDBData;
    }

    public LiveData<List<DBWeatherDetails>> getDailyDBData() {
        return dailyDBData;
    }
}
