package com.wiseassblog.samsaradayplanner.ui.managehourview

import com.wiseassblog.samsaradayplanner.domain.Hour
import com.wiseassblog.samsaradayplanner.domain.Tasks

class HourViewModel : IHourContract.ViewModel {
    override lateinit var hour: Hour
    override lateinit var tasks: Tasks

}