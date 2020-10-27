package com.wiseassblog.samsaradayplanner.domain;

import com.wiseassblog.samsaradayplanner.common.Continuation;

public interface ITaskStorage {
    public void getTasks(Continuation<Tasks> continuation);

    public void getTask(int taskId, Continuation<Task> continuation);

    public void updateTask(Task task, Continuation<Void> continuation);
}
