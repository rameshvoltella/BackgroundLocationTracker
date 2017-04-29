/*******************************************************************************
 * Title:Common Shared Preference class
 * Authour:Voltella
 *******************************************************************************/

package com.ramz.locationtracker.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class AppPreferences {
    // private static final String APP_SHARED_PREFS =
    // "com.aydabtu.BroadcastSMS_preferences"; // Name of the file -.xml
    private SharedPreferences appSharedPrefs;
    private Editor prefsEditor;

    public AppPreferences(Context context, String Preferncename) {
        this.appSharedPrefs = context.getSharedPreferences(Preferncename,
                Activity.MODE_PRIVATE);
        this.prefsEditor = appSharedPrefs.edit();
    }

    /**
     * *
     * <p/>
     * getdata() get the value from the preference
     */
    public String getData(String key) {
        return appSharedPrefs.getString(key, "");
    }

    public void clearpref(Context prefcontect, String name) {
        this.appSharedPrefs = prefcontect.getSharedPreferences(
                name, 0);
        this.appSharedPrefs.edit().clear().commit();
    }

    /**
     * *
     * <p/>
     * SaveData() save the value to the preference
     */
    public void SaveData(String Tag, String text) {
        prefsEditor.putString(Tag, text);
        prefsEditor.commit();
    }

    public int getIntData(String key) {
        // return appSharedPrefs.getString(key, "");
        return appSharedPrefs.getInt(key, 1);
    }

    /**
     * *
     * <p/>
     * SaveData() save the value to the preference
     */
    public void SaveIntData(String text, Integer Tag) {
        // prefsEditor.putString(Tag, text);
        prefsEditor.putInt(text, Tag);
        prefsEditor.commit();
    }
}