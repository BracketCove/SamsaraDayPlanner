package com.wiseassblog.samsaradayplanner.ui.tasklistview

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.wiseassblog.samsaradayplanner.R
import com.wiseassblog.samsaradayplanner.common.getColorResId
import com.wiseassblog.samsaradayplanner.common.getResIdFromEnum
import com.wiseassblog.samsaradayplanner.domain.Tasks

internal class TaskGridItemView(context: Context) : ConstraintLayout(context) {
    private var background: ImageView? = null
    private var icon: ImageView? = null
    private var taskName: TextView? = null
    private fun build() {
        inflate(context, R.layout.list_item_task, this)
        background = findViewById(R.id.imv_background_color)
        icon = findViewById(R.id.imv_list_item_task)
        taskName = findViewById(R.id.lbl_task_name)
    }

    fun setListItemText(text: String?) {
        taskName!!.text = text
    }

    fun setListItemIcon(resId: Int) {
        icon!!.setImageResource(resId)
    }

    fun setListItemBackground(resId: Int) {
        background!!.setBackgroundColor(resId)
    }

    init {
        build()
    }

    fun getTaskGridItems(context: Context, tasks: Tasks): List<TaskGridItemView> {
        val taskItems: MutableList<TaskGridItemView> = ArrayList()
        for (t in tasks.get()) {
            val newItem = TaskGridItemView(context)
            newItem.setListItemBackground(
                getColorResId(
                    context,
                    t.taskColor
                )
            )
            newItem.setListItemIcon(
                getResIdFromEnum(context, t.taskIcon)
            )
            newItem.setListItemText(t.taskName)
            taskItems.add(
                newItem
            )
        }
        return taskItems
    }
}