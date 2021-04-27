package com.justnik.justtasks.view.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.justnik.justtasks.R;
import com.justnik.justtasks.dagger.TaskApplication;
import com.justnik.justtasks.view.viewmodel.TaskViewModel;
import com.justnik.justtasks.view.viewmodel.TaskViewModelFactory;

import java.util.Calendar;

import javax.inject.Inject;

public class TaskDetailFragment extends Fragment {

    @Inject
    TaskViewModelFactory factory;
    private TaskViewModel viewModel;

    private TextView tvDetailedTaskTitle;
    private TextView tvDetailedTaskBody;
    private TextView tvDetailedComplete;
    private TextView tvDetailedTaskNotifDate;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ((TaskApplication)getActivity().getApplication()).getComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task_detail, container, false);
        int id = TaskDetailFragmentArgs.fromBundle(getArguments()).getTaskID();

        viewModel = new ViewModelProvider(getViewModelStore(),factory).get(TaskViewModel.class);
        tvDetailedTaskTitle = view.findViewById(R.id.tvDetailedTaskTitle);
        tvDetailedTaskBody = view.findViewById(R.id.tvDetailedTaskBody);
        tvDetailedComplete = view.findViewById(R.id.tvDetailedComplete);
        tvDetailedTaskNotifDate = view.findViewById(R.id.tvDetailedTaskNotifDate);


        viewModel.getTaskByID(id).observe(getViewLifecycleOwner(), task -> {
            tvDetailedTaskTitle.setText(task.getTaskName());
            tvDetailedTaskBody.setText(task.getTaskText());

            if (task.getNotificationDate() == null) {
                tvDetailedComplete.setVisibility(View.GONE);
                tvDetailedTaskNotifDate.setVisibility(View.GONE);
            } else {
                Calendar date = task.getNotificationDate();
                tvDetailedTaskNotifDate.setText(String.format("%s/%s/%s %s:%s",
                        date.get(Calendar.DAY_OF_MONTH),
                        date.get(Calendar.MONTH) + 1,
                        date.get(Calendar.YEAR),
                        date.get(Calendar.HOUR_OF_DAY),
                        date.get(Calendar.MINUTE)));
            }
        });
        return view;
    }
}