package com.ramz.locationtracker.recevier;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.ramz.locationtracker.services.BackgroundLocationService;
import com.ramz.locationtracker.utils.Constants;

public class LocationLoggerServiceManager extends BroadcastReceiver {

    private SharedPreferences mPrefs;
    public static final String TAG = "LLoggerServiceManager";
    @Override
    public void onReceive(Context context, Intent intent) {
        // Make sure we are getting the right intent
        if( "android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            boolean mUpdatesRequested = false;
            // Open the shared preferences
            mPrefs = context.getSharedPreferences(
                    Constants.APP_PACKAGE_NAME, Context.MODE_PRIVATE);
	        /*
	         * Get any previous setting for location updates
	         * Gets "false" if an error occurs
	         */
            if (mPrefs.contains(Constants.RUNNING)) {
                mUpdatesRequested = mPrefs.getBoolean(Constants.RUNNING, false);
            }
            if(mUpdatesRequested){
                ComponentName comp = new ComponentName(context.getPackageName(), BackgroundLocationService.class.getName());
                ComponentName service = context.startService(new Intent().setComponent(comp));

                if (null == service){
                    // something really wrong here
                    Log.e(TAG, "Could not start service " + comp.toString());
                }
            }

        } else {
            Log.e(TAG, "Received unexpected intent " + intent.toString());
        }
    }
}