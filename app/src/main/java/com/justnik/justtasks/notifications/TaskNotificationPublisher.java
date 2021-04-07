package com.justnik.justtasks.notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.justnik.justtasks.R;

public class TaskNotificationPublisher extends BroadcastReceiver {




    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        Notification notification = intent.getParcelableExtra("notification");
        Log.d("TAG", "onReceive: "+notification);
        notificationManager.notify(1, notification);
    }


}
