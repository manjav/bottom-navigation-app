package com.grantech.cinnagen.solife.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.TimeZone;

public class Prefs
{
    public List<Boolean> pages;
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
    public static final String KEY_SETTINGS_PAGES = "settingsPages";

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

        // pages
        if( instance.contains(KEY_SETTINGS_PAGES) ) {
            instance.pages = (List<Boolean>) instance.getObject(KEY_SETTINGS_PAGES, null);
        } else {
            instance.pages = Arrays.asList(true,true,true,true,true,true);
            instance.setObject(KEY_SETTINGS_PAGES, instance.pages);
        }
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

    @SuppressLint("NewApi")
    public Object getObject(String key, @Nullable Object defValue)
    {
        byte[] data = Base64.getDecoder().decode(sharedPref.getString(key, null));
        ObjectInputStream ois = null;
        Object o = null;
        try {
            ois = new ObjectInputStream(new ByteArrayInputStream(data));
            o = ois.readObject();
            ois.close();
        } catch (Exception e) { e.printStackTrace(); }
        return o;
    }

    @SuppressLint("NewApi")
    public void setObject(String key, Object value)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream( baos );
            oos.writeObject(value);
            oos.close();
        } catch (Exception e) { e.printStackTrace(); }

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, Base64.getEncoder().encodeToString(baos.toByteArray()));
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
