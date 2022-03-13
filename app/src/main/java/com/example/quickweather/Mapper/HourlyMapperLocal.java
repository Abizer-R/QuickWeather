package com.example.quickweather.Mapper;

import com.example.quickweather.Data.Model.HourlyWeatherForecast;
import com.example.quickweather.Data.Source.Local.Entity.DBWeatherDetails;

import java.util.ArrayList;
import java.util.List;

public class HourlyMapperLocal implements BaseMapper<List<DBWeatherDetails>, List<HourlyWeatherForecast>> {

    @Override
    public List<HourlyWeatherForecast> mapFromEntity(List<DBWeatherDetails> dbWeatherDetails) {
        List<HourlyWeatherForecast> hourlyWeatherForecasts = new ArrayList<>();
        for(int i=0; i<dbWeatherDetails.size(); i++) {

            DBWeatherDetails currHour = dbWeatherDetails.get(i);
            hourlyWeatherForecasts.add(new HourlyWeatherForecast(
                    currHour.getTimestamp(),
                    currHour.getWeatherId(),
                    currHour.getCurrTemp()
            ));
        }
        return hourlyWeatherForecasts;
    }

    @Override
    public List<DBWeatherDetails> mapToEntity(List<HourlyWeatherForecast> hourlyWeatherForecasts) {
        return null;
    }
}
