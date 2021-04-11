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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class NotificationScheduler {
    Context context;
    private final String CHANNEL_ID = "just_task_channel";
    private static final String NOTIF_TAG = "JustNotifications";

    public void scheduleNotification(Context context, long timeMillis, String title, int id) {
        this.context = context;
        Log.d(NOTIF_TAG, "Scheduling Notification " + title + " " + id);
        createNotificationChannel();

        //Creating notification JSON to reschedule it after restart
        JsonObject json = new JsonObject();
        json.addProperty("Time",timeMillis);
        json.addProperty("Title", title);
        json.addProperty("ID", id);
        //Writing JSOn to a file
        File file = new File(context.getFilesDir() + "/notification_" + id+".json");
        if (!file.exists()){
            try {

                //PrintWriter bw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
                Log.d(NOTIF_TAG, "Writing notification json to a file " + json.toString());
                //bw.write(json.toString());
                FileOutputStream fos = new FileOutputStream(file);
                String text = json.toString();
                fos.write(text.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(NOTIF_TAG, "Something went wrong... Notification won't be rescheduled");
            }
        }


        Intent intent = new Intent(context, TaskNotificationPublisher.class);
        intent.putExtra("notification", createNotification(title));
        intent.putExtra("ID", id);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, timeMillis, pendingIntent);

    }

    public void cancelNotification(Context context, int id) {
        this.context = context;
        Log.d(NOTIF_TAG, "Canceling notification: " + id);

        //Deleting notification JSON if it exists
        String path = context.getFilesDir()+"/notification_" + id+".json";
        File file = new File(path);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("file Deleted :" + path);
            } else {
                System.out.println("file not Deleted :" + path);
            }
        }


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
