package com.resmenu.constants;

import android.content.Context;
import android.content.SharedPreferences;

import com.resmenu.base.ResMenuApplication;

import static com.resmenu.constants.AppConstants.SHaredPrefKeys.SHAREDPREFRESMENU;

public class SharedPreferenceManager {

    public static SharedPreferenceManager sharedPreferenceManager;
    public SharedPreferenceManager() {

    }

    private static SharedPreferences getSharedPreferences() {
        return ResMenuApplication.getContext().getSharedPreferences(SHAREDPREFRESMENU, Context.MODE_PRIVATE);
    }

    public static SharedPreferenceManager getInstance() {
        if (sharedPreferenceManager == null)
            sharedPreferenceManager = new SharedPreferenceManager();
        return sharedPreferenceManager;
    }

    // shared preferences method to store string value
    public static void store(String key, String value) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    // shared preferences method to store boolean value
    public static void storeBoolean(String key,boolean value){
        SharedPreferences sharedPreferences = getSharedPreferences();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key,value);
        editor.commit();
    }

    public static String get(String key, String defaultValue) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        String value = sharedPreferences.getString(key, defaultValue);
        return value;
    }
    public static int getInt(String key) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        int value = sharedPreferences.getInt(key, 0);
        return value;
    }
    public static void storeInt(String key, int value) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }


    public static boolean getBoolean(String key , boolean value){

        SharedPreferences sharedPreferences = getSharedPreferences();
        boolean result = sharedPreferences.getBoolean(key,value);
        return result;
    }
    public static void delete(String key) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }
}
