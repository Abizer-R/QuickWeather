package com.example.quickweather.Mapper;

import com.example.quickweather.Data.Model.HourlyWeatherForecast;
import com.example.quickweather.Data.Source.Local.Entity.DBHourlyWeather;

import java.util.ArrayList;
import java.util.List;

public class HourlyMapperLocal implements BaseMapper<List<DBHourlyWeather>, List<HourlyWeatherForecast>> {

    @Override
    public List<HourlyWeatherForecast> mapFromEntity(List<DBHourlyWeather> dbHourlyWeathers) {
        List<HourlyWeatherForecast> hourlyWeatherForecasts = new ArrayList<>();
        for(int i=0; i<dbHourlyWeathers.size(); i++) {

            DBHourlyWeather currHour = dbHourlyWeathers.get(i);
            hourlyWeatherForecasts.add(new HourlyWeatherForecast(
                    currHour.getTimestamp(),
                    currHour.getIconId(),
                    currHour.getTemp()
            ));
        }
        return hourlyWeatherForecasts;
    }

    @Override
    public List<DBHourlyWeather> mapToEntity(List<HourlyWeatherForecast> hourlyWeatherForecasts) {

        List<DBHourlyWeather> dbHourlyWeathers = new ArrayList<>();
        for(int i=0; i<hourlyWeatherForecasts.size(); i++) {

            HourlyWeatherForecast currHour = hourlyWeatherForecasts.get(i);
            dbHourlyWeathers.add(new DBHourlyWeather(
                    currHour.getTimestamp(),
                    currHour.getIconId(),
                    currHour.getTemp()
            ));
        }
        return dbHourlyWeathers;
    }
}
