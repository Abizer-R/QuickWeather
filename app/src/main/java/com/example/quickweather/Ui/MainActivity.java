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

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quickweather.Data.Source.Local.Entity.DBWeatherDetails;
import com.example.quickweather.Mapper.DailyMapperLocal;
import com.example.quickweather.Mapper.HourlyMapperLocal;
import com.example.quickweather.R;
import com.example.quickweather.Ui.Adapters.WeatherDailyDetailsAdapter;
import com.example.quickweather.Ui.Adapters.WeatherHourlyDetailsAdapter;

import com.example.quickweather.Utils.LocationUtils;
import com.example.quickweather.Utils.SharedPrefsUtil;
import com.example.quickweather.Utils.WeatherUtils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    HourlyMapperLocal hourlyMapperLocal = new HourlyMapperLocal();
    DailyMapperLocal dailyMapperLocal = new DailyMapperLocal();

    private WeatherViewModel weatherViewModel;

    WeatherHourlyDetailsAdapter hourlyAdapter;
    WeatherDailyDetailsAdapter dailyAdapter;

    private SwipeRefreshLayout swipeRefreshLayout;

    private FusedLocationProviderClient fusedLocationProviderClient;

    private LocationManager locationManager;

    // TODO: changes according to "islocationAvalaible" prefs
    private ImageView locationIndicator;

    // TODO: show according to latitude and longitude prefs
    private TextView currentLocation;

    // TODO: Visible only if islocationAvalaible = false
    private TextView lastKnownLocationTV;

    private TextView currTemp;
    private TextView currDesc;
    private ImageView currDescIcon;
    private TextView currMinMaxTemp;
    private TextView lastUpdated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        setViewModelAndObservers();

        bindViewsWithVariables();

        setRecyclerViews();
        currTemp = findViewById(R.id.current_temp);
        currDesc = findViewById(R.id.current_desc);
        currDescIcon = findViewById(R.id.current_icon);
        currMinMaxTemp = findViewById(R.id.current_max_min_temp);
        lastUpdated = findViewById(R.id.last_updated);

        Toolbar myToolbar = findViewById(R.id.custom_toolbar);
        setSupportActionBar(myToolbar);

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
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
        lastKnownLocationTV = findViewById(R.id.is_location_available);

        currTemp = findViewById(R.id.current_temp);
        currDesc = findViewById(R.id.current_desc);
        currDescIcon = findViewById(R.id.current_icon);
        currMinMaxTemp = findViewById(R.id.current_max_min_temp);
        lastUpdated = findViewById(R.id.last_updated);
    }

    private void setViewModelAndObservers() {
        weatherViewModel = new ViewModelProvider(this).get(WeatherViewModel.class);

        weatherViewModel.getCurrDBData().observe(this, new Observer<DBWeatherDetails>() {
            @Override
            public void onChanged(DBWeatherDetails weatherDetails) {
                if(weatherDetails != null)
                    updateCurrentWeatherData(weatherDetails);
            }
        });

        weatherViewModel.getHourlyDBData().observe(this, new Observer<List<DBWeatherDetails>>() {
            @Override
            public void onChanged(List<DBWeatherDetails> dbWeatherDetails) {
                hourlyAdapter.setHourlyForecasts(hourlyMapperLocal.mapFromEntity(dbWeatherDetails));
            }
        });

        weatherViewModel.getDailyDBData().observe(this, new Observer<List<DBWeatherDetails>>() {
            @Override
            public void onChanged(List<DBWeatherDetails> dbWeatherDetails) {
                dailyAdapter.setDailyForecasts(dailyMapperLocal.mapFromEntity(dbWeatherDetails));
            }
        });

    }

    private void updateView() {


        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            LocationUtils.turnOnGps(this);
        } else {
            LocationUtils.updateLocation(this, fusedLocationProviderClient);
        }

        weatherViewModel.updateDBWeatherData(SharedPrefsUtil.getSharedPrefLatitude(this), SharedPrefsUtil.getSharedPrefLongitude(this));
        currentLocation.setText(LocationUtils.getAddress(
                this, SharedPrefsUtil.getSharedPrefLatitude(this), SharedPrefsUtil.getSharedPrefLongitude(this)));
    }

    private void updateCurrentWeatherData(DBWeatherDetails weatherDetails) {

        currTemp.setText(String.valueOf(weatherDetails.getCurrTemp()));
        currDesc.setText(WeatherUtils.getDescription(weatherDetails.getWeatherId()));
        currDescIcon.setImageResource(WeatherUtils.getIconResourceId(weatherDetails.getWeatherId(), weatherDetails.getTimestamp()));
        currMinMaxTemp.setText(WeatherUtils.getCurrentMinMaxTemp(
                (int)weatherDetails.getMaxTemp(),
                (int)weatherDetails.getMinTemp()));

        // TODO: Should also go in SharedPref Changed function..... Cuz we have to save last updated time in sharedPrefs
        /*
            instead of this....
            We will update the sharedPref
         */
        lastUpdated.setText(WeatherUtils.getLastWeatherUpdated(weatherDetails.getTimestamp()));
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

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == LocationUtils.REQUEST_LOCATION) {

            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if(location != null) {
                            SharedPrefsUtil.setSharedPrefLocation(MainActivity.this, location.getLatitude(), location.getLongitude());
                        }
                    }
                });
            }
        }
    }
}