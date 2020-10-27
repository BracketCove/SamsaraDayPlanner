package com.wiseassblog.samsaradayplanner.ui.managehourview;

import com.wiseassblog.samsaradayplanner.common.BaseViewLogic;
import com.wiseassblog.samsaradayplanner.common.Continuation;
import com.wiseassblog.samsaradayplanner.common.TimeFormatUtility;
import com.wiseassblog.samsaradayplanner.domain.Hour;
import com.wiseassblog.samsaradayplanner.domain.IDayStorage;
import com.wiseassblog.samsaradayplanner.domain.ITaskStorage;
import com.wiseassblog.samsaradayplanner.domain.QuarterHour;
import com.wiseassblog.samsaradayplanner.domain.Task;
import com.wiseassblog.samsaradayplanner.domain.Tasks;
import com.wiseassblog.samsaradayplanner.domain.constants.HOUR_MODE;
import com.wiseassblog.samsaradayplanner.domain.constants.QUARTER;

public class HourViewLogic extends BaseViewLogic<HourViewEvent> {

    private final IHourContract.View view;
    private final IHourContract.ViewModel vm;
    private final IDayStorage dayStorage;
    private final ITaskStorage taskStorage;

    public HourViewLogic(IHourContract.View view,
                         IHourContract.ViewModel vm,
                         IDayStorage dayStorage,
                         ITaskStorage taskStorage) {
        this.view = view;
        this.vm = vm;
        this.dayStorage = dayStorage;
        this.taskStorage = taskStorage;
    }

    @Override
    public void onViewEvent(HourViewEvent event) {
        switch (event.getEvent()) {
            case ON_START:
                onStart();
                break;
            case ON_DONE_CLICK:
                onDone();
                break;
            case ON_QUARTER_TOGGLE:
                onQuarterToggled(event.getQuarter(), event.isActive());
                break;
            case ON_TASK_SELECTED:
                onTaskSelected(event.getQuarter(), event.getPosition());
                break;
        }
    }

    private void onQuarterToggled(QUARTER quarter, boolean isActive) {
        Hour hour = vm.getHour();
        QuarterHour oldQuarterHour = null;

        int quarterHourIndex = 0;

        for (QuarterHour q : hour.getQuarters()) {
            if (q.getQuarter() == quarter) {
                oldQuarterHour = q;
                break;
            }

            quarterHourIndex++;
        }

        //Probably not necessary but working in C made me paranoid
        if (oldQuarterHour == null) {
            view.showMessage("An error has occurred.");
            view.navigateToDayView();
        }

        hour.getQuarters()[quarterHourIndex] = new QuarterHour(
                oldQuarterHour.getTaskId(),
                oldQuarterHour.getQuarter(),
                isActive
        );
    }

    private void onTaskSelected(QUARTER quarter, int position) {
        Tasks tasks = vm.getTasks();
        Hour hour = vm.getHour();

        //newly selected Task by ID (which reflects the position the list of tasks
        int taskId = tasks.get()[position].getTaskId();

        //Retrieve the correct Quarter Hour from current Hour
        QuarterHour oldQuarterHour = null;
        int quarterHourIndex = 0;

        for (QuarterHour q : hour.getQuarters()) {
            if (q.getQuarter() == quarter) {
                oldQuarterHour = q;
                break;
            }

            quarterHourIndex++;
        }

        //Probably not necessary but working in C made me paranoid
        if (oldQuarterHour == null) {
            view.showMessage("An error has occurred.");
            view.navigateToDayView();
        }

        hour.getQuarters()[quarterHourIndex] = new QuarterHour(
                taskId,
                oldQuarterHour.getQuarter(),
                oldQuarterHour.getIsActive()
        );
    }

    private void onDone() {
        dayStorage.updateHour(vm.getHour(),
                new Continuation<Void>() {
                    @Override
                    public void onSuccess(Void result) {
                        view.navigateToDayView();
                    }

                    @Override
                    public void onException(Exception e) {
                        view.showMessage(e.getMessage());
                        view.navigateToDayView();
                    }
                }
        );
    }

    private void onStart() {
        //I give the VM the hour integer and fake data for the rest of the hour, and then
        //grab it from
        int hour = vm.getHour().getHourInteger();
        dayStorage.getHourWithMode(
                hour,
                new Continuation<Object[]>() {

                    /**
                     * Expect for onSuccess:
                     * result[0] to be an Hour Type
                     * result[1] to be an HOUR_MODE enum
                     */
                    @Override
                    public void onSuccess(Object[] result) {
                        onHourRetrieved(
                                (Hour) result[0],
                                (HOUR_MODE) result[1]
                        );
                    }

                    @Override
                    public void onException(Exception e) {
                        view.showMessage(e.getMessage());
                        view.navigateToDayView();
                    }
                }
        );
    }

    private void onHourRetrieved(Hour hour, HOUR_MODE mode) {
        vm.setHour(hour);

        taskStorage.getTasks(
                new Continuation<Tasks>() {
                    @Override
                    public void onSuccess(Tasks tasks) {
                        onTasksRetrieved(
                                tasks,
                                hour,
                                mode
                        );
                    }

                    @Override
                    public void onException(Exception e) {
                        view.showMessage(e.getMessage());
                        view.navigateToDayView();
                    }
                }
        );
    }

    private void onTasksRetrieved(Tasks tasks, Hour hour, HOUR_MODE mode) {
        vm.setTaskList(tasks);
        drawView(tasks, hour, mode);
    }

    private void drawView(Tasks tasks, Hour hour, HOUR_MODE mode) {
        int index = 0;

        //for use in a spinner widget
        String[] taskNames = getTaskNames(tasks.get().length, tasks.get());

        for (QUARTER q : QUARTER.values()) {

            view.setQuarterHour(q);


            String formattedHourText = TimeFormatUtility.getHourToggleViewFormattedText(
                    q,
                    hour.getHourInteger(),
                    mode
            );

            view.setQuarterHourText(
                    q,
                    formattedHourText
            );

            view.setQuarterHourActive(
                    q,
                    hour.getQuarters()[index].getIsActive()
            );

            view.setQuarterHourAdapterData(
                    q,
                    taskNames,
                    hour.getQuarters()[index].getTaskId()
            );

            index++;
        }

        view.setListeners();
    }

    private String[] getTaskNames(int length, Task[] tasks) {
        String[] taskNames = new String[length];

        for (int i = 0; i < length; i++) {
            taskNames[i] = tasks[i].getTaskName();
        }

        return taskNames;
    }
}
