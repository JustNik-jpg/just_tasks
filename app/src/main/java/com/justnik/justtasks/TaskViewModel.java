package com.justnik.justtasks;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.justnik.justtasks.taskdb.Task;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {

    private TaskRepository taskRepository;
    private LiveData<List<Task>> taskList;
    private MutableLiveData<Integer> selectedCount;


    public MutableLiveData<Integer> getSelectedCount() {
        return selectedCount;
    }

    public void setSelectedCount(int selectedCount) {
        this.selectedCount.setValue(selectedCount);
    }

    public TaskViewModel(@NonNull Application application) {
        super(application);
        taskRepository = new TaskRepository(application);
        taskList = taskRepository.getTasks();
        selectedCount = new MutableLiveData<>();
        selectedCount.setValue(0);
    }

    public LiveData<List<Task>> getTaskList() {
        return taskList;
    }

    public void insertAll(Task tasks){
        taskRepository.insertAll(tasks);
    }

    public LiveData<Task> getTaskByID(int id){
        return taskRepository.getTaskByID(id);
    }

    public void delete(Task... tasks){
        taskRepository.delete(tasks);
    }


}
