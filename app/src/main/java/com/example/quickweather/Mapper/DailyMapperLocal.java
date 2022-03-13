package com.example.quickweather.Mapper;

import com.example.quickweather.Data.Model.DailyWeatherForecast;
import com.example.quickweather.Data.Source.Local.Entity.DBWeatherDetails;

import java.util.ArrayList;
import java.util.List;

public class DailyMapperLocal implements BaseMapper<List<DBWeatherDetails>, List<DailyWeatherForecast>> {

    @Override
    public List<DailyWeatherForecast> mapFromEntity(List<DBWeatherDetails> dbWeatherDetails) {
        List<DailyWeatherForecast> dailyWeatherForecasts = new ArrayList<>();
        for(int i=0; i<dbWeatherDetails.size(); i++) {

            DBWeatherDetails currDay = dbWeatherDetails.get(i);
            dailyWeatherForecasts.add(new DailyWeatherForecast(
                    currDay.getTimestamp(),
                    currDay.getWeatherId(),
                    currDay.getMinTemp(),
                    currDay.getMaxTemp()));
        }
        return dailyWeatherForecasts;
    }

    @Override
    public List<DBWeatherDetails> mapToEntity(List<DailyWeatherForecast> dailyWeatherForecasts) {
        return null;
    }
}