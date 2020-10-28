package com.wiseassblog.samsaradayplanner.ui.tasklistview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wiseassblog.samsaradayplanner.R
import com.wiseassblog.samsaradayplanner.SamsaraApp
import com.wiseassblog.samsaradayplanner.ui.tasklistview.buildlogic.buildComponents

class TaskListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks)
        var fragment = this.supportFragmentManager.findFragmentByTag(
            TASK_LIST_VIEW
        ) as TaskListView?
        if (fragment == null) {
            fragment = TaskListView.newInstance()
        }
        this.supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.root_task_list_view,
                fragment!!,
                TASK_LIST_VIEW
            ).commit()
        buildComponents(
            fragment,
            (application as SamsaraApp).serviceLocator
        )
    }

    companion object {
        private const val TASK_LIST_VIEW = "TASK_LIST_VIEW"
    }
}