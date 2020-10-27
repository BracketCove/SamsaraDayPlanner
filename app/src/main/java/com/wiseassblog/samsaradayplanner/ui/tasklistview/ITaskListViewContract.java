package com.wiseassblog.samsaradayplanner.ui.tasklistview;

import com.wiseassblog.samsaradayplanner.domain.Tasks;

public interface ITaskListViewContract {
    interface View {
        void setTasks(Tasks tasks);
        void showMessage(String message);
        void navigateToDayView();
        void navigateToTaskView(int taskId);
    }

    interface ViewModel {
        void setTasks(Tasks tasks);
        Tasks getTasks();
    }
}
