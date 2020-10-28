package com.wiseassblog.samsaradayplanner.ui.managehourview

import com.wiseassblog.samsaradayplanner.domain.constants.QUARTER

sealed class HourViewEvent {
    object OnStart : HourViewEvent()
    object OnDoneClick : HourViewEvent()
    data class OnQuarterToggled(val quarter: QUARTER, val active: Boolean) : HourViewEvent()
    data class OnTaskSelected(val quarter: QUARTER, val position: Int) : HourViewEvent()
}