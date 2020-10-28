package com.wiseassblog.samsaradayplanner.ui.managetaskview

import com.wiseassblog.samsaradayplanner.domain.Task
import com.wiseassblog.samsaradayplanner.domain.constants.ICON
import com.wiseassblog.samsaradayplanner.domain.constants.COLOR

interface ITaskViewContract {
    interface View {
        fun showColorPickerSheet()
        fun hideColorPickerSheet()
        var name: String
        fun setIconSelection(icon: ICON)
        fun setSelectedSpinnerItem(position: Int)
        fun setButtonColor(c: COLOR)
        fun goToTaskListActivity()
        fun showMessage(message: String)
    }

    interface ViewModel {
        var task: Task
    }
}