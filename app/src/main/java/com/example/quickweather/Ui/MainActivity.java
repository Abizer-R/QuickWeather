package com.example.quickweather.Ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quickweather.Data.Model.DailyWeatherForecast;
import com.example.quickweather.Data.Model.HourlyWeatherForecast;
import com.example.quickweather.Data.Source.Remote.RetrofitHelper;
import com.example.quickweather.Data.Model.NetworkWeatherDetails;
import com.example.quickweather.Mapper.DailyMapper;
import com.example.quickweather.Mapper.HourlyMapper;
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

    WeatherHourlyDetailsAdapter hourlyAdapter;
    WeatherDailyDetailsAdapter dailyAdapter;

    private SwipeRefreshLayout swipeRefreshLayout;

    private TextView currTemp;
    private TextView currDesc;
    private ImageView currDescIcon;
    private TextView currMinMaxTemp;
    private TextView lastUpdated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currTemp = findViewById(R.id.current_temp);
        currDesc = findViewById(R.id.current_desc);
        currDescIcon = findViewById(R.id.current_icon);
        currMinMaxTemp = findViewById(R.id.current_max_min_temp);
        lastUpdated = findViewById(R.id.last_updated);

        Toolbar myToolbar = findViewById(R.id.custom_toolbar);
        setSupportActionBar(myToolbar);

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

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        // TODO: Remove this one
        updateView();
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
                    // TODO: Remove Toast in the end
                    Toast.makeText(MainActivity.this, "Response code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                NetworkWeatherDetails weatherDetails = response.body();
                if(weatherDetails != null) {

                    updateRecyclerViewCurrent(
                            weatherDetails.getCurrent(),
                            weatherDetails.getDaily().get(0));
                    updateRecyclerViewHourly(weatherDetails.getHourly());
                    updateRecyclerViewDaily(weatherDetails.getDaily());
                }
            }

            @Override
            public void onFailure(Call<NetworkWeatherDetails> call, Throwable t) {
                // TODO: Remove Toast in the end
                Toast.makeText(MainActivity.this, "onFailure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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

        List<HourlyWeatherForecast> hourlyForecasts = new ArrayList<>();
        HourlyMapper mapper = new HourlyMapper();
        for(int i=0; i<25; i++) {
            hourlyForecasts.add(mapper.mapFromEntity(hourlyList.get(i)));
        }
        hourlyAdapter.setHourlyForecasts(hourlyForecasts);
    }

    private void updateRecyclerViewDaily(List<Daily> dailyList) {

        List<DailyWeatherForecast> dailyForecasts = new ArrayList<>();
        DailyMapper mapper = new DailyMapper();
        for(int i=0; i<7; i++) {
            dailyForecasts.add(mapper.mapFromEntity(dailyList.get(i)));
        }
        dailyAdapter.setDailyForecasts(dailyForecasts);
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