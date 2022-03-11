package com.example.quickweather.Mapper;

import com.example.quickweather.Data.Model.DailyWeatherForecast;
import com.example.quickweather.Data.Model.NetworkWeatherDetails;
import com.example.quickweather.Data.Model.NetworkWeatherDetails.Daily;

public class DailyMapper implements BaseMapper<Daily, DailyWeatherForecast> {
    @Override
    public DailyWeatherForecast mapFromEntity(Daily daily) {
        return new DailyWeatherForecast(
                daily.getDt(),
                daily.getWeather().get(0).getIcon(),
                (int) daily.getDailyTemp().getMin(),
                (int) daily.getDailyTemp().getMax()
        );
    }
}
