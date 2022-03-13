package com.example.quickweather.Data.Model;

public class HourlyWeatherForecast {

    private long timestamp;

    private int weatherId;

    private int temp;

    public HourlyWeatherForecast(long timestamp, int iconId, int temp) {
        this.timestamp = timestamp;
        this.weatherId = iconId;
        this.temp = temp;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public int getWeatherId() {
        return weatherId;
    }

    public int getTemp() {
        return temp;
    }
}
