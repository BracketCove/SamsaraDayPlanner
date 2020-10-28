package com.wiseassblog.samsaradayplanner.ui.tasklistview

import com.wiseassblog.samsaradayplanner.common.BaseViewLogic
import com.wiseassblog.samsaradayplanner.domain.ITaskStorage
import com.wiseassblog.samsaradayplanner.domain.constants.Messages.GENERIC_ERROR_MESSAGE

class TaskListViewLogic(
    private val view: ITaskListViewContract.View,
    private val vm: ITaskListViewContract.ViewModel,
    private val storage: ITaskStorage
) : BaseViewLogic<TasksListViewEvent>() {
    override fun onViewEvent(event: TasksListViewEvent) {
        when (event) {
            is TasksListViewEvent.OnStart -> onStart()
            is TasksListViewEvent.OnListItemSelected -> onItemSelected(event.position)
            is TasksListViewEvent.OnBackPressed -> onBackPressed()
        }
    }

    private fun onBackPressed() {
        view.navigateToDayView()
    }

    private fun onItemSelected(position: Int) {
        val id = vm.tasks.get()[position].taskId
        view.navigateToTaskView(id)
    }

    private fun onStart() {
        storage.getTasks(
            { tasks ->
                vm.tasks = tasks
                view.setTasks(tasks)
            },
            {
                view.showMessage(GENERIC_ERROR_MESSAGE)
                view.navigateToDayView()
            }
        )
    }
}