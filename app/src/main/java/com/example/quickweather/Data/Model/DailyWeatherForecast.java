package com.example.quickweather.Data.Model;

public class DailyWeatherForecast {

    private long timestamp;

    private int weatherId;

    private int temp_min;

    private int temp_max;

    public DailyWeatherForecast(long timestamp, int iconId, int temp_min, int temp_max) {
        this.timestamp = timestamp;
        this.weatherId = iconId;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public int getWeatherId() {
        return weatherId;
    }

    public int getTemp_min() {
        return temp_min;
    }

    public int getTemp_max() {
        return temp_max;
    }
}
