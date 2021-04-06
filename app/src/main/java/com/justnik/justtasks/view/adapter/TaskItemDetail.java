package com.justnik.justtasks.view.adapter;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemDetailsLookup;

import com.justnik.justtasks.taskdb.Task;

public class TaskItemDetail extends ItemDetailsLookup.ItemDetails {

    private final int adapterPosition;
    private final Task selectionKey;

    public TaskItemDetail(int adapterPosition, Task selectionKey) {
        this.adapterPosition = adapterPosition;
        this.selectionKey = selectionKey;
    }

    @Override
    public int getPosition() {
        return adapterPosition;
    }

    @Nullable
    @Override
    public Object getSelectionKey() {
        return selectionKey;
    }
}
