package com.prodapt.weather.view;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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
import com.prodapt.weather.utils.LogUtils;
import com.prodapt.weather.utils.RequestURL;
import com.prodapt.weather.utils.SharedUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Splash extends AppCompatActivity {

    private Context context;
    private String TAG = "Splash";
    private FusedLocationProviderClient fusedLocationClient;
    protected int LOCATION_PERMISSION_REQUEST_CODE = 1;
    protected double latitude, longitude;
    private Handler handler;

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
                            SharedUtils.putKey(context, "latitude", String.valueOf(location.getLatitude()));
                            SharedUtils.putKey(context, "longitude", String.valueOf(location.getLongitude()));

                            request(location.getLatitude(), location.getLongitude());

                            latitude = location.getLatitude();
                            longitude = location.getLatitude();
                            LogUtils.e(TAG, " = " + latitude + "  longitude = " + location.getLongitude() );
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
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, RequestURL.API_URL + "?lat=" + lat + "&lon=" + lon + "&appid=" + RequestURL.API_KEY , null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        LogUtils.e(TAG, "onResponse: " + response.toString());

                        pDialog.dismiss();

//                        try {
//                            String title = response.getString("title");
//
//                            JSONObject description = response.getJSONObject("description");
//                            String description_text = description.getString("text");
//
//                            JSONArray forecasts = response.getJSONArray("forecasts");
//                            for (int i = 0; i < forecasts.length(); i++) {
//                                JSONObject forecast = forecasts.getJSONObject(i);
//                                String date = forecast.getString("date");
//                                String telop = forecast.getString("telop");
////                                    adapter.add(date + ":" + telop);
//                            }
//                        } catch (JSONException e) {
//                            LogUtils.e(TAG, e.getMessage());
//                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.hide();

                        LogUtils.d(TAG, "Error: " + error.getMessage());

                        if (error instanceof NetworkError) {
                        } else if (error instanceof ServerError) {
                        } else if (error instanceof AuthFailureError) {
                        } else if (error instanceof ParseError) {
                        } else if (error instanceof NoConnectionError) {
                        } else if (error instanceof TimeoutError) {
                        }
                    }

                }
        );

        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }
}