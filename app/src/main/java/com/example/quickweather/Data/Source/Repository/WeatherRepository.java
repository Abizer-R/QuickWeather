package com.example.quickweather.Data.Source.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.quickweather.Data.Source.Local.Dao.DailyWeatherDao;
import com.example.quickweather.Data.Source.Local.Entity.DBDailyWeather;
import com.example.quickweather.Data.Source.Local.Entity.DBHourlyWeather;
import com.example.quickweather.Data.Source.Local.Dao.HourlyWeatherDao;
import com.example.quickweather.Data.Source.Local.WeatherDatabase;

import java.util.List;

public class WeatherRepository {

    private HourlyWeatherDao hourlyWeatherDao;
    private DailyWeatherDao dailyWeatherDao;

    private LiveData<List<DBHourlyWeather>> hourlyData;
    private LiveData<List<DBDailyWeather>> dailyData;

    /*
        We pass "Application" because later in our viewModel, we will
        also get passed an "Application"
        And also, since "Application" is a subclass of 'Context',
        we can use it to create our 'WeatherDatabase' instance
     */
    public WeatherRepository(Application application) {

        WeatherDatabase database = WeatherDatabase.getInstance(application);
        hourlyWeatherDao = database.hourlyDataDao();
        hourlyData = hourlyWeatherDao.getLocalHourlyData();

        dailyWeatherDao = database.dailyWeatherDao();
        dailyData = dailyWeatherDao.getLocalDailyData();
    }

    public void insertHourlyData(List<DBHourlyWeather> hourlyWeathers) {
        // TODO: (Use executor threadpool or something)
        new InsertHourlyWeatherTask(hourlyWeatherDao).execute(hourlyWeathers);
    }


    public void deleteAllHourlyData() {
        // TODO: (Use executor threadpool or something)
        new DeleteAllHourlyDataTask(hourlyWeatherDao).execute();
    }

    public LiveData<List<DBHourlyWeather>> getHourlyData() {
        return hourlyData;
    }

    public void insertDailyData(List<DBDailyWeather> dailyWeathers) {
        // TODO: (Use executor threadpool or something)
        new InsertDailyWeatherTask(dailyWeatherDao).execute(dailyWeathers);
    }

    public void deleteAllDailyData() {
        // TODO: (Use executor threadpool or something)
        new DeleteAllDailyDataTask(dailyWeatherDao).execute();
    }

    public LiveData<List<DBDailyWeather>> getDailyData() {
        return dailyData;
    }

    private static class InsertHourlyWeatherTask extends AsyncTask<List<DBHourlyWeather>, Void, Void> {

        private HourlyWeatherDao hourlyWeatherDao;

        private InsertHourlyWeatherTask(HourlyWeatherDao dao) {
            hourlyWeatherDao = dao;
        }

        @Override
        protected Void doInBackground(List<DBHourlyWeather>... lists) {
            List<DBHourlyWeather> weatherList = lists[0];
            for(int i=0; i< weatherList.size(); i++)
                    hourlyWeatherDao.insert(weatherList.get(i));
            return null;
        }
    }

    private static class DeleteAllHourlyDataTask extends AsyncTask<Void, Void, Void> {

        private HourlyWeatherDao hourlyWeatherDao;

        private DeleteAllHourlyDataTask(HourlyWeatherDao dao) {
            hourlyWeatherDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            hourlyWeatherDao.deleteAllHourlyData();
            return null;
        }
    }

    private static class InsertDailyWeatherTask extends AsyncTask<List<DBDailyWeather>, Void, Void> {

        private DailyWeatherDao dailyWeatherDao;

        private InsertDailyWeatherTask(DailyWeatherDao dao) {
            dailyWeatherDao = dao;
        }

        @Override
        protected Void doInBackground(List<DBDailyWeather>... lists) {
            List<DBDailyWeather> weatherList = lists[0];
            for(int i=0; i<weatherList.size(); i++)
                dailyWeatherDao.insert(weatherList.get(i));
            return null;
        }
    }

    private static class DeleteAllDailyDataTask extends AsyncTask<Void, Void, Void> {

        private DailyWeatherDao dailyWeatherDao;

        private DeleteAllDailyDataTask(DailyWeatherDao dao) {
            dailyWeatherDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dailyWeatherDao.deleteAllDailyData();
            return null;
        }
    }

}
