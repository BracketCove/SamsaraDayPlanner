package com.wiseassblog.samsaradayplanner.ui.dayview;

/**
 * It models interactions between the View and the Logic class
 */
public class DayViewEvent {

    private final Event event;
    private final Object value;

    public DayViewEvent(Event event, Object value){
        this.event = event;
        this.value = value;
    }

    public Event getEvent() {
        return event;
    }

    public Object getValue() {
        return value;
    }

    public enum Event {
        ON_START,
        ON_HOUR_SELECTED,
        ON_MANAGE_TASKS_SELECTED
    }
}
