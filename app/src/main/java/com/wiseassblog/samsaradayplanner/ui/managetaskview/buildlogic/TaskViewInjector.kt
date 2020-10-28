package com.wiseassblog.samsaradayplanner.ui.managetaskview.buildlogic

import com.wiseassblog.samsaradayplanner.StorageServiceLocator
import com.wiseassblog.samsaradayplanner.domain.Task
import com.wiseassblog.samsaradayplanner.domain.constants.COLOR
import com.wiseassblog.samsaradayplanner.domain.constants.ICON
import com.wiseassblog.samsaradayplanner.ui.managetaskview.*

internal fun TaskActivity.buildComponents(
    view: TaskView,
    locator: StorageServiceLocator,
    taskId: Int
) {
    val vm: ITaskViewContract.ViewModel = TaskViewModel()
    vm.task = Task(
        taskId,
        "",
        ICON.BREAK,
        COLOR.DARK_BLUE
    )
    view.setLogic(
        TaskViewLogic(
            (view as ITaskViewContract.View),
            vm,
            locator.taskStorageImpl
        )
    )
}