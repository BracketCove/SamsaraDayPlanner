package com.wiseassblog.samsaradayplanner.ui.dayview

import com.wiseassblog.samsaradayplanner.domain.Day
import com.wiseassblog.samsaradayplanner.domain.Tasks

interface IDayViewContract {
    interface View {
        fun setData(day: Day, tasks: Tasks)
        fun navigateToHourView(hourInteger: Int)
        fun navigateToTasksView()
        fun showMessage(message: String)
        fun restartFeature()
    }

    interface ViewModel {
        var day: Day?
        var tasks: Tasks?
    }
}