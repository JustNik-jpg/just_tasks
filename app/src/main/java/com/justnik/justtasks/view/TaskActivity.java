package com.justnik.justtasks.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.justnik.justtasks.R;
import com.justnik.justtasks.TaskViewModel;
import com.justnik.justtasks.view.adapter.TaskAdapter;

public class TaskActivity extends AppCompatActivity implements View.OnClickListener {

    private TaskViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_activity);
        viewModel = new ViewModelProvider.AndroidViewModelFactory(this.getApplication()).create(TaskViewModel.class);
        //TaskDB.getDatabase(this).taskDAO().insertAll(new Task("Anal,sex","fisting"));

        RecyclerView rv = findViewById(R.id.rvTasks);
        final TaskAdapter taskAdapter = new TaskAdapter(this,viewModel);
        rv.setAdapter(taskAdapter);
        rv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));


        viewModel.getTaskList().observe(this, tasks -> {
            taskAdapter.submitData(tasks);
        });

        FloatingActionButton btTaskAdd = findViewById(R.id.btTaskAdd);
        btTaskAdd.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        startActivity(new Intent(this, TaskAddActivity.class));

    }
}