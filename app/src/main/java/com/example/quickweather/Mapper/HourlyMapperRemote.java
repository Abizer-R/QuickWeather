package com.example.quickweather.Mapper;

import com.example.quickweather.Data.Model.HourlyWeatherForecast;
import com.example.quickweather.Data.Model.NetworkWeatherDetails;
import com.example.quickweather.Data.Model.NetworkWeatherDetails.Hourly;

import java.util.ArrayList;
import java.util.List;

public class HourlyMapperRemote implements BaseMapper<List<Hourly>, List<HourlyWeatherForecast>>{

    @Override
    public List<HourlyWeatherForecast> mapFromEntity(List<Hourly> hourlyList) {
        List<HourlyWeatherForecast> hourlyWeatherForecasts = new ArrayList<>();

        for(int i=0; i<hourlyList.size(); i++) {
            Hourly currHour = hourlyList.get(i);
            hourlyWeatherForecasts.add(new HourlyWeatherForecast(
                            currHour.getDt(),
                            currHour.getWeather().get(0).getIcon(),
                            (int) currHour.getTemp())
            );
        }
        return hourlyWeatherForecasts;
    }

    @Override
    public List<Hourly> mapToEntity(List<HourlyWeatherForecast> hourlyWeatherForecasts) {
        return null;
    }
}
