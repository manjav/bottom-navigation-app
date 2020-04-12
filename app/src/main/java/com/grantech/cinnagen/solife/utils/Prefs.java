package com.grantech.cinnagen.solife.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import java.util.TimeZone;

public class Prefs
{
    private SharedPreferences sharedPref;

    static private Prefs instance;
    static public final String KEY_NUM_RUN = "numRun";
    static public final String KEY_LOC = "loc";
    static public final String KEY_DOSE_MG = "doseMG";
    static public final String KEY_DOSE_GAP = "doseGap";
    static public final String KEY_PREV = "prev";
    static public final String KEY_PREV_X = "prevX";
    static public final String KEY_PREV_Y = "prevY";
    static public final String KEY_NEXT = "next";
    static public final String KEY_ALARM_1 = "alarm1";
    static public final String KEY_ALARM_2 = "alarm2";
    static public final String KEY_ALARM_3 = "alarm3";
    public static final String KEY_ALARM_NOTIFICATION = "alarmNotify";
    public static final String KEY_INJECT_SETTINGS = "injectSettings";

    static public Prefs getInstance() {
        return instance;
    }
    static public void setInstance(Context context)
    {
        instance = new Prefs();
        instance.sharedPref = context.getSharedPreferences("solife", Context.MODE_PRIVATE);

        // locale
        if( !instance.contains(KEY_LOC) )
            instance.setString(KEY_LOC, getDefaultLocale());
    }

    private static String getDefaultLocale() {
        switch( TimeZone.getDefault().getID() )
        {
            case "Asia/Kabul":
            case "Asia/Tehran":
            case "Asia/Dushanbe":
                return "fa";
        }
        return "en";
    }

    public boolean contains(String key)
    {
        return sharedPref.contains(key);
    }

    public int getInt(String key, int defValue)
    {
        return sharedPref.getInt(key, defValue);
    }
    public void setInt(String key, int value)
    {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, value);
        editor.apply();
    }
    public long getLong(String key, long defValue)
    {
        return sharedPref.getLong(key, defValue);
    }
    public void setLong(String key, long value)
    {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public String getString(String key, @Nullable String defValue)
    {
        return sharedPref.getString(key, defValue);
    }
    public void setString(String key, String value)
    {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public boolean getBoolean(String key, @Nullable boolean defValue)
    {
        return sharedPref.getBoolean(key, defValue);
    }
    public void setBoolean(String key, boolean value)
    {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }
}
