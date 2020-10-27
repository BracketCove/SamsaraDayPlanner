package com.wiseassblog.samsaradayplanner.ui.managehourview;

import com.wiseassblog.samsaradayplanner.domain.Hour;
import com.wiseassblog.samsaradayplanner.domain.Tasks;
import com.wiseassblog.samsaradayplanner.domain.constants.QUARTER;

public interface IHourContract {
    interface View {
       void setQuarterHourText(QUARTER quarter, String hour);
       void setQuarterHourAdapterData(QUARTER quarter, String[] taskNames, int startingPosition);
       void setQuarterHourActive(QUARTER quarter, boolean isActive);
       void setListeners();
       void navigateToDayView();
       void showMessage(String message);
       void setQuarterHour(QUARTER q);
    }

    interface ViewModel {
        void setHour(Hour hour);
        Hour getHour();
        void setTaskList(Tasks tasks);
        Tasks getTasks();
    }
}
