package com.prodapt.weather.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedUtils {

    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;

    public static void putKey(Context context, String Key, String Value) {
        sharedPreferences = context.getSharedPreferences("prodapt", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(Key, Value);
        editor.apply();

    }

    public static String getKey(Context contextGetKey, String Key) {
        sharedPreferences = contextGetKey.getSharedPreferences("prodapt", Context.MODE_PRIVATE);
        return sharedPreferences.getString(Key, "");
    }

    public static String getKey(Context contextGetKey, String Key, String defaultValue) {
        sharedPreferences = contextGetKey.getSharedPreferences("prodapt", Context.MODE_PRIVATE);
        return sharedPreferences.getString(Key, defaultValue);
    }

    public static void clearSharedPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences("prodapt", Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
    }
}
