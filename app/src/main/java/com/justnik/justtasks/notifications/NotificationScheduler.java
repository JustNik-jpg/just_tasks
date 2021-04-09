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

    public void scheduleNotification(Context context, long timeMillis,String title,int id) {
        this.context = context;
        Log.d("Notification", "Scheduling Notification "+title+" "+id);
        createNotificationChannel();
        Intent intent = new Intent(context, TaskNotificationPublisher.class);
        intent.putExtra("notification",createNotification(title));
        intent.putExtra("ID",id);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, timeMillis, pendingIntent);

    }

    public void cancelNotification(Context context, String title, int id){
        this.context = context;
        Log.d("Canceling Notification",id+"");
        Intent intent = new Intent(context, TaskNotificationPublisher.class);
        intent.putExtra("notification",createNotification(title));
        intent.putExtra("ID",id);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        pendingIntent.cancel();
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
