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

    private long dt;
    private long sunrise;
    private long sunset;
    private double temp;
    private double feels_like;
    private int pressure;
    private int humidity;
    private double dew_point;
    private double uvi;
    private int clouds;
    private int visibility;
    private double wind_speed;
    private int wind_deg;
    private double wind_gust;
    private List<Weather> weather;
    public void setDt(long dt) {
         this.dt = dt;
     }
     public long getDt() {
         return dt;
     }

    public void setSunrise(long sunrise) {
         this.sunrise = sunrise;
     }
     public long getSunrise() {
         return sunrise;
     }

    public void setSunset(long sunset) {
         this.sunset = sunset;
     }
     public long getSunset() {
         return sunset;
     }

    public void setTemp(double temp) {
         this.temp = temp;
     }
     public double getTemp() {
         return temp;
     }

    public void setFeels_like(double feels_like) {
         this.feels_like = feels_like;
     }
     public double getFeels_like() {
         return feels_like;
     }

    public void setPressure(int pressure) {
         this.pressure = pressure;
     }
     public int getPressure() {
         return pressure;
     }

    public void setHumidity(int humidity) {
         this.humidity = humidity;
     }
     public int getHumidity() {
         return humidity;
     }

    public void setDew_point(double dew_point) {
         this.dew_point = dew_point;
     }
     public double getDew_point() {
         return dew_point;
     }

    public void setUvi(double uvi) {
         this.uvi = uvi;
     }
     public double getUvi() {
         return uvi;
     }

    public void setClouds(int clouds) {
         this.clouds = clouds;
     }
     public int getClouds() {
         return clouds;
     }

    public void setVisibility(int visibility) {
         this.visibility = visibility;
     }
     public int getVisibility() {
         return visibility;
     }

    public void setWind_speed(double wind_speed) {
         this.wind_speed = wind_speed;
     }
     public double getWind_speed() {
         return wind_speed;
     }

    public void setWind_deg(int wind_deg) {
         this.wind_deg = wind_deg;
     }
     public int getWind_deg() {
         return wind_deg;
     }

    public void setWind_gust(double wind_gust) {
         this.wind_gust = wind_gust;
     }
     public double getWind_gust() {
         return wind_gust;
     }

    public void setWeather(List<Weather> weather) {
         this.weather = weather;
     }
     public List<Weather> getWeather() {
         return weather;
     }

}