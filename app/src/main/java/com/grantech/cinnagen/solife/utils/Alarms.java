package com.grantech.cinnagen.solife.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import java.util.Map;
import java.util.Random;

public class Alarms
{
    public static int schedule(Context context, Class<?> cls, long time, long interval, String ticker, String title, String text) {
        return schedule(context, cls, time, interval, ticker, title, text, null, null, null, null);
    }
    public static int schedule(Context context, Class<?> cls, long time, long interval, String ticker, String title, String text, String info, String data, String icon, String sound){
        Bundle bundle = new Bundle();
        bundle.putString("ticker", ticker);
        bundle.putString("title", title);
        bundle.putString("text", text);
        bundle.putString("info", info);
        bundle.putString("data", data);
        bundle.putString("icon", icon);
        bundle.putString("sound", sound);
        return set(context, cls, bundle, time, interval);
    }

    public static int set(Context context, Class<?> cls, Bundle bundle, long time, long interval)
    {
        Intent intent = new Intent(context, cls);

        int id = getRandomID(context);
        bundle.putInt("id", id);
        intent.putExtras(bundle);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        assert alarmManager != null;
        if (interval > 1)
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time, interval, pendingIntent); // Millisec * Second * Minute
        else
            alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
        Log.i(Fragments.TAG, "setRepeating " + cls.getSimpleName() + " " + time + " " + interval + " " + id);
        return id;
    }

    public static void cancel(Context context, Class<?> cls, int id)
    {
        Intent intent = new Intent(context, cls);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        SharedPreferences sharedPreferences = context.getSharedPreferences("messages", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        assert alarmManager != null;

        if(id == -1)
        {
            Map<String, Boolean> messagesMap = (Map<String, Boolean>) sharedPreferences.getAll();
            for (Map.Entry<String, Boolean> entry : messagesMap.entrySet())
            {
                //Log.w(AndroidExtension.LOG_TAG, entry.getKey()+" -> "+entry.getValue());
                id = Integer.parseInt(entry.getKey());
                pendingIntent = PendingIntent.getBroadcast(context, id, intent, 0);
                alarmManager.cancel(pendingIntent);
            }
            editor.clear();
        }
        else
        {
            alarmManager.cancel(pendingIntent);
            editor.remove(id+"");
        }
        editor.apply();
        //Toast.makeText(context, notiID+" canceled", Toast.LENGTH_LONG).show();
    }


    private static int getRandomID(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("messages", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int ret = getRandomInt(sharedPreferences, editor);
        editor.putBoolean(ret+"", true);
        editor.apply();
        return ret;
    }

    private static int getRandomInt(SharedPreferences sharedPreferences, SharedPreferences.Editor editor)
    {
        Random r = new Random();
        int ret = r.nextInt(100);
        if(sharedPreferences.contains(""+ret))
        {
            if(sharedPreferences.getBoolean(""+ret, false))
                editor.remove(""+ret);
            else
                ret = getRandomInt(sharedPreferences, editor);
        }
        return ret;
    }
}
