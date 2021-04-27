package com.justnik.justtasks.notifications;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationManagerCompat;

public class TaskNotificationPublisher extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        Notification notification = intent.getParcelableExtra("notification");
        int id  = intent.getIntExtra("ID",-1);
        Log.d("Notification", "Received notification with id: "+" "+id);

        if (id == -1) {
            Log.d("Notification", "Invalid id");
        } else {
            notificationManager.notify(1, notification);
            NotificationJSONHelper helper = new NotificationJSONHelper();
            helper.deleteNotifJSON(context,id);
        }
    }

}
