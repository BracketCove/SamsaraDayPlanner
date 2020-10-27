package com.wiseassblog.samsaradayplanner.ui.managehourview;

import com.wiseassblog.samsaradayplanner.domain.constants.QUARTER;
import com.wiseassblog.samsaradayplanner.ui.managetaskview.TaskViewEvent;

public class HourViewEvent {
    private final HourViewEvent.Event event;
    private final Object value;
    private QUARTER quarter;
    private int position;
    private boolean isActive;

    public QUARTER getQuarter() {
        return quarter;
    }

    public int getPosition() {
        return position;
    }

    public boolean isActive() {
        return isActive;
    }

    public HourViewEvent(HourViewEvent.Event event, Object value){
        this.event = event;
        this.value = value;
    }

    private HourViewEvent(
            HourViewEvent.Event event,
            Object value,
            QUARTER quarter,
            int position){
        this.event = event;
        this.value = value;
        this.quarter = quarter;
        this.position = position;
        this.isActive = false;
    }

    private HourViewEvent(
            HourViewEvent.Event event,
            Object value,
            QUARTER quarter,
            boolean isActive){
        this.event = event;
        this.value = value;
        this.quarter = quarter;
        this.isActive = isActive;
        this.position = 0;
    }

    /**
     * Convenience method for creating events with multiple parameters safely
     * @param quarter
     * @param position
     * @return
     */
    public static HourViewEvent getOnTaskSelectedEvent(
            QUARTER quarter,
            int position
            ){

        return new HourViewEvent(Event.ON_TASK_SELECTED, null, quarter, position);
    }

    /**
     * Convenience method for creating events with multiple parameters safely
     * @param quarter
     * @param isActive
     * @return
     */
    public static HourViewEvent getOnQuarterToggleEvent(
            QUARTER quarter,
            boolean isActive
    ){

        return new HourViewEvent(Event.ON_QUARTER_TOGGLE, null, quarter, isActive);
    }


    public HourViewEvent.Event getEvent() {
        return event;
    }

    public Object getValue() {
        return value;
    }

    public enum Event {
        ON_QUARTER_TOGGLE,
        ON_DONE_CLICK,
        ON_TASK_SELECTED,
        ON_START
    }

}
