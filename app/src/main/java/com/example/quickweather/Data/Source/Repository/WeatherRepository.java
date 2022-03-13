package com.example.quickweather.Data.Source.Repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.quickweather.Data.Model.NetworkWeatherDetails;
import com.example.quickweather.Data.Source.Local.Dao.DBWeatherDao;
import com.example.quickweather.Data.Source.Local.Entity.DBWeatherDetails;
import com.example.quickweather.Data.Source.Local.WeatherDatabase;
import com.example.quickweather.Data.Source.Remote.RetrofitHelper;
import com.example.quickweather.Ui.WeatherRemoteApiCallback;

import java.nio.channels.AsynchronousChannelGroup;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherRepository {

    private static final String TAG = WeatherRepository.class.getSimpleName();

    private DBWeatherDao dbWeatherDao;

    private LiveData<DBWeatherDetails> currDBData;
    private LiveData<List<DBWeatherDetails>> hourlyDBData;
    private LiveData<List<DBWeatherDetails>> dailyDBData;

    /*
        We pass "Application" because later in our viewModel, we will
        also get passed an "Application"
        And also, since "Application" is a subclass of 'Context',
        we can use it to create our 'WeatherDatabase' instance
     */
    public WeatherRepository(Application application) {

        WeatherDatabase database = WeatherDatabase.getInstance(application);
        dbWeatherDao = database.dbWeatherDao();

        currDBData = dbWeatherDao.getCurrentDBWeatherData();
        hourlyDBData = dbWeatherDao.getHourlyDBWeatherData();
        dailyDBData = dbWeatherDao.getDailyDBWeatherData();
    }

    public void fetchRemoteWeatherData(WeatherRemoteApiCallback callback) {
        Call<NetworkWeatherDetails> weatherDetailCall = RetrofitHelper.getWeatherApiClient().getWeatherDetails(
                22.7196,
                75.8577,
                "metric",
                "minutely,alerts",
                "6b40419e55e87b4c7eb082eca5f50dab"
        );

        weatherDetailCall.enqueue(new Callback<NetworkWeatherDetails>() {
            @Override
            public void onResponse(Call<NetworkWeatherDetails> call, Response<NetworkWeatherDetails> response) {

                if(!response.isSuccessful()) {
                    Log.e(TAG, "onResponse: " + "Response code: " + response.code());
                    return;
                }

                NetworkWeatherDetails weatherDetails = response.body();
                if(weatherDetails != null) {
                    callback.onSuccess(weatherDetails);
                }
            }

            @Override
            public void onFailure(Call<NetworkWeatherDetails> call, Throwable t) {
                Log.e(TAG, "onFailure: Network Call failed. throwable msg: " + t.getMessage());
            }
        });
    }

    public void insertDBWeatherData(List<DBWeatherDetails> currWeather) {
        // TODO: (Use executor threadpool or something)
        new InsertDBWeatherData(dbWeatherDao).execute(currWeather);
    }

    public void deleteAllWeatherData() {
        // TODO: (Use executor threadpool or something)
        new DeleteDBWeatherData(dbWeatherDao).execute();
    }

    private static class InsertDBWeatherData extends AsyncTask<List<DBWeatherDetails>, Void, Void> {

        private DBWeatherDao dbWeatherDao;

        private InsertDBWeatherData(DBWeatherDao dao) {
            dbWeatherDao = dao;
        }

        @Override
        protected Void doInBackground(List<DBWeatherDetails>... lists) {
            List<DBWeatherDetails> weatherlist = lists[0];
            for(int i=0; i<weatherlist.size(); i++)
                    dbWeatherDao.insert(weatherlist.get(i));
            return null;
        }
    }

    private static class DeleteDBWeatherData extends AsyncTask<Void, Void, Void> {

        private DBWeatherDao dbWeatherDao;

        private DeleteDBWeatherData(DBWeatherDao dao) {
            dbWeatherDao = dao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            dbWeatherDao.deleteAllDBWeatherData();
            return null;
        }
    }

    public LiveData<DBWeatherDetails> getCurrDBData() {
        return currDBData;
    }

    public LiveData<List<DBWeatherDetails>> getHourlyDBData() {
        return hourlyDBData;
    }

    public LiveData<List<DBWeatherDetails>> getDailyDBData() {
        return dailyDBData;
    }
}
