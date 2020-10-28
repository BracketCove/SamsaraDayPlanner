package com.wiseassblog.samsaradayplanner.ui.tasklistview.buildlogic

import com.wiseassblog.samsaradayplanner.StorageServiceLocator
import com.wiseassblog.samsaradayplanner.ui.tasklistview.*

internal fun TaskListActivity.buildComponents(
    view: TaskListView,
    locator: StorageServiceLocator
) {
    val vm: ITaskListViewContract.ViewModel = TaskListViewModel()
    view.setLogic(
        TaskListViewLogic(
            view,
            vm,
            locator.taskStorageImpl
        )
    )
}