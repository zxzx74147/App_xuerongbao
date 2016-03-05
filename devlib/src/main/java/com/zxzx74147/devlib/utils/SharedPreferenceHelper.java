package com.zxzx74147.devlib.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.zxzx74147.devlib.ZXApplicationDelegate;

public class SharedPreferenceHelper {

    private static final String SHARED_PREFERENCE = "zx_devlib";

    public static void saveInt(String key, int value) {
        SharedPreferences sp = ZXApplicationDelegate.getApplication().getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static int getInt(String key) {
        SharedPreferences sp = ZXApplicationDelegate.getApplication().getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE);
        return sp.getInt(key, 0);
    }

    public static int getInt(String key, int defaultValue) {
        SharedPreferences sp = ZXApplicationDelegate.getApplication().getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE);
        return sp.getInt(key, defaultValue);
    }

    public static void saveBoolean(String key, boolean value) {
        SharedPreferences sp = ZXApplicationDelegate.getApplication().getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static boolean getBoolean(String key) {
        SharedPreferences sp = ZXApplicationDelegate.getApplication().getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE);
        return sp.getBoolean(key, false);
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        SharedPreferences sp = ZXApplicationDelegate.getApplication().getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defaultValue);
    }

    public static void saveLong(String key, long value) {
        SharedPreferences sp = ZXApplicationDelegate.getApplication().getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public static long getLong(String key) {
        SharedPreferences sp = ZXApplicationDelegate.getApplication().getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE);
        return sp.getLong(key, 0);
    }

    public static long getLong(String key, long defaultValue) {
        SharedPreferences sp = ZXApplicationDelegate.getApplication().getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE);
        return sp.getLong(key, defaultValue);
    }

    public static void saveString(String key, String value) {
        SharedPreferences sp = ZXApplicationDelegate.getApplication().getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getString(String key) {
        SharedPreferences sp = ZXApplicationDelegate.getApplication().getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE);
        return sp.getString(key, "");
    }

    public static String getString(String key, String defaultValue) {
        SharedPreferences sp = ZXApplicationDelegate.getApplication().getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE);
        return sp.getString(key, defaultValue);
    }
}
