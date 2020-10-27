package com.wiseassblog.samsaradayplanner.ui.tasklistview;

public class TasksListViewEvent {
    private final TasksListViewEvent.Event event;
    private final Object value;

    public TasksListViewEvent(TasksListViewEvent.Event event, Object value){
        this.event = event;
        this.value = value;
    }

    public TasksListViewEvent.Event getEvent() {
        return event;
    }

    public Object getValue() {
        return value;
    }

    public enum Event {
        ON_LIST_ITEM_SELECTED,
        ON_START,
        ON_BACK_PRESSED
    }
}
