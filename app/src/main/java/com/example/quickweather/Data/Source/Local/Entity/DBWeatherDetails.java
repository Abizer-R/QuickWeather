package com.example.quickweather.Data.Source.Local.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "weather_data_table")
public class DBWeatherDetails {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private long timestamp;

    private int weatherId;

    /**
     * Current element will be the first one
     * It will have all 3 (temp, minTemp and maxTemp)
     * It will have Hourly as False
     */

    // To differentiate between hourly and daily data
    private Boolean isHourly;

    // Hourly Weather element:
    // Has value if 'isHourly' is true, else -300
    private int currTemp;

    // Daily Weather element:
    // Has value if 'isHourly' is false, else -300
    private int maxTemp;

    // Daily Weather element:
    // Has value if 'isHourly' is false, else -300
    private int minTemp;

    public DBWeatherDetails(long timestamp, int weatherId, Boolean isHourly, int currTemp, int maxTemp, int minTemp) {
        this.timestamp = timestamp;
        this.weatherId = weatherId;
        this.isHourly = isHourly;
        this.currTemp = currTemp;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
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

    public int getWeatherId() {
        return weatherId;
    }

    public Boolean getHourly() {
        return isHourly;
    }

    public int getCurrTemp() {
        return currTemp;
    }

    public int getMaxTemp() {
        return maxTemp;
    }

    public int getMinTemp() {
        return minTemp;
    }
}
