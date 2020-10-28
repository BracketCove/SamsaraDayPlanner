package com.wiseassblog.samsaradayplanner.ui.managetaskview

import com.wiseassblog.samsaradayplanner.common.BaseViewLogic
import com.wiseassblog.samsaradayplanner.domain.ITaskStorage
import com.wiseassblog.samsaradayplanner.domain.Task
import com.wiseassblog.samsaradayplanner.domain.constants.COLOR
import com.wiseassblog.samsaradayplanner.domain.constants.ICON
import com.wiseassblog.samsaradayplanner.domain.constants.Messages.GENERIC_ERROR_MESSAGE

class TaskViewLogic
/**
 * Why skip Use Cases/Interactors/Transaction Scripts and just call the Repository directly?
 * Because this feature is so simple that adding in the domain layer (Use Case...etc.) is
 * not worth the effort.
 *
 * @param view
 * @param vm
 * @param storage
 */(
    private val view: ITaskViewContract.View,
    private val vm: ITaskViewContract.ViewModel,
    private val storage: ITaskStorage
) : BaseViewLogic<TaskViewEvent>() {
    override fun onViewEvent(event: TaskViewEvent) {
        when (event) {
            is TaskViewEvent.OnStart -> onStart()
            is TaskViewEvent.OnColorButtonClick -> view.showColorPickerSheet()
            is TaskViewEvent.OnColorSelected -> onColorSelected(event.color)
            is TaskViewEvent.OnDoneClick -> updateStorage()
            is TaskViewEvent.OnIconSelected -> onIconSelected(event.icon)
        }
    }

    private fun onStart() {
        storage.getTask(
            vm.task.taskId,
            { task ->
                vm.task = task
                view.setButtonColor(task.taskColor)
                view.name = task.taskName
                view.setIconSelection(task.taskIcon)
                view.setSelectedSpinnerItem(task.taskId)
            },
            {
                view.showMessage(GENERIC_ERROR_MESSAGE)
                view.goToTaskListActivity()
            }
        )
    }

    private fun onIconSelected(icon: ICON) {
        val oldTask = vm.task
        val update = Task(
            oldTask.taskId,
            oldTask.taskName,
            icon,
            oldTask.taskColor
        )
        vm.task = update
        view.setIconSelection(update.taskIcon)
    }

    private fun updateStorage() {
        val oldTask = vm.task
        val update = Task(
            oldTask.taskId,
            view.name,
            oldTask.taskIcon,
            oldTask.taskColor
        )
        storage.updateTask(
            update,
            {
                view.goToTaskListActivity()
            },
            {
                view.showMessage(GENERIC_ERROR_MESSAGE)
                view.goToTaskListActivity()
            }

        )
    }

    private fun onColorSelected(color: COLOR) {
        val oldTask = vm.task
        val newTask = Task(
            oldTask.taskId,
            oldTask.taskName,
            oldTask.taskIcon,
            color
        )
        vm.task = newTask
        view.setButtonColor(newTask.taskColor)
        view.hideColorPickerSheet()
    }
}