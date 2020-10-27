package com.wiseassblog.samsaradayplanner.ui.tasklistview;

import com.wiseassblog.samsaradayplanner.domain.Tasks;

public class TaskListViewModel implements ITaskListViewContract.ViewModel {

    private Tasks tasks;

    @Override
    public void setTasks(Tasks tasks) {
        this.tasks = tasks;
    }

    @Override
    public Tasks getTasks() {
        return this.tasks;
    }
}
