/**
  * Copyright 2022 bejson.com 
  */
package com.prodapt.weather.model;
import java.util.List;

/**
 * Auto-generated: 2022-09-23 16:59:2
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Current {

    public static long dt;
    public static long sunrise;
    public static long sunset;
    public static double temp;
    public static double feels_like;
    public static int pressure;
    public static int humidity;
    public static double dew_point;
    public static double uvi;
    public static int clouds;
    public static int visibility;
    public static double wind_speed;
    public static int wind_deg;
    public static double wind_gust;
    public static List<Weather> weather;
    public static List<Rain> rain;
    public static void setDt(long dt) {
         Current.dt = dt;
     }
     public static long getDt() {
         return dt;
     }

    public static void setSunrise(long sunrise) {
        Current.sunrise = sunrise;
     }
     public static long getSunrise() {
         return sunrise;
     }

    public static void setSunset(long sunset) {
         Current.sunset = sunset;
     }
     public static long getSunset() {
         return sunset;
     }

    public static void setTemp(double temp) {
         Current.temp = temp;
     }
     public static double getTemp() {
         return temp;
     }

    public static void setFeels_like(double feels_like) {
         Current.feels_like = feels_like;
     }
     public static double getFeels_like() {
         return feels_like;
     }

    public static void setPressure(int pressure) {
         Current.pressure = pressure;
     }
     public static int getPressure() {
         return pressure;
     }

    public static void setHumidity(int humidity) {
         Current.humidity = humidity;
     }
     public static int getHumidity() {
         return humidity;
     }

    public static void setDew_point(double dew_point) {
         Current.dew_point = dew_point;
     }
     public static double getDew_point() {
         return dew_point;
     }

    public static void setUvi(double uvi) {
         Current.uvi = uvi;
     }
     public static double getUvi() {
         return uvi;
     }

    public static void setClouds(int clouds) {
         Current.clouds = clouds;
     }
     public static int getClouds() {
         return clouds;
     }

    public static void setVisibility(int visibility) {
         Current.visibility = visibility;
     }
     public static int getVisibility() {
         return visibility;
     }

    public static void setWind_speed(double wind_speed) {
         Current.wind_speed = wind_speed;
     }
     public static double getWind_speed() {
         return wind_speed;
     }

    public static void setWind_deg(int wind_deg) {
         Current.wind_deg = wind_deg;
     }
     public static int getWind_deg() {
         return wind_deg;
     }

    public static void setWind_gust(double wind_gust) {
         Current.wind_gust = wind_gust;
     }
     public static double getWind_gust() {
         return wind_gust;
     }

    public static void setWeather(List<Weather> weather) {
         Current.weather = weather;
     }
     public static List<Weather> getWeather() {
         return weather;
     }

}