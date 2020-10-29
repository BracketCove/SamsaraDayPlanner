package com.wiseassblog.samsaradayplanner.ui.dayview

import com.wiseassblog.samsaradayplanner.common.BaseViewLogic
import com.wiseassblog.samsaradayplanner.domain.constants.Messages
import com.wiseassblog.samsaradayplanner.domain.Day
import com.wiseassblog.samsaradayplanner.domain.IDayStorage
import com.wiseassblog.samsaradayplanner.domain.ITaskStorage
import com.wiseassblog.samsaradayplanner.domain.Tasks

class DayViewLogic(
    private val view: IDayViewContract.View,
    private val vm: IDayViewContract.ViewModel,
    private val dayStorage: IDayStorage,
    private val taskStorage: ITaskStorage
) : BaseViewLogic<DayViewEvent>() {
    override fun onViewEvent(event: DayViewEvent) {
        when (event) {
            is DayViewEvent.OnStart -> onStart()
            is DayViewEvent.OnHourSelected -> onHourSelected(event.position)
            is DayViewEvent.OnManageTasksSelected -> onManageTaskSelected()
        }
    }

    private fun onManageTaskSelected() {
        view.navigateToTasksView()
    }

    private fun onHourSelected(hourInteger: Int) {
        view.navigateToHourView(hourInteger)
    }

    private fun onStart() {
        dayStorage.getDay(
            { day ->
                getTasks(day)
            },
            { error ->
                view.showMessage(Messages.GENERIC_ERROR_MESSAGE)
                view.restartFeature()
            }
        )
    }

    private fun getTasks(day: Day) {
        taskStorage.getTasks(
            { tasks ->
                bindData(day, tasks)
            },
            {
                view.showMessage(Messages.GENERIC_ERROR_MESSAGE)
                view.restartFeature()
            }
        )
    }

    private fun bindData(dayResult: Day, taskResult: Tasks) {
        vm.day = dayResult
        vm.tasks = taskResult
        view.setData(dayResult, taskResult)
    }
}