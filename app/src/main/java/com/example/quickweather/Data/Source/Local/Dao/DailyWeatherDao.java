package com.example.quickweather.Data.Source.Local.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.quickweather.Data.Source.Local.Entity.DBDailyWeather;

import java.util.List;

@Dao
public interface DailyWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DBDailyWeather dailyWeather);

    @Query("DELETE FROM daily_weather_data")
    void deleteAllDailyData();

    @Query("SELECT * FROM daily_weather_data ORDER BY timestamp ASC")
    LiveData<List<DBDailyWeather>> getLocalDailyData();
}
