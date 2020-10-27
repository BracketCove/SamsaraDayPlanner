package com.wiseassblog.samsaradayplanner.domain;

import java.io.Serializable;

/**
 * Collections of tasks to make reads/writes easier
 *
 * See JavaDoc comments in "Day.java" for an explanation of why I am using
 * Serializable instead of Parcelable.
 */
public class Tasks implements Serializable {
    private final Task[] tasks;

    public Tasks(Task[] tasks) {
        this.tasks = tasks;
    }

    public Task[] get() {
        return tasks;
    }

    public Task getTaskById(int taskId) {
        for (Task task : tasks) {
                if (task.getTaskId() == taskId) return task;
        }

        return null;
    }
}
