package com.justnik.justtasks.view.adapter;

import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.widget.RecyclerView;

public class TaskLookup extends ItemDetailsLookup {
    private final RecyclerView rv;

    public TaskLookup(RecyclerView rv) {
        this.rv = rv;
    }

    @Nullable
    @Override
    public ItemDetails getItemDetails(@NonNull MotionEvent e) {
        View view = rv.findChildViewUnder(e.getX(), e.getY());
        if (view != null) {
            RecyclerView.ViewHolder viewHolder = rv.getChildViewHolder(view);
            if (viewHolder instanceof TaskAdapter.TaskViewHolder) {
                return ((TaskAdapter.TaskViewHolder) viewHolder).getItemDetails();
            }
        }

        return null;
    }
}
