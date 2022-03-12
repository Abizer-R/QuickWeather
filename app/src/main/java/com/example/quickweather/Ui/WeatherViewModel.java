package com.example.quickweather.Ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.quickweather.Data.Model.DailyWeatherForecast;
import com.example.quickweather.Data.Model.HourlyWeatherForecast;
import com.example.quickweather.Data.Model.NetworkWeatherDetails;
import com.example.quickweather.Data.Source.Local.Entity.DBDailyWeather;
import com.example.quickweather.Data.Source.Local.Entity.DBHourlyWeather;
import com.example.quickweather.Data.Source.Repository.WeatherRepository;
import com.example.quickweather.Mapper.DailyMapperLocal;
import com.example.quickweather.Mapper.DailyMapperRemote;
import com.example.quickweather.Mapper.HourlyMapperLocal;
import com.example.quickweather.Mapper.HourlyMapperRemote;

import java.util.List;

public class WeatherViewModel extends AndroidViewModel  {

    private WeatherRepository repository;
    private LiveData<List<DBHourlyWeather>> hourlyWeather;
    private LiveData<List<DBDailyWeather>> dailyWeather;

    public WeatherViewModel(@NonNull Application application) {
        super(application);
        repository = new WeatherRepository(application);
        hourlyWeather = repository.getHourlyData();
        dailyWeather = repository.getDailyData();
    }

    public void insertAllHourlyData(List<NetworkWeatherDetails.Hourly> hourlyList)  {

        HourlyMapperLocal localMapper = new HourlyMapperLocal();
        HourlyMapperRemote remoteMapper = new HourlyMapperRemote();

        List<HourlyWeatherForecast> hourlyWeatherForecasts = remoteMapper.mapFromEntity(hourlyList);
        repository.insertHourlyData(localMapper.mapToEntity(hourlyWeatherForecasts));
    }

    public void deleteAllHourlyData() {
        repository.deleteAllHourlyData();
    }

    public LiveData<List<DBHourlyWeather>> getHourlyWeather() {
        return hourlyWeather;
    }

    public void insertAllDailyData(List<NetworkWeatherDetails.Daily> dailyList) {

        DailyMapperLocal localMapper = new DailyMapperLocal();
        DailyMapperRemote remoteMapper = new DailyMapperRemote();

        List<DailyWeatherForecast> dailyWeatherForecasts = remoteMapper.mapFromEntity(dailyList);
        repository.insertDailyData(localMapper.mapToEntity(dailyWeatherForecasts));
    }

    public void deleteAllDailyData() {
        repository.deleteAllDailyData();
    }

    public LiveData<List<DBDailyWeather>> getDailyWeather() {
        return dailyWeather;
    }
}
