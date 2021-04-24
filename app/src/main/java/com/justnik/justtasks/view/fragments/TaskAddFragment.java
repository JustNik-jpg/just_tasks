package com.justnik.justtasks.view.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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


public class TaskAddFragment extends Fragment implements View.OnClickListener {

    private EditText etTaskTitle;
    private EditText etTaskBody;
    private Calendar calendar;

    private final String TAG_ADD = "JustAdd";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task_add, container, false);
        etTaskTitle = view.findViewById(R.id.etTaskAddTitle);
        etTaskBody = view.findViewById(R.id.etTaskAddBody);
        FloatingActionButton btTaskAdd = view.findViewById(R.id.btConfirmTaskAdd);
        btTaskAdd.setOnClickListener(this);
        return view;
    }

    @SuppressLint("CheckResult")
    @Override
    public void onClick(View v) {

        Task task = new Task();
        task.setTaskName(etTaskTitle.getText().toString());
        task.setTaskText(etTaskBody.getText().toString());
        if (!task.getTaskName().isEmpty() || !task.getTaskText().isEmpty()) {
            TaskViewModel viewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(TaskViewModel.class);

            if (calendar != null) {
                task.setNotificationDate(calendar);


            }
            Observable<List<Long>> o = viewModel.insertAll(task);
            o.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(longs -> {
                if (calendar != null) {
                    Log.d(TAG_ADD, "Added task" + longs.get(0) + "------" + task.toString());
                    NotificationScheduler notificationScheduler = new NotificationScheduler();
                    notificationScheduler.scheduleNotification(getActivity().getApplicationContext(), calendar.getTimeInMillis(), task.getTaskName(), Math.toIntExact(longs.get(0)));
                }
            });

        }

        Navigation.findNavController(getActivity(), R.id.nav_host_fragment).popBackStack();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.add_task_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.miAddNotification) {
            DateTimePicker picker = new DateTimePicker(c -> calendar = c);
            picker.showDialog(getContext(), System.currentTimeMillis());
        } else if (item.getItemId() == android.R.id.home) {
            Navigation.findNavController(getActivity(), R.id.nav_host_fragment).popBackStack();
        }
        return true;

    }
}