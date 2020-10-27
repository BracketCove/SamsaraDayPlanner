package com.wiseassblog.samsaradayplanner.ui.dayview;

import com.wiseassblog.samsaradayplanner.domain.Day;
import com.wiseassblog.samsaradayplanner.domain.Tasks;

public interface IDayViewContract {
    interface View {
        void setData(Day day, Tasks tasks);
        void navigateToHourView(int hourInteger);
        void navigateToTasksView();
        void showMessage(String message);
        void restartFeature();
    }

    interface ViewModel {
        void setDay(Day day);
        Day getDay();
        void setTasks(Tasks tasks);
        Tasks getTasks();
    }

}
