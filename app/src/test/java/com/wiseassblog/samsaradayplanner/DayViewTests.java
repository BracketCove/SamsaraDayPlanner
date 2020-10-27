package com.wiseassblog.samsaradayplanner;

import com.wiseassblog.samsaradayplanner.domain.Day;
import com.wiseassblog.samsaradayplanner.domain.Tasks;
import com.wiseassblog.samsaradayplanner.ui.dayview.DayViewEvent;
import com.wiseassblog.samsaradayplanner.ui.dayview.DayViewLogic;
import com.wiseassblog.samsaradayplanner.ui.dayview.IDayViewContract;
import com.wiseassblog.samsaradayplanner.ui.managehourview.HourViewEvent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Semaphore;

import static com.wiseassblog.samsaradayplanner.TestData.HOUR_INTEGER;

class DayViewTests {
    private DayViewLogic logic;
    private DayViewTests.FakeDayView view;
    private DayViewTests.FakeDayViewModel vm;
    private FakeDayStorage dayStorage;
    private FakeTaskStorage taskStorage;

    @BeforeEach
    void setUp() {
        view = new DayViewTests.FakeDayView();
        vm = new DayViewTests.FakeDayViewModel();
        vm.setDay(TestData.getTestDay());
        vm.setDayCalled = false;
        dayStorage = new FakeDayStorage();
        taskStorage = new FakeTaskStorage();
        logic = new DayViewLogic(view, vm, dayStorage, taskStorage);
    }

    /**
     * Designing ahead of time without worrying about implementation details
     * - Design of code, and implementation present different (often related) problems which
     * ought to be solved separately
     *
     * Retrieve current day from storage, as well as the list of tasks, and render that
     *      * appropriately
     *      *
     *      * 1. Get Day
     *      * 2. Get Tasks
     *      * 3. Give to View and ViewModel
     *      *
     *      * Note, if either of the IO devices fail, we cannot render the view properly, and therefore
     *      * a reboot is the only recourse.
     */
    @Test
    public void onStartSuccess() {
        DayViewEvent event = new DayViewEvent(
                DayViewEvent.Event.ON_START,
                ""
        );

        logic.onViewEvent(event);

        assert (vm.setDayCalled);
        assert (vm.setTasksCalled);
        assert (view.setDataCalled);
    }

    @Test
    public void onStartDayStorageException() {
        dayStorage.setWillFail(true);

        DayViewEvent event = new DayViewEvent(
                DayViewEvent.Event.ON_START,
                ""
        );

        logic.onViewEvent(event);

        assert (view.showMessageCalled);
        assert (view.restartFeatureCalled);
    }

    @Test
    public void onStartTaskStorageException() {
        taskStorage.setWillFail(true);

        DayViewEvent event = new DayViewEvent(
                DayViewEvent.Event.ON_START,
                ""
        );

        logic.onViewEvent(event);

        assert (view.showMessageCalled);
        assert (view.restartFeatureCalled);
    }


    /**
     * Tell the View to start the HourViewFeature and give it the appropriate data
     *
     *
     */
    @Test
    public void onHourSelected() {
        DayViewEvent event = new DayViewEvent(
                DayViewEvent.Event.ON_HOUR_SELECTED,
                12
        );

        logic.onViewEvent(event);

        assert (view.navigateHourCalled);
    }

    /**
     *
     * 1. Tell view to start manage task feature
     *
     */
    @Test
    public void onManageTasksSelected() {
        DayViewEvent event = new DayViewEvent(
                DayViewEvent.Event.ON_MANAGE_TASKS_SELECTED,
                null
        );

        logic.onViewEvent(event);

        assert (view.navigateToTasksViewCalled);
    }



    private class FakeDayView implements IDayViewContract.View {

        boolean setDataCalled = false;

        boolean navigateHourCalled = false;

        boolean restartFeatureCalled = false;

        boolean navigateToTasksViewCalled = false;

        boolean showMessageCalled = false;

        @Override
        public void setData(Day day, Tasks tasks) {
            this.setDataCalled = true;
        }

        @Override
        public void navigateToHourView(int hourInteger) {
            this.navigateHourCalled = true;
        }

        @Override
        public void navigateToTasksView() {
            this.navigateToTasksViewCalled = true;
        }

        @Override
        public void showMessage(String message) {
            showMessageCalled = true;
        }

        @Override
        public void restartFeature() {
            restartFeatureCalled = true;
        }
    }

    private class FakeDayViewModel implements IDayViewContract.ViewModel {
        private Day day = null;
        private Tasks tasks = null;

        boolean setDayCalled = false;
        boolean getDayCalled = false;

        boolean setTasksCalled = false;
        boolean getTasksCalled = false;

        @Override
        public void setDay(Day day) {
            this.day = day;
            this.setDayCalled = true;
        }

        @Override
        public Day getDay() {
            this.getDayCalled = true;
            return this.day;
        }

        @Override
        public void setTasks(Tasks tasks) {
            this.setTasksCalled = true;
            this.tasks = tasks;
        }

        @Override
        public Tasks getTasks() {
            this.getTasksCalled = true;
            return this.tasks;
        }
    }

}
