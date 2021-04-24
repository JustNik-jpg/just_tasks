package com.justnik.justtasks.taskdb;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Task.class}, version = 1)
@TypeConverters({DateConverter.class})
public abstract class TaskDB extends RoomDatabase {

    public abstract TaskDAO taskDAO();

    private static TaskDB INSTANCE;

    public static TaskDB getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TaskDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), TaskDB.class, "task_db").build();
                }
            }
        }

        return INSTANCE;
    }


}
