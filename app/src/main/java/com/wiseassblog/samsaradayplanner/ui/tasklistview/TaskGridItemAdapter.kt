package com.wiseassblog.samsaradayplanner.ui.tasklistview

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.wiseassblog.samsaradayplanner.common.getColorResId
import com.wiseassblog.samsaradayplanner.common.getResIdFromEnum
import com.wiseassblog.samsaradayplanner.domain.Task

internal class TaskGridItemAdapter(private val taskItems: Array<Task>) :
    BaseAdapter() {

    override fun getCount(): Int {
        return taskItems.size
    }

    override fun getItem(i: Int): Any {
        return taskItems[i]
    }

    override fun getItemId(i: Int): Long {
        return taskItems[i].taskId.toLong()
    }


    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        var gridItem: TaskGridItem? = view as TaskGridItem?

        if (gridItem == null) gridItem = TaskGridItem(parent.context)

        gridItem = TaskGridItem(parent.context)

        gridItem.setListItemBackground(
            getColorResId(
                parent.context,
                taskItems[position].taskColor
            )
        )
        gridItem.setListItemIcon(
            getResIdFromEnum(
                parent.context,
                taskItems[position].taskIcon
            )
        )
        gridItem.setListItemText(taskItems[position].taskName)
        return gridItem
    }
}