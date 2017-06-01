package com.stepstone.rxjava2showcase.util;

import android.util.Log;

/**
 * @author Robert Zagórski (robert.zagorski@stepstone.com) on 04/05/2017.
 */

public class Logger {

    public static void d(String tag, String message) {
        try {
            Log.d(tag, message);
        } catch (RuntimeException e) {
            System.out.println(tag + ": " + message);
        }
    }

    public static void e(String tag, String message, Throwable throwable) {
        try {
            Log.e(tag, message, throwable);
        } catch (RuntimeException e) {
            System.out.println(tag + message);
            throwable.printStackTrace();
        }
    }
}
