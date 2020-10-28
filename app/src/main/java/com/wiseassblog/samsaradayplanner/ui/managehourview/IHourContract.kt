package com.wiseassblog.samsaradayplanner.ui.managehourview

import com.wiseassblog.samsaradayplanner.domain.Hour
import com.wiseassblog.samsaradayplanner.domain.Tasks
import com.wiseassblog.samsaradayplanner.domain.constants.QUARTER

interface IHourContract {
    interface View {
        fun setQuarterHourText(quarter: QUARTER, hour: String)
        fun setQuarterHourAdapterData(
            quarter: QUARTER,
            taskNames: Array<String>,
            startingPosition: Int
        )

        fun setQuarterHourActive(quarter: QUARTER, isActive: Boolean)
        fun setListeners()
        fun navigateToDayView()
        fun showMessage(message: String)
        fun setQuarterHour(q: QUARTER)
    }

    interface ViewModel {
        var hour: Hour
        var tasks: Tasks
    }
}