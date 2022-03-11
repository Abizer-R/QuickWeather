package com.example.quickweather.Data.Model;

public class HourlyWeatherForecast {

    private long timestamp;

    private String iconId;

    private int temp;

    public HourlyWeatherForecast(long timestamp, String iconId, int temp) {
        this.timestamp = timestamp;
        this.iconId = iconId;
        this.temp = temp;
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
