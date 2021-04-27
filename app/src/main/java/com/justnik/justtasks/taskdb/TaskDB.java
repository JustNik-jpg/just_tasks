package com.justnik.justtasks.taskdb;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Task.class}, version = 1)
@TypeConverters({DateConverter.class})
public abstract class TaskDB extends RoomDatabase {

    public abstract  TaskDAO taskDAO();

    private static volatile  TaskDB INSTANCE;

    public static TaskDB getDatabase(final Context context) {
        TaskDB localInstance = INSTANCE;
        if (localInstance == null) {
            synchronized (TaskDB.class) {
                if (localInstance == null) {
                    INSTANCE = localInstance = Room.databaseBuilder(context.getApplicationContext(), TaskDB.class, "task_db").build();
                }
            }
        }

        return localInstance;
    }


}
