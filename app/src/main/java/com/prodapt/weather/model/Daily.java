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
public class Daily {

    private long dt;
    private long sunrise;
    private long sunset;
    private long moonrise;
    private long moonset;
    private double moon_phase;
    private Temp temp;
    private Feels_like feels_like;
    private int pressure;
    private int humidity;
    private double dew_point;
    private double wind_speed;
    private int wind_deg;
    private double wind_gust;
    private List<Weather> weather;
    private int clouds;
    private int pop;
    private double uvi;
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

    public void setMoonrise(long moonrise) {
         this.moonrise = moonrise;
     }
     public long getMoonrise() {
         return moonrise;
     }

    public void setMoonset(long moonset) {
         this.moonset = moonset;
     }
     public long getMoonset() {
         return moonset;
     }

    public void setMoon_phase(double moon_phase) {
         this.moon_phase = moon_phase;
     }
     public double getMoon_phase() {
         return moon_phase;
     }

    public void setTemp(Temp temp) {
         this.temp = temp;
     }
     public Temp getTemp() {
         return temp;
     }

    public void setFeels_like(Feels_like feels_like) {
         this.feels_like = feels_like;
     }
     public Feels_like getFeels_like() {
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

    public void setClouds(int clouds) {
         this.clouds = clouds;
     }
     public int getClouds() {
         return clouds;
     }

    public void setPop(int pop) {
         this.pop = pop;
     }
     public int getPop() {
         return pop;
     }

    public void setUvi(double uvi) {
         this.uvi = uvi;
     }
     public double getUvi() {
         return uvi;
     }

}