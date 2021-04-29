package com.justnik.justtasks.notifications;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class NotificationJSONHelper {
    private String JSON_TAG = "JustJSON";

    public void deleteNotifJSON(Context context, int id){
        String path = context.getFilesDir()+"/notification_" + id+".json";
        File file = new File(path);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("file Deleted :" + path);
            } else {
                System.out.println("file not Deleted :" + path);
            }
        }
    }

    public void writeNotifJSON(Context context, int id, JsonObject json){
        File file = new File(context.getFilesDir() + "/notification_" + id+".json");
        Log.d(JSON_TAG, "Created file: "+file);
        if (!file.exists()){
            try {
                //PrintWriter bw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
                Log.d(JSON_TAG, "Writing notification json to a file " + json.toString());
                //bw.write(json.toString());
                FileOutputStream fos = new FileOutputStream(file);
                String text = json.toString();
                fos.write(text.getBytes());
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(JSON_TAG, "Something went wrong... Notification won't be written");
            }
        }
    }

    public JsonObject readNotifJSON(Context context, String fileName) throws IOException {
        File file = new File(context.getFilesDir() + "/" + fileName);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String jString = br.readLine();
        br.close();
        JsonObject jsonObject = new JsonParser().parse(jString).getAsJsonObject();
        Log.d(JSON_TAG, "JSON: " + jsonObject);
        return jsonObject;
    }

    public boolean checkForFile(Context context, String fileName){
        File file = new File(context.getFilesDir() + "/" + fileName);
        return file.exists();
    }
}
