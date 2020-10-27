package com.wiseassblog.samsaradayplanner.ui.managehourview.buildlogic;

import com.wiseassblog.samsaradayplanner.StorageServiceLocator;
import com.wiseassblog.samsaradayplanner.domain.Hour;
import com.wiseassblog.samsaradayplanner.ui.managehourview.HourView;
import com.wiseassblog.samsaradayplanner.ui.managehourview.HourViewLogic;
import com.wiseassblog.samsaradayplanner.ui.managehourview.HourViewModel;
import com.wiseassblog.samsaradayplanner.ui.managehourview.IHourContract;

public class HourViewInjector {
    public static void build(
            int hourInt,
            HourView view,
            StorageServiceLocator locator
    ) {
        IHourContract.ViewModel vm = new HourViewModel();
        vm.setHour(
                new Hour(
                        null,
                        hourInt
                )
        );

        view.setLogic(
                new HourViewLogic(
                        view,
                        vm,
                        locator.getDayStorageImpl(),
                        locator.getTaskStorageImpl()
                )
        );
    }
}
