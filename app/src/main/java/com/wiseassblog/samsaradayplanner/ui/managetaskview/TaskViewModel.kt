package com.wiseassblog.samsaradayplanner.ui.managetaskview

import com.wiseassblog.samsaradayplanner.domain.Task

/**
 * A data store (cache) for front end session data
 */
class TaskViewModel : ITaskViewContract.ViewModel {
    override lateinit var task: Task
}