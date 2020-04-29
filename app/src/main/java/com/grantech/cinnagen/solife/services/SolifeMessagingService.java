package com.grantech.cinnagen.solife.services;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.grantech.cinnagen.solife.utils.Fragments;

import java.util.Map;

public class SolifeMessagingService extends FirebaseMessagingService {

    @WorkerThread
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        RemoteMessage.Notification n = remoteMessage.getNotification();
        Map<String, String> data= remoteMessage.getData();
//        Log.i(Fragments.TAG, n.getTitle() + "-=-=" + n.getBody());
        for (Map.Entry e :data.entrySet())
            Log.i(Fragments.TAG, e.getKey() + "-=-=" + e.getValue());
    }
}
