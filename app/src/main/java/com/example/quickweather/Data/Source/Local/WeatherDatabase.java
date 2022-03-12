package com.example.quickweather.Data.Source.Local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.quickweather.Data.Source.Local.Dao.DailyWeatherDao;
import com.example.quickweather.Data.Source.Local.Dao.HourlyWeatherDao;
import com.example.quickweather.Data.Source.Local.Entity.DBDailyWeather;
import com.example.quickweather.Data.Source.Local.Entity.DBHourlyWeather;

@Database(entities = {DBHourlyWeather.class, DBDailyWeather.class}, version = 2)
public abstract class WeatherDatabase extends RoomDatabase {

    // We create this variable because we have to turn this class into a "singleton"
    // i.e. Now, we can't create multiple instances of this database. Instead we we use same instance everywhere
    private static WeatherDatabase instance;

    public abstract HourlyWeatherDao hourlyDataDao();
    public abstract DailyWeatherDao dailyWeatherDao();

    /*
        We can get a handle to the database instance from this method.
        'SYNCHRONIZED' means that only one thread at a time can access this method.
        This way we don't accidentally create two instances of this database when two different
        threads try to access this method at the same time
     */
    public static synchronized  WeatherDatabase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    WeatherDatabase.class,"weather_database")
                    .fallbackToDestructiveMigration()   /* When we increment to a new version of the database, we have to tell room how to migrate to the new schema */
                    .build();
        }
        return instance;
    }
}
