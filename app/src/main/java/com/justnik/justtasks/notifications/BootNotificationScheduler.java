package com.justnik.justtasks.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class BootNotificationScheduler extends BroadcastReceiver {
    private static final String BOOT_TAG = "JustBoot";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(BOOT_TAG, "Caught boot intent");
        String[] files = context.fileList();
        NotificationScheduler scheduler = new NotificationScheduler();
        for (String s : files) {
            Log.d(BOOT_TAG, "Found file: " + s);


            try {
                File file = new File(context.getFilesDir() + "/" + s);
                BufferedReader br = new BufferedReader(new FileReader(file));
                String jString = br.readLine();
                JsonObject jsonObject = new JsonParser().parse(jString).getAsJsonObject();
                Log.d(BOOT_TAG, "JSON: " + jsonObject);

                long time = jsonObject.get("Time").getAsLong();
                String title = jsonObject.get("Time").getAsString();
                int id = jsonObject.get("Time").getAsInt();

                scheduler.scheduleNotification(context, time, title, id);
            } catch (FileNotFoundException e) {
                Log.d(BOOT_TAG, "JSON file not found");
                e.printStackTrace();
            } catch (IOException e) {
                Log.d(BOOT_TAG, "Error reading file");
                e.printStackTrace();
            }


        }
    }
}
