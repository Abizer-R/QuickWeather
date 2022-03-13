package com.example.quickweather.Mapper;

import com.example.quickweather.Data.Model.DailyWeatherForecast;
import com.example.quickweather.Data.Source.Local.Entity.DBDailyWeather;

import java.util.ArrayList;
import java.util.List;

public class DailyMapperLocal implements BaseMapper<List<DBDailyWeather>, List<DailyWeatherForecast>> {

    @Override
    public List<DailyWeatherForecast> mapFromEntity(List<DBDailyWeather> dailyWeathers) {
        List<DailyWeatherForecast> dailyWeatherForecasts = new ArrayList<>();
        for(int i=0; i<dailyWeathers.size(); i++) {

            DBDailyWeather currDay = dailyWeathers.get(i);
            dailyWeatherForecasts.add(new DailyWeatherForecast(
                    currDay.getTimestamp(),
                    currDay.getIconId(),
                    currDay.getTemp_min(),
                    currDay.getTemp_max()
            ));
        }
        return dailyWeatherForecasts;
    }

    @Override
    public List<DBDailyWeather> mapToEntity(List<DailyWeatherForecast> dailyWeatherForecasts) {
        List<DBDailyWeather> dbDailyWeathers =new ArrayList<>();
        for(int i=0; i<dailyWeatherForecasts.size(); i++) {

            DailyWeatherForecast currDay = dailyWeatherForecasts.get(i);
            dbDailyWeathers.add(new DBDailyWeather(
                    currDay.getTimestamp(),
                    currDay.getIconId(),
                    currDay.getTemp_min(),
                    currDay.getTemp_max()
            ));
        }
        return dbDailyWeathers;
    }
}