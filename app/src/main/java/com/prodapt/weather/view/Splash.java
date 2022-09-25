package com.prodapt.weather.view;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

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
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
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

import java.util.ArrayList;
import java.util.List;

public class Splash extends AppCompatActivity {

    private Context context;
    private String TAG = "Splash";
    private FusedLocationProviderClient fusedLocationClient;
    protected int LOCATION_PERMISSION_REQUEST_CODE = 1;
    protected double latitude, longitude;
    protected String GPSCity;
    private Handler handler;
    private ResponseJSON responseJSON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        context = Splash.this;
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        handler = new Handler();
        responseJSON = new ResponseJSON();

        if (checkSelfPermission(
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            //   gps functions.
            afterPermission();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (isPermissionGranted(permissions, grantResults, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    afterPermission();
                } else {
                    Toast.makeText(this, "Can not proceed! i need permission", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public static boolean isPermissionGranted(String[] grantPermissions, int[] grantResults, String permission) {
        for (int i = 0; i < grantPermissions.length; i++) {
            if (permission.equals(grantPermissions[i])) {
                return grantResults[i] == PackageManager.PERMISSION_GRANTED;
            }
        }
        return false;
    }

    @SuppressLint("MissingPermission")
    public void afterPermission() {
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object

                            request(location.getLatitude(), location.getLongitude());

                            latitude = location.getLatitude();
                            longitude = location.getLatitude();

                            requestCity(location.getLatitude(), location.getLongitude());

                        }
                    }
                });

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 3000ms
                startActivity(new Intent(Splash.this, WeatherActivity.class));
                finish();
            }
        }, 3000);
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
                            Toast.makeText(Splash.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ServerError) {
                            Toast.makeText(Splash.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        } else if (error instanceof AuthFailureError) {
                        } else if (error instanceof ParseError) {
                        } else if (error instanceof NoConnectionError) {
                            Toast.makeText(Splash.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        } else if (error instanceof TimeoutError) {
                            Toast.makeText(Splash.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                }
        );

        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    private void requestCity(double lat, double lon){
        String requestUrl = RequestURL.GOOGLEMAP_URL + "?latlng=" + lat + "," + lon + "&location_type=APPROXIMATE&result_type=administrative_area_level_2&key=" + RequestURL.GOOGLE_API_KEY;

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, requestUrl, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //data
                        try {
                            String GPSCity = response.getJSONArray("results").getJSONObject(0).getJSONArray("address_components").getJSONObject(0).getString("short_name");
                            LogUtils.e(TAG, "city: " + GPSCity);
                            SharedUtils.putKey(Splash.this, "GPSCity", GPSCity);
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
                            Toast.makeText(Splash.this, error.getMessage(), Toast.LENGTH_SHORT);
                        } else if (error instanceof ServerError) {
                            Toast.makeText(Splash.this, error.getMessage(), Toast.LENGTH_SHORT);
                        } else if (error instanceof AuthFailureError) {
                        } else if (error instanceof ParseError) {
                        } else if (error instanceof NoConnectionError) {
                            Toast.makeText(Splash.this, error.getMessage(), Toast.LENGTH_SHORT);
                        } else if (error instanceof TimeoutError) {
                            Toast.makeText(Splash.this, error.getMessage(), Toast.LENGTH_SHORT);
                        }
                    }

                }
        );
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }
}