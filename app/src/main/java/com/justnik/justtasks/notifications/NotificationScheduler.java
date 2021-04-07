package com.justnik.justtasks.notifications;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.justnik.justtasks.R;
import com.justnik.justtasks.view.TaskActivity;

public class NotificationScheduler {
    Context context;
    private final String CHANNEL_ID = "just_task_channel";

    public void scheduleNotification(Context context, long timeMillis,String title) {
        this.context = context;
        Log.d("TAG", "scheduling 22222222222222222222222222222222222222222");
        createNotificationChannel();
        Intent intent = new Intent(context, TaskNotificationPublisher.class);
        intent.putExtra("notification",createNotification(title));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, timeMillis + 6000, pendingIntent);

    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,
                    "JustTaskNotifications", NotificationManager.IMPORTANCE_DEFAULT);
            ((NotificationManager) context.getSystemService(context.getApplicationContext().NOTIFICATION_SERVICE))
                    .createNotificationChannel(notificationChannel);
        }
    }

    private Notification createNotification(String title) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_task_alt_black_24dp)
                .setContentTitle("You gonna miss your task!")
                .setContentText(title)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        return builder.build();
    }
}
