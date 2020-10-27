package com.wiseassblog.samsaradayplanner.ui.tasklistview.buildlogic;

import com.wiseassblog.samsaradayplanner.StorageServiceLocator;
import com.wiseassblog.samsaradayplanner.ui.tasklistview.ITaskListViewContract;
import com.wiseassblog.samsaradayplanner.ui.tasklistview.TaskListView;
import com.wiseassblog.samsaradayplanner.ui.tasklistview.TaskListViewLogic;
import com.wiseassblog.samsaradayplanner.ui.tasklistview.TaskListViewModel;

public class TaskListViewInjector {
    public static void build(
            TaskListView view,
            StorageServiceLocator locator
    ) {

        ITaskListViewContract.ViewModel vm = new TaskListViewModel();

        view.setLogic(
                new TaskListViewLogic(
                        view,
                        vm,
                        locator.getTaskStorageImpl()
                )
        );
    }
}
