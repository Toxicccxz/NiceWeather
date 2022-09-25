package com.prodapt.weather.view;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.prodapt.weather.R;
import com.prodapt.weather.model.Current;
import com.prodapt.weather.model.Daily;
import com.prodapt.weather.model.Rain;
import com.prodapt.weather.model.ResponseJSON;
import com.prodapt.weather.model.Snow;
import com.prodapt.weather.model.Weather;
import com.prodapt.weather.model.WeatherDaily;
import com.prodapt.weather.utils.LogUtils;
import com.prodapt.weather.utils.SharedUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WeatherActivity extends AppCompatActivity {

    private String TAG = "WeatherActivity";
    private TextView titleText;
    private TextView nowTmpTV;
    private TextView nowWeatherQltyTV;
    private TextView nowToady;
    private TextView nowDescription;

    private TextView feelLike;
    private TextView humidity;
    private TextView visibility;
    private TextView wind_speed;
    private TextView rain;
    private TextView pressure;

    private LinearLayout dailyForecastLayout;
    private TextView dailyDate;
    private TextView dailyWeather;
    private ImageView dailyWeatherImage;
    private TextView dailyTemperature;

    private ImageView sortBtn;
    private Boolean sort = true;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        sortBtn = findViewById(R.id.sort);
        sortBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sort = sort ? false : true;
                showDailyForecast(sort);
            }
        });

        showCurrentWeather();

        showDailyForecast(sort);



    }

    private void showCurrentWeather() {
        titleText = findViewById(R.id.title_cityName);
        nowTmpTV = findViewById(R.id.now_temperature);
        nowWeatherQltyTV = findViewById(R.id.now_dayweather_qlty);
        nowToady = findViewById(R.id.now_today);
        nowDescription = findViewById(R.id.now_description);
        titleText.setText(SharedUtils.getKey(WeatherActivity.this,"GPSCity"));
        nowTmpTV.setText(String.valueOf(Current.getTemp()) + "℃");
        nowWeatherQltyTV.setText(Weather.getMain());
        nowToady.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));
        nowDescription.setText(Weather.getDescription());


        feelLike = findViewById(R.id.now_feels_like);
        humidity = findViewById(R.id.now_humidity);
        visibility = findViewById(R.id.now_visibility);
        wind_speed = findViewById(R.id.now_wind);
        rain = findViewById(R.id.now_rain);
        pressure = findViewById(R.id.now_pressure);
        feelLike.setText("feels like " + Current.getFeels_like() + "℃");
        humidity.setText("humidity " + Current.getHumidity() + "%");
        visibility.setText("visibility " + Current.getVisibility() + "M");
        wind_speed.setText(Current.getWind_speed() + " metre/sec");
        rain.setText((Snow.getOne_h() == null? (Rain.getOne_h() == null? 0.0 : Rain.getOne_h()) : Snow.getOne_h()) + " mm");
        pressure.setText("pressure " + Current.getPressure() + "hPa");

        LogUtils.e(TAG, ResponseJSON.getDaily().get(7).getWeatherDailyList().get(0).getMain());

    }

    private void showDailyForecast(Boolean sort) {
        dailyForecastLayout = findViewById(R.id.daily_forecast_layout);
        dailyForecastLayout.removeAllViews();
        LayoutInflater layoutInflater = getLayoutInflater();
        List<Daily> dailyArrayList = ResponseJSON.getDaily();
        dailyArrayList.subList(5, dailyArrayList.size()).clear();

        if (sort) {
            for (int i = 0; i < dailyArrayList.size(); i++) {
                View view = layoutInflater.from(this).inflate(R.layout.daily_forecast_item, dailyForecastLayout, false);
                dailyDate = (TextView) view.findViewById(R.id.daily_date);
                dailyWeather = (TextView) view.findViewById(R.id.daily_weather);
                dailyWeatherImage = (ImageView) view.findViewById(R.id.daily_weather_image);
                dailyTemperature = (TextView) view.findViewById(R.id.daily_temperature);

                dailyDate.setText(new SimpleDateFormat("MM - dd").format(new java.util.Date((long)dailyArrayList.get(i).getDt()*1000)));
                dailyWeather.setText(dailyArrayList.get(i).getWeatherDailyList().get(0).getMain());
                Glide.with(WeatherActivity.this)
                        .load("https://openweathermap.org/img/wn/" + dailyArrayList.get(i).getWeatherDailyList().get(0).getIcon() + "@2x.png")
                        .fitCenter()
                        .into(dailyWeatherImage);
                dailyTemperature.setText(dailyArrayList.get(i).getTemp().getMin() + "º ~" + dailyArrayList.get(i).getTemp().getMax() + "º");
                dailyForecastLayout.addView(view);
            }
        } else {
            for (int i = 4; i >= 0; i--) {
                View view = layoutInflater.from(this).inflate(R.layout.daily_forecast_item, dailyForecastLayout, false);
                dailyDate = (TextView) view.findViewById(R.id.daily_date);
                dailyWeather = (TextView) view.findViewById(R.id.daily_weather);
                dailyWeatherImage = (ImageView) view.findViewById(R.id.daily_weather_image);
                dailyTemperature = (TextView) view.findViewById(R.id.daily_temperature);

                dailyDate.setText(new SimpleDateFormat("MM - dd").format(new java.util.Date((long)dailyArrayList.get(i).getDt()*1000)));
                dailyWeather.setText(dailyArrayList.get(i).getWeatherDailyList().get(0).getMain());
                Glide.with(WeatherActivity.this)
                        .load("https://openweathermap.org/img/wn/" + dailyArrayList.get(i).getWeatherDailyList().get(0).getIcon() + "@2x.png")
                        .fitCenter()
                        .into(dailyWeatherImage);
                dailyTemperature.setText(dailyArrayList.get(i).getTemp().getMin() + "º ~" + dailyArrayList.get(i).getTemp().getMax() + "º");
                dailyForecastLayout.addView(view);
            }
        }

    }
}
