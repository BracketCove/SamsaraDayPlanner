package com.wiseassblog.samsaradayplanner.ui.managehourview.buildlogic

import com.wiseassblog.samsaradayplanner.StorageServiceLocator
import com.wiseassblog.samsaradayplanner.domain.Hour
import com.wiseassblog.samsaradayplanner.ui.managehourview.*

internal fun HourActivity.buildComponents(
    hourInt: Int,
    view: HourView,
    locator: StorageServiceLocator
) {
    val vm: IHourContract.ViewModel = HourViewModel()
    vm.hour = Hour(
        emptyArray(),
        hourInt
    )
    view.setLogic(
        HourViewLogic(
            view,
            vm,
            locator.dayStorageImpl,
            locator.taskStorageImpl
        )
    )
}