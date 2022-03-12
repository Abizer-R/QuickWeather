package com.example.quickweather.Data.Source.Local.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "hourly_weather_data")
public class DBHourlyWeather {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private long timestamp;

    private String iconId;

    private int temp;

    public DBHourlyWeather(long timestamp, String iconId, int temp) {
        this.timestamp = timestamp;
        this.iconId = iconId;
        this.temp = temp;
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

    public int getTemp() {
        return temp;
    }
}
