package com.ramz.locationtracker.utils;

import android.graphics.Color;

import com.ramz.locationtracker.R;

public final class Constants {

    // Milliseconds per second
    private static final int MILLISECONDS_PER_SECOND = 1000;
    // Update frequency in seconds
    private static final int UPDATE_INTERVAL_IN_SECONDS = 50;
    // Update frequency in milliseconds
    public static final long UPDATE_INTERVAL = MILLISECONDS_PER_SECOND * UPDATE_INTERVAL_IN_SECONDS;
    // The fastest update frequency, in seconds
    private static final int FASTEST_INTERVAL_IN_SECONDS = 10;
    // A fast frequency ceiling in milliseconds
    public static final long FASTEST_INTERVAL = MILLISECONDS_PER_SECOND * FASTEST_INTERVAL_IN_SECONDS;

    // Stores the connect / disconnect data in a text file
    public static final String _Id = "id";

    public static final String RUNNING = "runningInBackground"; // Recording data in background

    public static final String APP_PACKAGE_NAME = "com.ramz.locationtracker";

    public static final int FILL_COLOR = R.color.polylineFillColor ;
    public static final int STROKE_COLOR = R.color.polylineStrokeColor;

    public static final float POLYLINE_WIDTH_IN_PIXELS = 10;
    public static final float POLYLINE_STROKE_WIDTH_IN_PIXELS = 2;
    public static final int ANIMATE_COLOR  = Color.parseColor("#FFA7A6A6");;


    public interface ACTION {
        public static String STARTFOREGROUND_ACTION = "com.ramz.locationtracker.action.startforeground";
        public static String STOPFOREGROUND_ACTION = "com.ramz.locationtracker.action.stopforeground";
        public static String MAIN_ACTION = "com.ramz.locationtracker.action.main";
    }



    /**
     * Suppress default constructor for noninstantiability
     */
    private Constants() {
        throw new AssertionError();
    }
    public interface NOTIFICATION_ID {
        public static int FOREGROUND_SERVICE = 101;
    }
}