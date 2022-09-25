package com.prodapt.weather.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.prodapt.weather.AppController;
import com.prodapt.weather.R;
import com.prodapt.weather.model.Current;
import com.prodapt.weather.model.Daily;
import com.prodapt.weather.model.Rain;
import com.prodapt.weather.model.ResponseJSON;
import com.prodapt.weather.model.Snow;
import com.prodapt.weather.model.Temp;
import com.prodapt.weather.model.Weather;
import com.prodapt.weather.model.WeatherDaily;
import com.prodapt.weather.utils.LogUtils;
import com.prodapt.weather.utils.RequestURL;
import com.prodapt.weather.utils.SharedUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    private MaterialSearchBar searchBar;

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

        searchBar = findViewById(R.id.searchBar);
        searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {

            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                searchReRequest(text.toString());
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });
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

    private void searchReRequest(String city) {

        String requestUrl = RequestURL.API_URL_CITY + "?q=" + city + "&appid=" + RequestURL.API_KEY;

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, requestUrl, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //data
                        try {
                            if(response.getString("cod").equals("404")) {
                                Toast.makeText(WeatherActivity.this, "Wrong city name!", Toast.LENGTH_SHORT);
                            } else {Double lat = response.getJSONObject("coord").getDouble("lat");
                                Double lon = response.getJSONObject("coord").getDouble("lon");
                                SharedUtils.putKey(WeatherActivity.this, "GPSCity", city);
                                request(lat,lon);}
                        } catch (JSONException e) {
                            e.printStackTrace();
                            LogUtils.e(TAG, e.getMessage());
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(WeatherActivity.this, "Wrong city name", Toast.LENGTH_SHORT).show();

                        if (error instanceof NetworkError) {
                            Toast.makeText(WeatherActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ServerError) {
                            Toast.makeText(WeatherActivity.this, "Wrong city name", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof AuthFailureError) {
                        } else if (error instanceof ParseError) {
                        } else if (error instanceof NoConnectionError) {
                            Toast.makeText(WeatherActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        } else if (error instanceof TimeoutError) {
                            Toast.makeText(WeatherActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                }
        );

        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    private void request(double lat, double lon) {

        String requestUrl = RequestURL.API_URL + "?lat=" + lat + "&lon=" + lon + "&exclude=minutely,hourly,alerts&units=metric&appid=" + RequestURL.API_KEY;

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, requestUrl, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //data
                        try {
                            LogUtils.e(TAG, "onResponse: " + response.getJSONObject("current").toString());
                            JSONObject currentResponse = response.getJSONObject("current");
                            JSONObject currentWeatherResponse = currentResponse.getJSONArray("weather").getJSONObject(0);
                            JSONObject rainResponse = currentResponse.has("rain")? currentResponse.getJSONObject("rain") : null;
                            JSONObject snowResponse = currentResponse.has("snow")? currentResponse.getJSONObject("snow") : null;
                            JSONArray dailyArray = response.getJSONArray("daily");

                            Current.setSunrise(currentResponse.getLong("sunrise"));
                            Current.setSunset(currentResponse.getLong("sunset"));
                            Current.setTemp(currentResponse.getDouble("temp"));
                            Current.setFeels_like(currentResponse.getDouble("feels_like"));
                            Current.setPressure(currentResponse.getInt("pressure"));
                            Current.setHumidity(currentResponse.getInt("humidity"));
                            Current.setUvi(currentResponse.getDouble("uvi"));
                            Current.setClouds(currentResponse.getInt("clouds"));
                            Current.setVisibility(currentResponse.getInt("visibility"));
                            Current.setWind_speed(currentResponse.getDouble("wind_speed"));
                            Current.setWind_deg(currentResponse.getInt("wind_deg"));
                            Current.setWind_gust(currentResponse.getDouble("wind_gust"));
                            Weather.setMain(currentWeatherResponse.getString("main"));
                            Weather.setDescription(currentWeatherResponse.getString("description"));
                            Weather.setIcon(currentWeatherResponse.getString("icon"));
                            if (rainResponse != null) {
                                Rain.setOne_h(rainResponse.getDouble("1h"));
                            }
                            if (snowResponse != null) {
                                Snow.setOne_h(snowResponse.getDouble("1h"));
                            }
                            ArrayList<Daily> dailies = new ArrayList<Daily>();
                            for(int i = 0; i < dailyArray.length(); i++){
                                Daily dailyData = new Daily();

                                Temp tempData = new Temp();
                                tempData.setMax(dailyArray.getJSONObject(i).getJSONObject("temp").getDouble("max"));
                                tempData.setMin(dailyArray.getJSONObject(i).getJSONObject("temp").getDouble("min"));

                                WeatherDaily weatherDailyData = new WeatherDaily();
                                weatherDailyData.setMain(dailyArray.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main"));
                                weatherDailyData.setIcon(dailyArray.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("icon"));

                                ArrayList<WeatherDaily> setWeatherDailyList = new ArrayList<WeatherDaily>();
                                setWeatherDailyList.add(weatherDailyData);

                                dailyData.setDt(dailyArray.getJSONObject(i).getLong("dt"));
                                dailyData.setTemp(tempData);
                                dailyData.setWeatherDailyList(setWeatherDailyList);
                                dailies.add(dailyData);
                            }
                            ResponseJSON.setDaily(dailies);

                            titleText.setText(SharedUtils.getKey(WeatherActivity.this,"GPSCity"));


                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());
                            overridePendingTransition(0, 0);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            LogUtils.e(TAG, e.getMessage());
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        LogUtils.d(TAG, "Error: " + error.getMessage());

                        if (error instanceof NetworkError) {
                            Toast.makeText(WeatherActivity.this, error.getMessage(), Toast.LENGTH_SHORT);
                        } else if (error instanceof ServerError) {
                            Toast.makeText(WeatherActivity.this, error.getMessage(), Toast.LENGTH_SHORT);
                        } else if (error instanceof AuthFailureError) {
                        } else if (error instanceof ParseError) {
                        } else if (error instanceof NoConnectionError) {
                            Toast.makeText(WeatherActivity.this, error.getMessage(), Toast.LENGTH_SHORT);
                        } else if (error instanceof TimeoutError) {
                            Toast.makeText(WeatherActivity.this, error.getMessage(), Toast.LENGTH_SHORT);
                        }
                    }

                }
        );

        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }


}
