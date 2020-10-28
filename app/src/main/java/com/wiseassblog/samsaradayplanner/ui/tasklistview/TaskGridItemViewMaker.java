package com.wiseassblog.samsaradayplanner.ui.tasklistview;

import android.content.Context;

import com.wiseassblog.samsaradayplanner.domain.Task;
import com.wiseassblog.samsaradayplanner.domain.Tasks;

import java.util.ArrayList;
import java.util.List;

import static com.wiseassblog.samsaradayplanner.common.ResourceUtilsKt.getColorResId;
import static com.wiseassblog.samsaradayplanner.common.ResourceUtilsKt.getResIdFromEnum;

public class TaskGridItemViewMaker {
    public static List<TaskGridItemView> getTaskGridItems(Context context, Tasks tasks) {
        List<TaskGridItemView> taskItems = new ArrayList<>();
        for (Task t : tasks.get()) {
            TaskGridItemView newItem = new TaskGridItemView(context);
            newItem.setListItemBackground(
                    getColorResId(
                            context,
                            t.getTaskColor()
                    )
            );

            newItem.setListItemIcon(
                    getResIdFromEnum(context, t.getTaskIcon())
            );

            newItem.setListItemText(t.getTaskName());

            taskItems.add(
                    newItem
            );
        }

        return taskItems;
    }
}
