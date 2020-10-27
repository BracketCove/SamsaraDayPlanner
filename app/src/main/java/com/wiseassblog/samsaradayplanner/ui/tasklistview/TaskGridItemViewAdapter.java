package com.wiseassblog.samsaradayplanner.ui.tasklistview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import androidx.annotation.NonNull;

import com.wiseassblog.samsaradayplanner.common.ColorUtility;
import com.wiseassblog.samsaradayplanner.common.IconUtility;
import com.wiseassblog.samsaradayplanner.domain.Task;

class TaskGridItemViewAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {

    private final Task[] taskItems;
    private TaskGridAdapterCallback callback;

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        if (callback != null) callback.onItemClick(taskItems[position].getTaskId());
    }


    interface TaskGridAdapterCallback {
        public void onItemClick(int taskId);
    }

    public void setCallback(TaskGridAdapterCallback callback) {
        this.callback = callback;
    }

    public TaskGridItemViewAdapter(@NonNull Context context, Task[] taskItems) {
        this.taskItems = taskItems;
    }

    @Override
    public int getCount() {
        return taskItems.length;
    }

    @Override
    public Object getItem(int i) {
        return taskItems[i];
    }

    @Override
    public long getItemId(int i) {
        return taskItems[i].getTaskId();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        TaskGridItemView gridItem = (TaskGridItemView) view;
        if (gridItem == null) {
            gridItem = new TaskGridItemView(parent.getContext());
        }

        gridItem.setListItemBackground(
                ColorUtility.getColorResId(
                        parent.getContext(),
                        taskItems[position].getTaskColor()
                )
        );

        gridItem.setListItemIcon(
                IconUtility.getResIdFromEnum(
                        parent.getContext(),
                        taskItems[position].getTaskIcon()
                )
        );

        gridItem.setListItemText(taskItems[position].getTaskName());

        return gridItem;
    }
}
