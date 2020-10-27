package com.wiseassblog.samsaradayplanner.ui.dayview;

import com.wiseassblog.samsaradayplanner.common.BaseViewLogic;
import com.wiseassblog.samsaradayplanner.common.Continuation;
import com.wiseassblog.samsaradayplanner.common.Messages;
import com.wiseassblog.samsaradayplanner.domain.Day;
import com.wiseassblog.samsaradayplanner.domain.IDayStorage;
import com.wiseassblog.samsaradayplanner.domain.ITaskStorage;
import com.wiseassblog.samsaradayplanner.domain.Tasks;

//Decision maker (logic) class for the Front End
public class DayViewLogic extends BaseViewLogic<DayViewEvent> {

    private final IDayViewContract.View view;
    private final IDayViewContract.ViewModel vm;

    //backend IO devices
    //this is an example of the Facade Pattern
    //Hide the details of a sub-system
    //A more useful advanced pattern is the Observer Pattern/Publisher Subscriber (RxJava well)
    //I learned patterns from a book called
    // Design Patterns Explained James R. Trott & Alan Shalloway
    private final IDayStorage dayStorage;
    private final ITaskStorage taskStorage;

    public DayViewLogic(IDayViewContract.View view, IDayViewContract.ViewModel vm, IDayStorage dayStorage, ITaskStorage taskStorage) {
        this.view = view;
        this.vm = vm;
        this.dayStorage = dayStorage;
        this.taskStorage = taskStorage;
    }

    @Override
    public void onViewEvent(DayViewEvent event) {
        switch (event.getEvent()) {
            case ON_START:
                onStart();
                break;
            case ON_HOUR_SELECTED:
                onHourSelected((int)event.getValue());
                break;
            case ON_MANAGE_TASKS_SELECTED:
                onManageTaskSelected();
                break;
        }
    }

    private void onManageTaskSelected() {
        view.navigateToTasksView();
    }

    private void onHourSelected(int hourInteger) {
        view.navigateToHourView(hourInteger);
    }

    /**
     * 1. Get data from both data sources
     * 2. Give data to the VMs
     * 3. Give data to the Views
     *
     *
     *
     */
    private void onStart() {
        dayStorage.getDay(new Continuation<Day>() {
            @Override
            public void onSuccess(Day result) {
                getTasks(result);
            }

            @Override
            public void onException(Exception e) {
                view.showMessage(Messages.GENERIC_ERROR_MESSAGE);
                view.restartFeature();
            }
        });
    }

    private void getTasks(Day dayResult) {
        taskStorage.getTasks(new Continuation<Tasks>() {
            @Override
            public void onSuccess(Tasks taskResult) {
                bindData(dayResult, taskResult);
            }

            @Override
            public void onException(Exception e) {
                view.showMessage(Messages.GENERIC_ERROR_MESSAGE);
                view.restartFeature();
            }
        });
    }

    private void bindData(Day dayResult, Tasks taskResult) {
        vm.setDay(dayResult);
        vm.setTasks(taskResult);
        view.setData(dayResult, taskResult);
    }
}
