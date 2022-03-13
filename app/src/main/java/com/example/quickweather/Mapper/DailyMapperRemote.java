package com.example.quickweather.Mapper;

import com.example.quickweather.Data.Model.DailyWeatherForecast;
import com.example.quickweather.Data.Model.NetworkWeatherDetails;
import com.example.quickweather.Data.Model.NetworkWeatherDetails.Daily;

import java.util.ArrayList;
import java.util.List;

public class DailyMapperRemote implements BaseMapper<List<Daily>, List<DailyWeatherForecast>> {

    @Override
    public List<DailyWeatherForecast> mapFromEntity(List<Daily> dailyList) {
        List<DailyWeatherForecast> dailyWeatherForecasts = new ArrayList<>();
        for (int i=0; i<dailyList.size(); i++) {

            Daily currDay = dailyList.get(i);
            dailyWeatherForecasts.add(new DailyWeatherForecast(
                    currDay.getDt(),
                    currDay.getWeather().get(0).getIcon(),
                    (int) currDay.getDailyTemp().getMin(),
                    (int) currDay.getDailyTemp().getMax()
            ));
        }
        return dailyWeatherForecasts;
    }

    @Override
    public List<Daily> mapToEntity(List<DailyWeatherForecast> dailyWeatherForecasts) {
        return null;
    }
}
