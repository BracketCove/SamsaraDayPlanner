package com.wiseassblog.samsaradayplanner.ui.dayview

/**
 * It models interactions between the View and the Logic class
 */
class DayViewEvent(val event: Event, val value: Any) {

    enum class Event {
        ON_START, ON_HOUR_SELECTED, ON_MANAGE_TASKS_SELECTED
    }
}