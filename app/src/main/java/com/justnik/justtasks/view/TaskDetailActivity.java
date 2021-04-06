package com.justnik.justtasks.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.TextView;

import com.justnik.justtasks.R;
import com.justnik.justtasks.TaskViewModel;
import com.justnik.justtasks.taskdb.Task;

public class TaskDetailActivity extends AppCompatActivity {
    TaskViewModel viewModel;

    TextView detailedTaskTitle;
    TextView detailedTaskBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_detail_activity);

        int id = getIntent().getIntExtra("Task_ID", -1);

        viewModel = new ViewModelProvider.AndroidViewModelFactory(this.getApplication()).create(TaskViewModel.class);
        detailedTaskTitle = findViewById(R.id.detailedTaskTitle);
        detailedTaskBody = findViewById(R.id.detailedTaskBody);

        viewModel.getTaskByID(id).observe(this,task -> {
            detailedTaskTitle.setText(task.getTaskName());
            detailedTaskBody.setText(task.getTaskText());
        });





    }


}