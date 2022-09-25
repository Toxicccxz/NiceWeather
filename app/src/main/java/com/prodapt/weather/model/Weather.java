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
public class Weather {

    public static int id;
    public static String main;
    public static String description;
    public static String icon;
    public static void setId(int id) {
        Weather.id = id;
    }
    public static int getId() {
        return id;
    }

    public static void setMain(String main) {
        Weather.main = main;
    }
    public static String getMain() {
        return main;
    }

    public static void setDescription(String description) {
        Weather.description = description;
    }
    public static String getDescription() {
        return description;
    }

    public static void setIcon(String icon) {
        Weather.icon = icon;
    }
    public static String getIcon() {
        return icon;
    }

}