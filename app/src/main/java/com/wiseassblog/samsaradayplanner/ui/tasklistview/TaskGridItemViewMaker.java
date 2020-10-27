package com.wiseassblog.samsaradayplanner.ui.tasklistview;

import android.content.Context;

import com.wiseassblog.samsaradayplanner.common.ColorUtility;
import com.wiseassblog.samsaradayplanner.common.IconUtility;
import com.wiseassblog.samsaradayplanner.domain.Task;
import com.wiseassblog.samsaradayplanner.domain.Tasks;

import java.util.ArrayList;
import java.util.List;

public class TaskGridItemViewMaker {
    public static List<TaskGridItemView> getTaskGridItems(Context context, Tasks tasks) {
        List<TaskGridItemView> taskItems = new ArrayList<>();
        for (Task t : tasks.get()) {
            TaskGridItemView newItem = new TaskGridItemView(context);
            newItem.setListItemBackground(
                    ColorUtility.getColorResId(
                            context,
                            t.getTaskColor()
                    )
            );

            newItem.setListItemIcon(
                    IconUtility.getResIdFromEnum(context, t.getTaskIcon())
            );

            newItem.setListItemText(t.getTaskName());

            taskItems.add(
                    newItem
            );
        }

        return taskItems;
    }
}
