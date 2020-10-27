package com.wiseassblog.samsaradayplanner.ui.managetaskview.buildlogic;

import com.wiseassblog.samsaradayplanner.StorageServiceLocator;
import com.wiseassblog.samsaradayplanner.domain.Task;
import com.wiseassblog.samsaradayplanner.domain.constants.COLOR;
import com.wiseassblog.samsaradayplanner.domain.constants.ICON;
import com.wiseassblog.samsaradayplanner.ui.managetaskview.ITaskViewContract;
import com.wiseassblog.samsaradayplanner.ui.managetaskview.TaskView;
import com.wiseassblog.samsaradayplanner.ui.managetaskview.TaskViewLogic;
import com.wiseassblog.samsaradayplanner.ui.managetaskview.TaskViewModel;

public class TaskViewInjector {
    public static void build(
            TaskView view,
            StorageServiceLocator locator,
            int taskId
    ) {

        ITaskViewContract.ViewModel vm = new TaskViewModel();

        vm.setTask(
                new Task(
                     taskId,
                     "",
                     ICON.BREAK,
                     COLOR.DARK_BLUE
                )
        );

        view.setLogic(
                new TaskViewLogic(
                        (ITaskViewContract.View) view,
                        vm,
                        locator.getTaskStorageImpl()
                )
        );
    }
}
