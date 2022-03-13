package com.example.quickweather.Data.Source.Local.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.quickweather.Data.Source.Local.Entity.DBWeatherDetails;

import java.util.List;

@Dao
public interface DBWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DBWeatherDetails weatherDetails);

    @Query("DELETE FROM weather_data_table")
    void deleteAllDBWeatherData();

    @Query("SELECT * FROM weather_data_table WHERE currTemp != -300 AND maxTemp != -300")
    LiveData<DBWeatherDetails> getCurrentDBWeatherData();

    @Query("SELECT * FROM weather_data_table WHERE isHourly = 1")
    LiveData<List<DBWeatherDetails>> getHourlyDBWeatherData();

    @Query("SELECT * FROM weather_data_table WHERE isHourly = 0 AND currTemp == -300")
    LiveData<List<DBWeatherDetails>> getDailyDBWeatherData();
}
