package com.example.quickweather.Data.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NetworkWeatherDetails {

    private Current current;

    private List<Hourly> hourly;

    private List<Daily> daily;

    public Current getCurrent() {
        return current;
    }

    public List<Hourly> getHourly() {
        return hourly;
    }

    public List<Daily> getDaily() {
        return daily;
    }

    public class Current {

        private long dt;

        private long sunrise;

        private long sunset;

        private double temp;

        private List<Weather> weather;

        public long getDt() {
            return dt;
        }

        public long getSunrise() {
            return sunrise;
        }

        public long getSunset() {
            return sunset;
        }

        public double getTemp() {
            return temp;
        }

        public List<Weather> getWeather() {
            return weather;
        }
    }

    public class Hourly {

        private long dt;

        private double temp;

        private List<Weather> weather;

        public long getDt() {
            return dt;
        }

        public double getTemp() {
            return temp;
        }

        public List<Weather> getWeather() {
            return weather;
        }
    }

    public class Daily {

        private long dt;

        private long sunrise;

        private long sunset;

        @SerializedName("temp")
        private DailyTemp dailyTemp;

        private List<Weather> weather;

        public long getDt() {
            return dt;
        }

        public long getSunrise() {
            return sunrise;
        }

        public long getSunset() {
            return sunset;
        }

        public DailyTemp getDailyTemp() {
            return dailyTemp;
        }

        public List<Weather> getWeather() {
            return weather;
        }

        public class DailyTemp {

            private double min;

            private double max;

            public double getMin() {
                return min;
            }

            public double getMax() {
                return max;
            }
        }
    }

    public class Weather {

        private int id;

        private String description;

        private String icon;

        public int getId() {
            return id;
        }

        public String getDescription() {
            return description;
        }

        public String getIcon() {
            return icon;
        }
    }
}
