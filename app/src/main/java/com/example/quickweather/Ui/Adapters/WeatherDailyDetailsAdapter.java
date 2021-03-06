package com.example.quickweather.Ui.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickweather.Data.Model.DailyWeatherForecast;
import com.example.quickweather.R;
import com.example.quickweather.Utils.DateTimeUtil;
import com.example.quickweather.Utils.WeatherUtils;

import java.util.ArrayList;
import java.util.List;

public class WeatherDailyDetailsAdapter extends RecyclerView.Adapter<WeatherDailyDetailsAdapter.DailyDataHolder> {

    private List<DailyWeatherForecast> dailyForecasts = new ArrayList<>();
    private boolean isFahrenheit = false;

    @NonNull
    @Override
    public DailyDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.daily_recyclerview_item, parent, false);
        return new DailyDataHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyDataHolder holder, int position) {

        DailyWeatherForecast currDay = dailyForecasts.get(position);

        holder.date.setText(DateTimeUtil.getLocalDate(currDay.getTimestamp()));
        // TODO: IDHR BHIII KUCHHH KARRRR
        holder.icon.setImageResource(WeatherUtils.getIconResourceId(currDay.getWeatherId(), currDay.getTimestamp()));
        if(isFahrenheit)
            holder.tempMinMax.setText(String.valueOf(WeatherUtils.getTempInFahrenheit(currDay.getTemp_max())) + "° / " +
                    String.valueOf(WeatherUtils.getTempInFahrenheit(currDay.getTemp_min()) + "°"));
        else
            holder.tempMinMax.setText(String.valueOf(currDay.getTemp_max()) + "° / " +
                        String.valueOf(currDay.getTemp_min()) + "°");
    }

    @Override
    public int getItemCount() {
        return dailyForecasts.size();
    }

    public void setDailyForecasts(List<DailyWeatherForecast> dailyForecasts) {
        this.dailyForecasts = dailyForecasts;
        // TODO: change notifyDataSetChanged(); to something good
        notifyDataSetChanged();
    }

    public void setFahrenheit(boolean fahrenheit) {
        isFahrenheit = fahrenheit;
    }

    class DailyDataHolder extends RecyclerView.ViewHolder{
        private TextView date;
        private ImageView icon;
        private TextView tempMinMax;

        public DailyDataHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.daily_rv_date);
            icon = itemView.findViewById(R.id.daily_rv_icon);
            tempMinMax = itemView.findViewById(R.id.daily_rv_temp);
        }
    }
}
