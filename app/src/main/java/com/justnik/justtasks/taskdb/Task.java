package com.justnik.justtasks.taskdb;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Calendar;
import java.util.Date;

@Entity
public class Task {


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "task_id")
    private int taskId;

    @ColumnInfo(name = "task_name")
    private String taskName;
    @ColumnInfo(name = "task_text")
    private String taskText;
    //@ColumnInfo(name = "creation_date")
    //public Calendar creationDate;
    @ColumnInfo(name = "done")
    private boolean isDone;

    public Task() {

    }

    public Task(String title, String body) {
        this.taskName = title;
        this.taskText = body;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskText() {
        return taskText;
    }

    public void setTaskText(String taskText) {
        this.taskText = taskText;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    @NonNull
    @Override
    public String toString() {
        return "Task: " + taskName + ", body: no" +  ", ID: " + taskId;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Task) {
            Task temp = (Task)obj;
            return this.taskId == temp.taskId;
        }
        return false;
    }
}
