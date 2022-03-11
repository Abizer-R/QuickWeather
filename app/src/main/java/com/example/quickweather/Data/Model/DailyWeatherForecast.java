package com.example.quickweather.Data.Model;

public class DailyWeatherForecast {

    private long timestamp;

    private String iconId;

    private int temp_min;

    private int temp_max;

    public DailyWeatherForecast(long timestamp, String iconId, int temp_min, int temp_max) {
        this.timestamp = timestamp;
        this.iconId = iconId;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
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
