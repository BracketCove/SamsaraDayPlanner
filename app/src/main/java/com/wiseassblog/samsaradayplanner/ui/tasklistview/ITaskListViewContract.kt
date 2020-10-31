package com.wiseassblog.samsaradayplanner.ui.tasklistview

import com.wiseassblog.samsaradayplanner.common.Subscriber
import com.wiseassblog.samsaradayplanner.domain.Tasks

interface ITaskListViewContract {
    interface View {
        fun setTasks(tasks: Tasks)
        fun showMessage(message: String)
        fun navigateToDayView()
        fun navigateToTaskView(taskId: Int)
    }

    interface ViewModel {
        var tasks: Tasks
    }
}