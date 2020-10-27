package com.wiseassblog.samsaradayplanner.ui.dayview.buildlogic;

import com.wiseassblog.samsaradayplanner.StorageServiceLocator;
import com.wiseassblog.samsaradayplanner.ui.dayview.DayView;
import com.wiseassblog.samsaradayplanner.ui.dayview.DayViewLogic;
import com.wiseassblog.samsaradayplanner.ui.dayview.DayViewModel;
import com.wiseassblog.samsaradayplanner.ui.dayview.IDayViewContract;

public class DayViewInjector {
    public static void build(
            DayView view,
            StorageServiceLocator locator
    ) {
        IDayViewContract.ViewModel vm = new DayViewModel();

        view.setLogic(
                new DayViewLogic(
                        view,
                        vm,
                        locator.getDayStorageImpl(),
                        locator.getTaskStorageImpl()
                )
        );
    }
}
