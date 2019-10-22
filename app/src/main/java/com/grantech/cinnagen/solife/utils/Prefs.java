package com.grantech.cinnagen.solife.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

public class Prefs
{
    static private Prefs instance;
    private SharedPreferences sharedPref;

    static public String KEY_NUM_RUN = "numRun";
    static public String KEY_DOSE_MG= "doseMG";
    static public String KEY_DOSE_GAP= "doseGap";

    static public Prefs getInstance() {
        return instance;
    }
    static public void setInstance(Context context)
    {
        instance = new Prefs();
        instance.sharedPref = context.getSharedPreferences("solife", Context.MODE_PRIVATE);
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
}