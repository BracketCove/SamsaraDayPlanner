package com.wiseassblog.samsaradayplanner.ui.dayview.buildlogic

import android.app.Activity
import com.wiseassblog.samsaradayplanner.StorageServiceLocator
import com.wiseassblog.samsaradayplanner.ui.dayview.*

internal fun DayActivity.buildComponents(
    view: DayView,
    locator: StorageServiceLocator
) {
    val vm: IDayViewContract.ViewModel = DayViewModel()
    view.setLogic(
        DayViewLogic(
            view,
            vm,
            locator.dayStorageImpl,
            locator.taskStorageImpl
        )
    )
}