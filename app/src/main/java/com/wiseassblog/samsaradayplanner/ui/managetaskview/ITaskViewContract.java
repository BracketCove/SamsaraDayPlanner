package com.wiseassblog.samsaradayplanner.ui.managetaskview;

import com.wiseassblog.samsaradayplanner.domain.Task;
import com.wiseassblog.samsaradayplanner.domain.constants.COLOR;
import com.wiseassblog.samsaradayplanner.domain.constants.ICON;

public interface ITaskViewContract {
    interface View {
        void showColorPickerSheet();
        void hideColorPickerSheet();
        void setName(String name);
        String getName();
        void setIconSelection(ICON icon);
        void setSelectedSpinnerItem(int position);
        void setButtonColor(COLOR c);
        void goToTaskListActivity();
        void showMessage(String message);
    }

    interface ViewModel {
        void setTask(Task task);
        Task getTask();
    }
}
