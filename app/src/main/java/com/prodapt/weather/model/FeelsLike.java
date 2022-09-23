/* Copyright 2022 freecodeformat.com */
package com.prodapt.weather.model;

/* Time: 2022-09-23 16:46:46 @author freecodeformat.com @website http://www.freecodeformat.com/json2javabean.php */
public class FeelsLike {

    private double day;
    private double night;
    private double eve;
    private double morn;
    public void setDay(double day) {
         this.day = day;
     }
     public double getDay() {
         return day;
     }

    public void setNight(double night) {
         this.night = night;
     }
     public double getNight() {
         return night;
     }

    public void setEve(double eve) {
         this.eve = eve;
     }
     public double getEve() {
         return eve;
     }

    public void setMorn(double morn) {
         this.morn = morn;
     }
     public double getMorn() {
         return morn;
     }

}