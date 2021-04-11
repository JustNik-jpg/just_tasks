package com.justnik.justtasks.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.justnik.justtasks.R;
import com.justnik.justtasks.TaskViewModel;
import com.justnik.justtasks.notifications.NotificationScheduler;
import com.justnik.justtasks.taskdb.Task;
import com.justnik.justtasks.view.datepicker.DateTimePicker;

import java.util.Calendar;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class TaskAddActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etTaskTitle;
    private EditText etTaskBody;
    private Calendar calendar;

    private final String TAG_ADD = "Notification";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_add_activity);
        etTaskTitle = findViewById(R.id.etTaskAddTitle);
        etTaskBody = findViewById(R.id.etTaskAddBody);
        FloatingActionButton btTaskAdd = findViewById(R.id.btConfirmTaskAdd);
        btTaskAdd.setOnClickListener(this);
    }


    @SuppressLint("CheckResult")
    @Override
    public void onClick(View v) {

        Task task = new Task();
        task.setTaskName(etTaskTitle.getText().toString());
        task.setTaskText(etTaskBody.getText().toString());
        if (!task.getTaskName().isEmpty() || !task.getTaskText().isEmpty()) {
            TaskViewModel viewModel = new ViewModelProvider.AndroidViewModelFactory(this.getApplication()).create(TaskViewModel.class);

            if (calendar != null) {
                task.setNotificationDate(calendar);


            }
            Observable<List<Long>> o = viewModel.insertAll(task);
            o.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(longs -> {
                if (calendar!=null){
                    Log.d(TAG_ADD, "Added task"+longs.get(0)+"------"+task.toString());
                    NotificationScheduler notificationScheduler = new NotificationScheduler();
                    notificationScheduler.scheduleNotification(getApplicationContext(),calendar.getTimeInMillis(),task.getTaskName(), Math.toIntExact(longs.get(0)));
                }
            });


        }




        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_task_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.miAddNotification) {
            DateTimePicker picker = new DateTimePicker(c -> calendar = c);
            picker.showDialog(this, System.currentTimeMillis());
        }

        return true;

    }

}