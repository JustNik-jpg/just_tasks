package com.justnik.justtasks.taskdb;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDAO {

    @Insert
    List<Long> insertAll(Task... tasks);

    @Delete
    void delete(Task... task);

    @Update
    void updateTask(Task task);

    @Query("SELECT * FROM task")
    LiveData<List<Task>> getAll();

    @Query("SELECT * FROM task WHERE task_id = :id")
    LiveData<Task> getTaskByID(int id);

    @Query("DELETE FROM task WHERE task_id = :id")
    void deleteByID(int id);

    @Query("DELETE FROM task")
    void clearDB();

}
