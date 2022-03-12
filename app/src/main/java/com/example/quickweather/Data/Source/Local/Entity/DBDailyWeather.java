package com.example.quickweather.Data.Source.Local.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "daily_weather_data")
public class DBDailyWeather {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private long timestamp;

    private String iconId;

    private int temp_min;

    private int temp_max;

    public DBDailyWeather(long timestamp, String iconId, int temp_min, int temp_max) {
        this.timestamp = timestamp;
        this.iconId = iconId;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
    }

    public int getId() {
        return id;
    }

    // Only value we don't have in our constructor
    public void setId(int id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getIconId() {
        return iconId;
    }

    public int getTemp_min() {
        return temp_min;
    }

    public int getTemp_max() {
        return temp_max;
    }
}
