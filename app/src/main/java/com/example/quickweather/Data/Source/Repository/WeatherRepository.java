package com.example.quickweather.Data.Source.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.quickweather.Data.Source.Local.Entity.DBHourlyWeather;
import com.example.quickweather.Data.Source.Local.Dao.HourlyWeatherDao;
import com.example.quickweather.Data.Source.Local.WeatherDatabase;

import java.util.List;

public class WeatherRepository {

    private HourlyWeatherDao hourlyDataDao;

    private LiveData<List<DBHourlyWeather>> hourlyData;

    /*
        We pass "Application" because later in our viewModel, we will
        also get passed an "Application"
        And also, since "Application" is a subclass of 'Context',
        we can use it to create our 'WeatherDatabase' instance
     */
    public WeatherRepository(Application application) {

        WeatherDatabase database = WeatherDatabase.getInstance(application);
        hourlyDataDao = database.hourlyDataDao();
        hourlyData = hourlyDataDao.getLocalHourlyData();
    }

    public void insertHourlyData(List<DBHourlyWeather> hourlyWeathers) {
        // TODO: (Use executor threadpool or something)
        new InsertHourlyWeatherTask(hourlyDataDao).execute(hourlyWeathers);
    }


    public void deleteAllHourlyData() {
        // TODO: (Use executor threadpool or something)
        new DeleteAllHourlyDataTask(hourlyDataDao).execute();
    }

    public LiveData<List<DBHourlyWeather>> getHourlyData() {
        return hourlyData;
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

}
