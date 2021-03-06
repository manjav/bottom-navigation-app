package com.grantech.cinnagen.solife.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.grantech.cinnagen.solife.R;
import com.grantech.cinnagen.solife.activities.SplashScreenActivity;

public class AlarmReceiver extends BroadcastReceiver
{
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Bundle bundle = intent.getExtras();
        int notiID = bundle.getInt("id");
        String ticker = bundle.getString("ticker");
        String title = bundle.getString("title");
        String text = bundle.getString("text");
        String info = bundle.getString("info");
        String data = bundle.getString("data");
        String icon = bundle.getString("icon");
        String sound = bundle.getString("sound");

        notify(context, notiID, ticker, title, text, info, data, icon, sound);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void notify(Context context, int id, String ticker, String title, String text, String info, String data, String icon, String sound)
    {
        Intent intent = new Intent(context, SplashScreenActivity.class);
        intent.putExtra("data", data);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notificationManager =(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel("solife-injection", "Injection Channel", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setShowBadge(true);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 350, 500});
            notificationManager.createNotificationChannel(notificationChannel);

            builder = new NotificationCompat.Builder(context, notificationChannel.getId());
        }else{
            builder = new NotificationCompat.Builder(context);
        }
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                .setSmallIcon(R.mipmap.ic_launcher_mono)
                .setContentTitle(title)
                .setContentText(text)
                .setContentInfo(info)
                .setStyle(new NotificationCompat.BigTextStyle()
                .bigText(text))
                .setContentIntent(contentIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat.from(context).notify(id, builder.build());
    }
}