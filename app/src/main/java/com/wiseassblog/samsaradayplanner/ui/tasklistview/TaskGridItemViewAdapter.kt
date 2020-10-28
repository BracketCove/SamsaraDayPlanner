package com.wiseassblog.samsaradayplanner.ui.tasklistview

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.BaseAdapter
import com.wiseassblog.samsaradayplanner.common.getColorResId
import com.wiseassblog.samsaradayplanner.common.getResIdFromEnum
import com.wiseassblog.samsaradayplanner.domain.Task

internal class TaskGridItemViewAdapter(context: Context, private val taskItems: Array<Task>) :
    BaseAdapter(), OnItemClickListener {
    private var callback: TaskGridAdapterCallback? = null
    override fun onItemClick(adapterView: AdapterView<*>?, view: View, position: Int, l: Long) {
        if (callback != null) callback!!.onItemClick(taskItems[position].taskId)
    }

    internal interface TaskGridAdapterCallback {
        fun onItemClick(taskId: Int)
    }

    fun setCallback(callback: TaskGridAdapterCallback?) {
        this.callback = callback
    }

    override fun getCount(): Int {
        return taskItems.size
    }

    override fun getItem(i: Int): Any {
        return taskItems[i]
    }

    override fun getItemId(i: Int): Long {
        return taskItems[i].taskId.toLong()
    }

    override fun getView(position: Int, view: View, parent: ViewGroup): View {
        var gridItem = view as TaskGridItemView
        if (gridItem == null) {
            gridItem = TaskGridItemView(parent.context)
        }
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