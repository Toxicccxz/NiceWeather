package com.prodapt.weather.utils;

import com.prodapt.weather.BuildConfig;

public class LogUtils {
    private static boolean debugMod = BuildConfig.DEBUG;

    public static void v(String tag, String message) {
        if (debugMod)
            android.util.Log.v(tag, message);
    }

    public static void d(String tag, String message) {
        if (debugMod)
            android.util.Log.d(tag, message);
    }

    public static void i(String tag, String message) {
        if (debugMod)
            android.util.Log.i(tag, message);
    }


    public static void w(String tag, String message, Exception exception) {
        if (debugMod)
            android.util.Log.w(tag, message);
    }

    public static void e(String tag, String message) {
        if (debugMod)
            if (tag == null || tag.length() == 0
                    || message == null || message.length() == 0)
                return;

        int segmentSize = 3 * 1024;
        long length = message.length();
        if (length <= segmentSize ) {
            android.util.Log.e(tag, message);
        }else {
            while (message.length() > segmentSize ) {
                String logContent = message.substring(0, segmentSize );
                message = message.replace(logContent, "");
                android.util.Log.e(tag, logContent);
            }
            android.util.Log.e(tag, message);
        }

    }
}