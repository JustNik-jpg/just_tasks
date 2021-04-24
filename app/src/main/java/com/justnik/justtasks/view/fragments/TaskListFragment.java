package com.justnik.justtasks.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.justnik.justtasks.R;
import com.justnik.justtasks.TaskViewModel;
import com.justnik.justtasks.view.adapter.TaskAdapter;

public class TaskListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);
        TaskViewModel viewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(TaskViewModel.class);
        RecyclerView rv = view.findViewById(R.id.rvTasks);
        final TaskAdapter taskAdapter = new TaskAdapter(getContext(), viewModel, this::onTaskClick);
        rv.setAdapter(taskAdapter);
        rv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        view.findViewById(R.id.btTaskAdd).setOnClickListener(this::onClick);

        viewModel.getTaskList().observe(getViewLifecycleOwner(), tasks -> {
            taskAdapter.submitData(tasks);
        });


        return view;
    }

    public void onClick(View v) {
        Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.openAddForm);
    }

    public void onTaskClick(int id) {

        TaskListFragmentDirections.OpenDetails action = TaskListFragmentDirections.openDetails(id);
        Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(action);
    }
}