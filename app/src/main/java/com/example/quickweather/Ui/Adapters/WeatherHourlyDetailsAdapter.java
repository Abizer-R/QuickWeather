package com.example.quickweather.Ui.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickweather.Data.Model.HourlyWeatherForecast;
import com.example.quickweather.R;
import com.example.quickweather.Utils.DateTimeUtil;
import com.example.quickweather.Utils.IconUtils;

import java.util.ArrayList;
import java.util.List;

public class WeatherHourlyDetailsAdapter extends RecyclerView.Adapter<WeatherHourlyDetailsAdapter.HourlyDataHolder> {

    private List<HourlyWeatherForecast> hourlyForecasts = new ArrayList<>();

    @NonNull
    @Override
    public HourlyDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hourly_recyclerview_item, parent, false);
        return new HourlyDataHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HourlyDataHolder holder, int position) {

        HourlyWeatherForecast currHour = hourlyForecasts.get(position);

        holder.time.setText(DateTimeUtil.getLocalTime(currHour.getTimestamp()));
        // TODO: IDHR BHIII KUCHH KARRRR
        holder.icon.setImageResource(R.drawable.ic_01d);
        holder.temp.setText(String.valueOf(currHour.getTemp()) + "°");
    }

    @Override
    public int getItemCount() {
        return hourlyForecasts.size();
    }

    public void setHourlyForecasts(List<HourlyWeatherForecast> hourlyForecasts) {
        this.hourlyForecasts = hourlyForecasts;
        // TODO: change notifyDataSetChanged(); to something good
        notifyDataSetChanged();
    }

    class HourlyDataHolder extends RecyclerView.ViewHolder {
        private TextView time;
        private ImageView icon;
        private TextView temp;

        public HourlyDataHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.hourly_rv_time);
            icon = itemView.findViewById(R.id.hourly_rv_icon);
            temp = itemView.findViewById(R.id.hourly_rv_temp);
        }
    }
}
