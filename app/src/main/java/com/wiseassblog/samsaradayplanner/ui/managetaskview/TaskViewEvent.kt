package com.wiseassblog.samsaradayplanner.ui.managetaskview

import com.wiseassblog.samsaradayplanner.domain.constants.COLOR
import com.wiseassblog.samsaradayplanner.domain.constants.ICON

/**
 * It models interactions between the View and the Logic class
 */
sealed class TaskViewEvent {
    object OnColorButtonClick : TaskViewEvent()
    data class OnColorSelected(val color: COLOR) : TaskViewEvent()
    object OnDoneClick : TaskViewEvent()
    data class OnIconSelected(val icon: ICON) : TaskViewEvent()
    object OnStart : TaskViewEvent()
}