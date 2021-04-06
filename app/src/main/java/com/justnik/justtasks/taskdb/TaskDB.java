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

    private static RoomDatabase.Callback sRoomCallback = new RoomDatabase.Callback(){

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            new PopulateTask(INSTANCE.taskDAO()).execute();
        }


    };
    private static class PopulateTask extends AsyncTask<Void,Void,Void>{

        TaskDAO taskDAO;

        PopulateTask(TaskDAO taskDAO){
            this.taskDAO = taskDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            taskDAO.clearDB();
            taskDAO.insertAll(new Task("Ass","fuck ass"),new Task("Suck","some dicks"));

            return null;
        }
    }

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
