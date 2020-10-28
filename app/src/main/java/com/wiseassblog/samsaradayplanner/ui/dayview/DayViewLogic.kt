package com.wiseassblog.samsaradayplanner.ui.dayview

import com.wiseassblog.samsaradayplanner.common.BaseViewLogic
import com.wiseassblog.samsaradayplanner.domain.constants.Messages
import com.wiseassblog.samsaradayplanner.domain.Day
import com.wiseassblog.samsaradayplanner.domain.IDayStorage
import com.wiseassblog.samsaradayplanner.domain.ITaskStorage
import com.wiseassblog.samsaradayplanner.domain.Tasks

//Decision maker (logic) class for the Front End
class DayViewLogic(
    private val view: IDayViewContract.View,
    private val vm: IDayViewContract.ViewModel, //backend IO devices
    //this is an example of the Facade Pattern
    //Hide the details of a sub-system
    //A more useful advanced pattern is the Observer Pattern/Publisher Subscriber (RxJava well)
    //I learned patterns from a book called
    // Design Patterns Explained James R. Trott & Alan Shalloway
    private val dayStorage: IDayStorage,
    private val taskStorage: ITaskStorage
) : BaseViewLogic<DayViewEvent>() {
    override fun onViewEvent(event: DayViewEvent) {
        when (event.event) {
            DayViewEvent.Event.ON_START -> onStart()
            DayViewEvent.Event.ON_HOUR_SELECTED -> onHourSelected(event.value as Int)
            DayViewEvent.Event.ON_MANAGE_TASKS_SELECTED -> onManageTaskSelected()
        }
    }

    private fun onManageTaskSelected() {
        view.navigateToTasksView()
    }

    private fun onHourSelected(hourInteger: Int) {
        view.navigateToHourView(hourInteger)
    }

    /**
     * 1. Get data from both data sources
     * 2. Give data to the VMs
     * 3. Give data to the Views
     *
     *
     *
     */
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