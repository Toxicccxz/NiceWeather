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
public class ResponseJSON {

    private double lat;
    private double lon;
    private String timezone;
    private int timezone_offset;
    private Current current;
    public static List<Daily> daily;
    public void setLat(double lat) {
         this.lat = lat;
     }
     public double getLat() {
         return lat;
     }

    public void setLon(double lon) {
         this.lon = lon;
     }
     public double getLon() {
         return lon;
     }

    public void setTimezone(String timezone) {
         this.timezone = timezone;
     }
     public String getTimezone() {
         return timezone;
     }

    public void setTimezone_offset(int timezone_offset) {
         this.timezone_offset = timezone_offset;
     }
     public int getTimezone_offset() {
         return timezone_offset;
     }

    public void setCurrent(Current current) {
         this.current = current;
     }
     public Current getCurrent() {
         return current;
     }

    public static void setDaily(List<Daily> daily) {
         ResponseJSON.daily = daily;
     }
     public static List<Daily> getDaily() {
         return daily;
     }

}