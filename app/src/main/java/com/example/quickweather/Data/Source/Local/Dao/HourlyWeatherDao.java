package com.example.quickweather.Data.Source.Local.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.quickweather.Data.Source.Local.Entity.DBHourlyWeather;

import java.util.List;

@Dao
public interface HourlyWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DBHourlyWeather hourlyWeather);

    @Query("DELETE FROM hourly_weather_data")
    void deleteAllHourlyData();

    @Query("SELECT * FROM hourly_weather_data ORDER BY timestamp ASC")
    LiveData<List<DBHourlyWeather>> getLocalHourlyData();
}
