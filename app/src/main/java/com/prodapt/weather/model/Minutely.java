/**
  * Copyright 2022 bejson.com 
  */
package com.prodapt.weather.model;

/**
 * Auto-generated: 2022-09-23 16:59:2
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Minutely {

    private long dt;
    private int precipitation;
    public void setDt(long dt) {
         this.dt = dt;
     }
     public long getDt() {
         return dt;
     }

    public void setPrecipitation(int precipitation) {
         this.precipitation = precipitation;
     }
     public int getPrecipitation() {
         return precipitation;
     }

}