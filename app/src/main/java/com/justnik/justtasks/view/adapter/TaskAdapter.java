package com.justnik.justtasks.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.LifecycleOwner;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.justnik.justtasks.R;
import com.justnik.justtasks.TaskViewModel;
import com.justnik.justtasks.notifications.NotificationScheduler;
import com.justnik.justtasks.taskdb.Task;
import com.justnik.justtasks.view.TaskDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private final LayoutInflater taskInflater;
    private List<Task> taskList;
    private final Context context;
    private List<Integer> selectedItemsPosition;
    private final TaskViewModel viewModel;
    private ActionMode.Callback selectionCallback;
    private TaskClickListener taskClickListener;

    private final String TAG_VIEW = "VIEW";
    private final String TAG_SELECT = "SELECT";

    public TaskAdapter(Context context, TaskViewModel viewModel, TaskClickListener taskClickListener) {
        this.taskClickListener=taskClickListener;
        this.context = context;
        this.viewModel = viewModel;
        taskInflater = LayoutInflater.from(context);
        selectedItemsPosition = viewModel.getSelectedItemsPosition();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = taskInflater.inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        //Null check
        if (taskList == null) {
            holder.taskTitle.setText("No data...");
        } else {
            //Binding data to a view

            Task task = taskList.get(position);
            Log.d(TAG_VIEW, task.toString());
            //If there is no title - hide it
            if (task.getTaskName().isEmpty()) {
                holder.taskTitle.setVisibility(View.GONE);
                Log.d(TAG_VIEW, "No title");
            } else {
                holder.taskTitle.setVisibility(View.VISIBLE);
                holder.taskTitle.setText(task.getTaskName());
                Log.d("TITLE", "Has title");
            }
            holder.taskBody.setText(task.getTaskText());

            //Click listener for a view
            holder.cardView.setOnClickListener(v -> {

                if (viewModel.isEnable()) {
                    toggleSelection(holder);
                } else {
                    taskClickListener.onTaskClick(task.getTaskId());
                }


            });

            //Long click listener for a view
            //Implements multiple selection
            holder.cardView.setOnLongClickListener(v -> {
                if (!viewModel.isEnable()) {
                    Log.d(TAG_SELECT, "LongClick pressed");
                    selectionCallback = new SelectionCallback();
                    holder.cardView.startActionMode(selectionCallback);
                }

                if (selectionCallback != null) {
                    toggleSelection(holder);
                }

                return true;
            });


            //Selection handling
            if (selectedItemsPosition.contains(position)){
                holder.ivChecked.setVisibility(View.VISIBLE);
                holder.cardView.setBackgroundColor(context.getResources().getColor(R.color.main_purple_light));
            } else {
                holder.ivChecked.setVisibility(View.GONE);
                holder.cardView.setBackgroundColor(context.getResources().getColor(R.color.white, null));
            }


        }

    }

    //Item selection toggle handling
    private void toggleSelection(TaskViewHolder holder){
        Integer position = holder.getAdapterPosition();
        if (selectedItemsPosition.contains(position)){
            selectedItemsPosition.remove(position);
        } else {
            selectedItemsPosition.add(position);
        }
        updateSelectedSize();
        notifyItemChanged(position);
    }

    @Override
    public int getItemCount() {
        return taskList == null ? 0 : taskList.size();
    }

    public void submitData(List<Task> list) {
        taskList = list;
        notifyDataSetChanged();
    }

    private class SelectionCallback implements ActionMode.Callback {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.main_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            viewModel.setEnable(true);
            viewModel.getSelectedCount().observe((LifecycleOwner) context, integer -> mode.setTitle(integer+" Selected"));

            return true;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.miDelete:
                    ArrayList<Task> temp = new ArrayList<>();

                    for (int i: selectedItemsPosition) {
                        Task t = taskList.get(i);

                        temp.add(t);
                        if(t.getNotificationDate()!=null){
                            Log.d("Notifications","Deleting task"+t.toString());
                            NotificationScheduler notificationScheduler = new NotificationScheduler();
                            notificationScheduler.cancelNotification(context.getApplicationContext(),t.getTaskId());


                        }
                    }
                    viewModel.delete(temp.toArray(new Task[0]));
                    selectedItemsPosition.clear();
                    mode.finish();
                    notifyDataSetChanged();
                    break;
                case R.id.miSelectAll:
                    if (viewModel.isSelectedAll()){
                        selectedItemsPosition.clear();
                        notifyDataSetChanged();
                        viewModel.setSelectedAll(false);
                    } else {
                        viewModel.setSelectedAll(true);
                        for (int i = 0; i < taskList.size(); i++) {
                            if (selectedItemsPosition.contains(i)){
                                continue;
                            } else {
                                selectedItemsPosition.add(i);
                                notifyItemChanged(i);
                            }
                        }
                    }
                    updateSelectedSize();
                    break;
            }
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            viewModel.setEnable(false);
            selectedItemsPosition.clear();
            selectionCallback = null;
            notifyDataSetChanged();
        }
    }

    private void updateSelectedSize(){
        viewModel.setSelectedCount(selectedItemsPosition.size());
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {
        private TextView taskTitle;
        private TextView taskBody;
        private CardView cardView;
        public ImageView ivChecked;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            taskTitle = itemView.findViewById(R.id.tvItemTaskTitle);
            taskBody = itemView.findViewById(R.id.tvItemTaskBody);
            cardView = itemView.findViewById(R.id.card);
            ivChecked = itemView.findViewById(R.id.ivChecked);
        }

    }

}
