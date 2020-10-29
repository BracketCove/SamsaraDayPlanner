package com.wiseassblog.samsaradayplanner.ui.managehourview

import com.wiseassblog.samsaradayplanner.common.BaseViewLogic
import com.wiseassblog.samsaradayplanner.common.getHourToggleViewFormattedText
import com.wiseassblog.samsaradayplanner.domain.*
import com.wiseassblog.samsaradayplanner.domain.constants.HOUR_MODE
import com.wiseassblog.samsaradayplanner.domain.constants.Messages.GENERIC_ERROR_MESSAGE
import com.wiseassblog.samsaradayplanner.domain.constants.QUARTER

class HourViewLogic(
    private val view: IHourContract.View,
    private val vm: IHourContract.ViewModel,
    private val dayStorage: IDayStorage,
    private val taskStorage: ITaskStorage
) : BaseViewLogic<HourViewEvent>() {
    override fun onViewEvent(event: HourViewEvent) {
        when (event) {
            is HourViewEvent.OnStart -> onStart()
            is HourViewEvent.OnDoneClick -> onDone()
            is HourViewEvent.OnQuarterToggled -> onQuarterToggled(event.quarter, event.active)
            is HourViewEvent.OnTaskSelected -> onTaskSelected(event.quarter, event.position)
        }
    }

    private fun onQuarterToggled(quarter: QUARTER, isActive: Boolean) {
        val hour = vm.hour
        var oldQuarterHour: QuarterHour? = null
        var quarterHourIndex = 0
        for (q in hour.quarters!!) {
            if (q.quarter == quarter) {
                oldQuarterHour = q
                break
            }
            quarterHourIndex++
        }

        //Probably not necessary but working in C made me paranoid
        if (oldQuarterHour == null) {
            view.showMessage("An error has occurred.")
            view.navigateToDayView()
        }
        hour.quarters[quarterHourIndex] = QuarterHour(
            oldQuarterHour!!.taskId,
            oldQuarterHour.quarter,
            isActive
        )
    }

    private fun onTaskSelected(quarter: QUARTER, position: Int) {
        val tasks = vm.tasks
        val hour = vm.hour

        //newly selected Task by ID (which reflects the position the list of tasks
        val taskId = tasks.get()[position].taskId

        //Retrieve the correct Quarter Hour from current Hour
        var oldQuarterHour: QuarterHour? = null
        var quarterHourIndex = 0
        for (q in hour.quarters!!) {
            if (q.quarter == quarter) {
                oldQuarterHour = q
                break
            }
            quarterHourIndex++
        }

        //Probably not necessary but working in C made me paranoid
        if (oldQuarterHour == null) {
            view.showMessage("An error has occurred.")
            view.navigateToDayView()
        }
        hour.quarters[quarterHourIndex] = QuarterHour(
            taskId,
            oldQuarterHour!!.quarter,
            oldQuarterHour.isActive
        )
    }

    private fun onDone() {
        dayStorage.updateHour(
            vm.hour,
            {
                view.navigateToDayView()
            },
            {
                view.showMessage(GENERIC_ERROR_MESSAGE)
                view.navigateToDayView()
            }
        )
    }

    private fun onStart() {
        //I give the VM the hour integer and fake data for the rest of the hour, and then
        //grab it from
        val hour = vm.hour.hourInteger
        dayStorage.getHourWithMode(
            hour,
            { hour, mode ->
                onHourRetrieved(hour, mode)
            },
            {
                view.showMessage(GENERIC_ERROR_MESSAGE)
                view.navigateToDayView()
            }
        )
    }

    private fun onHourRetrieved(hour: Hour, mode: HOUR_MODE) {
        vm.hour = hour
        taskStorage.getTasks(
            { tasks ->
                onTasksRetrieved(
                    tasks,
                    hour,
                    mode
                )
            },
            {
                view.showMessage(GENERIC_ERROR_MESSAGE)
                view.navigateToDayView()
            }
        )
    }

    private fun onTasksRetrieved(tasks: Tasks, hour: Hour, mode: HOUR_MODE) {
        vm.tasks = tasks
        drawView(tasks, hour, mode)
    }

    private fun drawView(tasks: Tasks, hour: Hour, mode: HOUR_MODE) {
        var index = 0

        //for use in a spinner widget
        val taskNames = Array(tasks.get().size) { index -> tasks.get()[index].taskName }
        for (q in QUARTER.values()) {
            view.setQuarterHour(q)
            val formattedHourText = getHourToggleViewFormattedText(
                q,
                hour.hourInteger,
                mode
            )
            view.setQuarterHourText(
                q,
                formattedHourText
            )
            view.setQuarterHourActive(
                q,
                hour.quarters!![index].isActive
            )
            view.setQuarterHourAdapterData(
                q,
                taskNames,
                hour.quarters[index].taskId
            )
            index++
        }
        view.setListeners()
    }

}