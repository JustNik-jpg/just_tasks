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

import com.google.gson.JsonObject;
import com.justnik.justtasks.R;

public class NotificationScheduler {
    Context context;
    private final String CHANNEL_ID = "just_task_channel";
    private static final String NOTIF_TAG = "JustNotifications";

    public void scheduleNotification(Context context, long timeMillis, String title, int id) {
        this.context = context;
        Log.d(NOTIF_TAG, "Scheduling Notification. Title:" + title + " ID: " + id + " Time:" + timeMillis);
        createNotificationChannel();
        NotificationJSONHelper helper = new NotificationJSONHelper();
        //Creating notification JSON to reschedule it after restart

        //Writing JSON to a file if it doesn't exists
        if (!helper.checkForFile(context, id + "")) {
            JsonObject json = new JsonObject();
            json.addProperty("Time", timeMillis);
            json.addProperty("Title", title);
            json.addProperty("ID", id);
            helper.writeNotifJSON(context, id, json);
        }

        Intent intent = new Intent(context, TaskNotificationPublisher.class);
        Log.d(NOTIF_TAG, "Creating intent: "+ intent);
        Notification notification = createNotification(title);
        Log.d(NOTIF_TAG, "Creating notification: "+ notification);
        intent.putExtra("notification", notification);
        intent.putExtra("ID", id);
        Log.d(NOTIF_TAG, "Finished intent: "+ intent);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, timeMillis, pendingIntent);

    }

    public void cancelNotification(Context context, int id) {
        this.context = context;
        Log.d(NOTIF_TAG, "Canceling notification: " + id);

        //Deleting notification JSON if it exists
        NotificationJSONHelper helper = new NotificationJSONHelper();
        helper.deleteNotifJSON(context, id);

        Intent intent = new Intent(context, TaskNotificationPublisher.class);
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
