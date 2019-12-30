package com.grantech.cinnagen.solife.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.grantech.cinnagen.solife.R;

public class AlarmReceiver extends BroadcastReceiver
{
    public void onReceive(Context context, Intent intent)
    {
        Bundle bundle = intent.getExtras();
        int notiID = bundle.getInt("id");
        String ticker = bundle.getString("ticker");
        String title = bundle.getString("title");
        String text = bundle.getString("text");
        String info = bundle.getString("info");
//      String notiData = bundle.getString("data");
        String icon = bundle.getString("icon");
        String sound = bundle.getString("sound");

        AlarmReceiver.notify(context, notiID, ticker, title, text, info, icon, sound);

//        Log.i(AndroidExtension.LOG_TAG, "{\"id\":\""+notiID+"\",\"ticker\":\""+notiTicker+"\",\"title\":\""+notiTitle+"\",\"text\":\""+notiText+"\",\"info\":\""+notiInfo+"\",\"data\":"+notiData+"}"+ notiIcon+ notiSound);
//        if( extensionContext != null )
//            extensionContext.dispatchStatusEventAsync("LocalNotificationReceived", "{\"id\":\""+notiID+"\",\"ticker\":\""+notiTicker+"\",\"title\":\""+notiTitle+"\",\"text\":\""+notiText+"\",\"info\":\""+notiInfo+"\",\"data\":"+notiData+"}");
//        AlarmsManager.cancel(context, LocalNotificationReceiver.class, notiID);
    }

    public static void notify(Context context, int id, String ticker, String title, String text, String info, String icon, String sound)
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, id + "")
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                .setSmallIcon(R.mipmap.ic_launcher_mono)
                .setContentTitle(title)
                .setContentText(text)
                .setContentInfo(info)
                .setStyle(new NotificationCompat.BigTextStyle()
                .bigText(text))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat.from(context).notify(id, builder.build());
    }
}