package com.justnik.justtasks;

import android.app.Application;
import android.content.Intent;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.justnik.justtasks.taskdb.Task;
import com.justnik.justtasks.taskdb.TaskDAO;
import com.justnik.justtasks.taskdb.TaskDB;

import java.util.List;
import java.util.concurrent.ExecutionException;

import io.reactivex.Observable;
import io.reactivex.Single;

public class TaskRepository {

    private TaskDAO taskDao;
    private LiveData<List<Task>> tasks;

    public TaskRepository(Application application) {
        taskDao = TaskDB.getDatabase(application).taskDAO();
        tasks = taskDao.getAll();
    }

    public LiveData<List<Task>> getTasks() {
        return tasks;
    }

    public Observable<List<Long>> insertAll(Task... tasks) {
        //new InsertAsyncTask(taskDao).execute(tasks);
        return Observable.fromCallable(() -> taskDao.insertAll(tasks));
    }

    public void delete(Task... tasks){
        new DeleteAsyncTask(taskDao).execute(tasks);
    }

    public LiveData<Task> getTaskByID(int id) {
        return taskDao.getTaskByID(id);
    }

    private static class InsertAsyncTask extends AsyncTask<Task, Void, Void> {

        private TaskDAO asyncTaskDAO;

        InsertAsyncTask(TaskDAO dao) {
            asyncTaskDAO = dao;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            asyncTaskDAO.insertAll(tasks);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Task,Void,Void>{
        private TaskDAO asyncTaskDAO;

        public DeleteAsyncTask(TaskDAO asyncTaskDAO) {
            this.asyncTaskDAO = asyncTaskDAO;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            asyncTaskDAO.delete(tasks);
            return null;
        }
    }

}
