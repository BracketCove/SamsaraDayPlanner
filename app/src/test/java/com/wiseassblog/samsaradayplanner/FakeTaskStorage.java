package com.wiseassblog.samsaradayplanner;

import com.wiseassblog.samsaradayplanner.common.Continuation;
import com.wiseassblog.samsaradayplanner.domain.ITaskStorage;
import com.wiseassblog.samsaradayplanner.domain.Task;
import com.wiseassblog.samsaradayplanner.domain.Tasks;

import java.io.IOException;

class FakeTaskStorage implements ITaskStorage {

    private Boolean willFail = false;

    public void setWillFail(Boolean willFail) {
        this.willFail = willFail;
    }

    @Override
    public void getTasks(Continuation<Tasks> continuation) {
        if (willFail) continuation.onException(new IOException());
        else continuation.onSuccess(TestData.getTestTasks());
    }

    @Override
    public void getTask(int taskId, Continuation<Task> continuation) {
        if (willFail) continuation.onException(new IOException());
        else continuation.onSuccess(TestData.getTestTask());
    }

    @Override
    public void updateTask(Task task, Continuation<Void> continuation) {
        if (willFail) continuation.onException(new IOException());
        else continuation.onSuccess(null);
    }
}