package com.example.quickweather.Ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quickweather.Data.Model.DailyWeatherForecast;
import com.example.quickweather.Data.Source.Local.Entity.DBDailyWeather;
import com.example.quickweather.Data.Source.Local.Entity.DBHourlyWeather;
import com.example.quickweather.Data.Source.Remote.RetrofitHelper;
import com.example.quickweather.Data.Model.NetworkWeatherDetails;
import com.example.quickweather.Mapper.DailyMapperLocal;
import com.example.quickweather.Mapper.DailyMapperRemote;
import com.example.quickweather.Mapper.HourlyMapperLocal;
import com.example.quickweather.Mapper.HourlyMapperRemote;
import com.example.quickweather.R;
import com.example.quickweather.Ui.Adapters.WeatherDailyDetailsAdapter;
import com.example.quickweather.Ui.Adapters.WeatherHourlyDetailsAdapter;

import com.example.quickweather.Data.Model.NetworkWeatherDetails.Current;
import com.example.quickweather.Data.Model.NetworkWeatherDetails.Hourly;
import com.example.quickweather.Data.Model.NetworkWeatherDetails.Daily;
import com.example.quickweather.Utils.IconUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    HourlyMapperRemote hourlyMapperRemote = new HourlyMapperRemote();
    DailyMapperRemote dailyMapperRemote = new DailyMapperRemote();
    HourlyMapperLocal hourlyMapperLocal = new HourlyMapperLocal();
    DailyMapperLocal dailyMapperLocal = new DailyMapperLocal();

    private WeatherViewModel weatherViewModel;

    WeatherHourlyDetailsAdapter hourlyAdapter;
    WeatherDailyDetailsAdapter dailyAdapter;

    private SwipeRefreshLayout swipeRefreshLayout;

    // TODO: UPDATE THESE ALONG THE WAY TOO
    private ImageView locationIndicator;
    private TextView currentLocation;
    private TextView lastUpdatedCurrent;

    private TextView currTemp;
    private TextView currDesc;
    private ImageView currDescIcon;
    private TextView currMinMaxTemp;
    private TextView lastUpdated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setViewModelAndObservers();

        bindViewsWithVariables();

        setRecyclerViews();

        Toolbar myToolbar = findViewById(R.id.custom_toolbar);
        setSupportActionBar(myToolbar);

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        // TODO: Remove this one
        updateView();
    }

    private void setRecyclerViews() {
        /*
          Setting up Hourly Recycler View
         */
        RecyclerView hourlyRecyclerView = findViewById(R.id.hourly_recycler_view);
        hourlyRecyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        );
        hourlyRecyclerView.setHasFixedSize(true);

        hourlyAdapter = new WeatherHourlyDetailsAdapter();
        hourlyRecyclerView.setAdapter(hourlyAdapter);

        /*
          Setting up Daily Recycler View
         */
        RecyclerView dailyRecyclerView = findViewById(R.id.daily_recycler_view);
        dailyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        dailyRecyclerView.setHasFixedSize(true);

        dailyAdapter = new WeatherDailyDetailsAdapter();
        dailyRecyclerView.setAdapter(dailyAdapter);

        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(getDrawable(R.drawable.daily_recyclerview_divider));
        dailyRecyclerView.addItemDecoration(divider);
    }

    private void bindViewsWithVariables() {
        locationIndicator = findViewById(R.id.location_image_view);
        currentLocation = findViewById(R.id.location_text_view);
        lastUpdatedCurrent = findViewById(R.id.last_updated_current);

        currTemp = findViewById(R.id.current_temp);
        currDesc = findViewById(R.id.current_desc);
        currDescIcon = findViewById(R.id.current_icon);
        currMinMaxTemp = findViewById(R.id.current_max_min_temp);
        lastUpdated = findViewById(R.id.last_updated);
    }

    private void setViewModelAndObservers() {
        weatherViewModel = new ViewModelProvider(this).get(WeatherViewModel.class);

        weatherViewModel.getHourlyWeather().observe(this, new Observer<List<DBHourlyWeather>>() {
            @Override
            public void onChanged(List<DBHourlyWeather> dbHourlyWeathers) {
                hourlyAdapter.setHourlyForecasts(hourlyMapperLocal.mapFromEntity(dbHourlyWeathers));
            }
        });

        weatherViewModel.getDailyWeather().observe(this, new Observer<List<DBDailyWeather>>() {
            @Override
            public void onChanged(List<DBDailyWeather> dailyWeathers) {
                dailyAdapter.setDailyForecasts(dailyMapperLocal.mapFromEntity(dailyWeathers));
            }
        });
    }

    private void updateView() {

        // lat=22.7196&lon=75.8577&units=metric&exclude=minutely,alerts&appid=6b40419e55e87b4c7eb082eca5f50dab
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

                    updateRecyclerViewCurrent(weatherDetails.getCurrent(), weatherDetails.getDaily().get(0));
                    insertDbHourly(weatherDetails.getHourly());
//                    updateRecyclerViewHourly(weatherDetails.getHourly());
                    insertDbDaily(weatherDetails.getDaily());
//                    updateRecyclerViewDaily(weatherDetails.getDaily());
                }
            }

            @Override
            public void onFailure(Call<NetworkWeatherDetails> call, Throwable t) {
                Log.e(TAG, "onFailure: Network Call failed. throwable msg: " + t.getMessage());
            }
        });
    }

    private void insertDbHourly(List<Hourly> hourlyList) {
        weatherViewModel.deleteAllHourlyData();
        if(hourlyList.get(0).getDt() < System.currentTimeMillis())
            hourlyList.remove(0);
        weatherViewModel.insertAllHourlyData(hourlyList);
    }

    private void insertDbDaily(List<Daily> dailyList) {
        weatherViewModel.deleteAllDailyData();
        weatherViewModel.insertAllDailyData(dailyList);
    }

    private void updateRecyclerViewCurrent(Current current, Daily daily) {

        currTemp.setText(String.valueOf((int)current.getTemp()));
        currDesc.setText(current.getWeather().get(0).getDescription());
        currDescIcon.setImageResource(IconUtils.getIconResourceId(current.getWeather().get(0).getIcon()));
        currMinMaxTemp.setText((int)daily.getDailyTemp().getMax() + "° / " +
                (int)daily.getDailyTemp().getMin() + "°");

        // TODO: Do update Last Seen after Implementing "Local"
        lastUpdated.setText("updated 20 minutes ago");
    }

    private void updateRecyclerViewHourly(List<Hourly> hourlyList) {

        hourlyAdapter.setHourlyForecasts(hourlyMapperRemote.mapFromEntity(hourlyList));
    }

    private void updateRecyclerViewDaily(List<Daily> dailyList) {

        dailyAdapter.setDailyForecasts(dailyMapperRemote.mapFromEntity(dailyList));
    }

    @Override
    public void onRefresh() {
        updateView();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menu_settings) {
            // TODO: Settingssssss
            Toast.makeText(this, "YOOOOOO BOIIII", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}