package com.justnik.justtasks.view.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.justnik.justtasks.TaskRepository;
import com.justnik.justtasks.taskdb.Task;
import com.justnik.justtasks.taskdb.TaskDB;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class TaskViewModel extends ViewModel {

    private TaskRepository taskRepository;
    private LiveData<List<Task>> taskList;
    private MutableLiveData<Integer> selectedCount;
    private boolean isEnable;
    private boolean isSelectedAll;
    private ArrayList<Integer> selectedItemsPosition;



    public MutableLiveData<Integer> getSelectedCount() {
        return selectedCount;
    }

    public void setSelectedCount(int selectedCount) {
        this.selectedCount.setValue(selectedCount);
    }

    @Inject
    public TaskViewModel(TaskRepository repository) {
        this.taskRepository = repository;
        taskList = taskRepository.getTasks();
        selectedCount = new MutableLiveData<>();
        selectedCount.setValue(0);
        selectedItemsPosition = new ArrayList<>();
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    public boolean isSelectedAll() {
        return isSelectedAll;
    }

    public void setSelectedAll(boolean selectedAll) {
        isSelectedAll = selectedAll;
    }

    public LiveData<List<Task>> getTaskList() {
        return taskList;
    }

    public Observable<List<Long>> insertAll(Task tasks){
        return taskRepository.insertAll(tasks);
    }

    public LiveData<Task> getTaskByID(int id){
        return taskRepository.getTaskByID(id);
    }

    public void delete(Task... tasks){
        taskRepository.delete(tasks);
    }

    public ArrayList<Integer> getSelectedItemsPosition() {
        return selectedItemsPosition;
    }
}
