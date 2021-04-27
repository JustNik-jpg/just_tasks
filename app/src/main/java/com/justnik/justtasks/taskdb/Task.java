package com.justnik.justtasks.taskdb;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Calendar;

@Entity
public class Task {


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "task_id")
    private int taskId;

    @ColumnInfo(name = "task_name")
    private String taskName;
    @ColumnInfo(name = "task_text")
    private String taskText;
    @ColumnInfo(name = "notification_date")
    private Calendar notificationDate;
    @ColumnInfo(name = "done")
    private boolean isDone;

    public Task() {

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

    public Calendar getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(Calendar notificationDate) {
        this.notificationDate = notificationDate;
    }

    @NonNull
    @Override
    public String toString() {
        return "Task: " + taskName + ", body: " + (taskText.length() > 10 ? taskText.substring(0, 9) : taskText)
                + ", ID: " + taskId + (notificationDate == null ? "" : ", Notification date: "
                + String.format("Date: %s %s, Time: %s:%s", notificationDate.get(Calendar.MONTH),
                notificationDate.get(Calendar.DAY_OF_MONTH),notificationDate.get(Calendar.HOUR_OF_DAY),
                notificationDate.get(Calendar.MINUTE)));
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Task) {
            Task temp = (Task) obj;
            return (this.taskId == temp.taskId)&&(this.taskName==temp.taskName)&&(this.taskText==temp.taskText);
        }
        return false;
    }
}
