package com.justnik.justtasks.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.justnik.justtasks.R;
import com.justnik.justtasks.TaskViewModel;
import com.justnik.justtasks.taskdb.Task;

public class TaskAddActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etTaskTitle;
    private EditText etTaskBody;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_add_activity);
        etTaskTitle = findViewById(R.id.etTaskAddTitle);
        etTaskBody = findViewById(R.id.etTaskAddBody);
        FloatingActionButton btTaskAdd = findViewById(R.id.btConfirmTaskAdd);
        btTaskAdd.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        Task task = new Task();
        task.setTaskName(etTaskTitle.getText().toString());
        task.setTaskText(etTaskBody.getText().toString());

        if (!task.getTaskName().isEmpty() || !task.getTaskText().isEmpty()) {
            TaskViewModel viewModel = new ViewModelProvider.AndroidViewModelFactory(this.getApplication()).create(TaskViewModel.class);
            viewModel.insertAll(task);
        }
        finish();

    }
}