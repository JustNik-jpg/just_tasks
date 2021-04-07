package com.justnik.justtasks.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.justnik.justtasks.R;
import com.justnik.justtasks.TaskViewModel;

public class TaskDetailActivity extends AppCompatActivity {
    TaskViewModel viewModel;

    TextView tvDetailedTaskTitle;
    TextView tvDetailedTaskBody;
    TextView tvDetailedComplete;
    TextView tvDetailedTaskNotifDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_detail_activity);

        int id = getIntent().getIntExtra("Task_ID", -1);

        viewModel = new ViewModelProvider.AndroidViewModelFactory(this.getApplication()).create(TaskViewModel.class);
        tvDetailedTaskTitle = findViewById(R.id.tvDetailedTaskTitle);
        tvDetailedTaskBody = findViewById(R.id.tvDetailedTaskBody);
        tvDetailedComplete = findViewById(R.id.tvDetailedComplete);
        tvDetailedTaskNotifDate = findViewById(R.id.tvDetailedTaskNotifDate);



        viewModel.getTaskByID(id).observe(this,task -> {
            tvDetailedTaskTitle.setText(task.getTaskName());
            tvDetailedTaskBody.setText(task.getTaskText());

            if (task.getNotificationDate()==null){
                tvDetailedComplete.setVisibility(View.GONE);
                tvDetailedTaskNotifDate.setVisibility(View.GONE);
            } else {
                tvDetailedTaskNotifDate.setText(task.getNotificationDate().toString());
            }
        });





    }


}