package com.wiseassblog.samsaradayplanner.ui.tasklistview

import com.wiseassblog.samsaradayplanner.domain.Tasks

class TaskListViewModel : ITaskListViewContract.ViewModel {
    override lateinit var tasks: Tasks
}