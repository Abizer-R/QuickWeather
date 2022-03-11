package com.example.quickweather.Mapper;

import com.example.quickweather.Data.Model.HourlyWeatherForecast;
import com.example.quickweather.Data.Model.NetworkWeatherDetails;
import com.example.quickweather.Data.Model.NetworkWeatherDetails.Hourly;

import java.util.ArrayList;
import java.util.List;

public class HourlyMapper implements BaseMapper<Hourly, HourlyWeatherForecast>{
    @Override
    public HourlyWeatherForecast mapFromEntity(Hourly hourly) {

        return new HourlyWeatherForecast(
                hourly.getDt(),
                hourly.getWeather().get(0).getIcon(),
                (int) hourly.getTemp()
        );
    }


//  HourlyWeatherForecast(long timestamp, String iconId, int temp)

}
