package com.wiseassblog.samsaradayplanner;

import com.wiseassblog.samsaradayplanner.domain.Hour;
import com.wiseassblog.samsaradayplanner.domain.Tasks;
import com.wiseassblog.samsaradayplanner.domain.constants.QUARTER;
import com.wiseassblog.samsaradayplanner.ui.managehourview.HourViewEvent;
import com.wiseassblog.samsaradayplanner.ui.managehourview.HourViewLogic;
import com.wiseassblog.samsaradayplanner.ui.managehourview.IHourContract;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HourViewTests {

    //Class to be tested
    private HourViewLogic logic;
    private FakeHourView view;
    private FakeHourViewModel vm;
    private FakeDayStorage dayStorage;
    private FakeTaskStorage taskStorage;

    @BeforeEach
    void setUp(){
        view = new HourViewTests.FakeHourView();
        vm = new HourViewTests.FakeHourViewModel();
        vm.setHour(TestData.getTestHour());
        vm.setHourCalled = false;
        vm.setTaskList(TestData.getTestTasks());
        vm.setTasksCalled = false;
        dayStorage = new FakeDayStorage();
        taskStorage = new FakeTaskStorage();
        logic = new HourViewLogic(view, vm, dayStorage, taskStorage);
    }

    /**
     * When the feature first starts, we want to bind the data from storage to the user interface
     * appropriately.
     *
     * 1. Get Hour id and list of tasks from ViewModel
     * 2a. Retrieve Hour from storage: Success
     * 3a. Retrieve Tasks from storage:Success
     * 4a. For each Quarter Hour, bind the Hour Display, Dropdown Task Menu, and Toggle button\
     *
     * 2b. Retrieve Hour from storage: Fail
     * 3b. Show error message and return to calling Activity
     *
     * 3c. Retrieve Tasks from storage: Fail
     * 4c. Show error message and return to calling Activity
     *
     *
     *
     * 4.
     *
     * a case (success)
     */
    @Test
    public void onStartSuccess() {
        HourViewEvent event = new HourViewEvent(
                HourViewEvent.Event.ON_START,
                ""
        );

        logic.onViewEvent(event);

        assert (vm.getHourCalled);
        assert (vm.setHourCalled);
        assert (vm.setTasksCalled);
        assert (view.numberOfTimesSetQuarterHourTextCalled == 4);
        assert (view.numberOfTimesQuarterHourActiveCalled == 4);
        assert (view.numberOfTimesQuarterHourDropdownCalled == 4);
        assert (view.setQuarterCalled);
    }

    /**
     * b case
     */
    @Test
    public void onStartDayStorageException() {
        dayStorage.setWillFail(true);

        HourViewEvent event = new HourViewEvent(
                HourViewEvent.Event.ON_START,
                ""
        );

        logic.onViewEvent(event);

        assert (vm.getHourCalled);
        assert (view.showMessageCalled);
        assert (view.navigateToDayViewCalled);
    }

    /**
     * c case
     *
     */
    @Test
    public void onStartTaskStorageException() {
        taskStorage.setWillFail(true);

        HourViewEvent event = new HourViewEvent(
                HourViewEvent.Event.ON_START,
                ""
        );

        logic.onViewEvent(event);

        assert (vm.getHourCalled);
        assert (vm.setHourCalled);
        assert (view.showMessageCalled);
        assert (view.navigateToDayViewCalled);
    }

    /**
     * When user clicks done button, save the data and navigate to the calling activity.
     *
     * 1. Pull data from the viewmodel
     * 2a. Write data to storage: success
     * 2b. Write data to storage: failure
     * 3a. navigate to calling activity
     * 3b. show message and navigate to calling activity
     *
     */
    @Test
    public void onDoneSuccess() {
        HourViewEvent event = new HourViewEvent(
                HourViewEvent.Event.ON_DONE_CLICK,
                ""
        );

        logic.onViewEvent(event);

        assert (vm.getHourCalled);
        assert (view.navigateToDayViewCalled);
    }

    /**
     * User selects a task for a quarter hour; update the ViewModel accordingly
     *
     * No need for failure case because we do not touch I/O devices
     *
     * 1. From selected position, retrieve the new task name from vm
     * 2. Retrieve old quarter hour from vm
     * 3. Update appropriate quarter hour in vm with new selected task
     */
    @Test
    public void onTaskSelectedSuccess() {
        HourViewEvent event = HourViewEvent.getOnTaskSelectedEvent(QUARTER.ZERO, 0);

        logic.onViewEvent(event);

        assert (vm.getTasksCalled);
        assert (vm.getHourCalled);
    }

    /**
     * User has hit the switch for a Quarter to render it active or inactive
     *
     * 1. Locate the appropriate quarter
     * 2. update it in VM
     */
    @Test
    public void onQuarterToggledSuccess() {
        HourViewEvent event = HourViewEvent.getOnQuarterToggleEvent(QUARTER.ZERO, true);

        logic.onViewEvent(event);

        assert (vm.getHourCalled);
    }

    private class FakeHourView implements IHourContract.View {

        boolean setQuarterHourTextCalled = false;
        int numberOfTimesSetQuarterHourTextCalled = 0;

        boolean setQuarterHourDropdownCalled = false;
        int numberOfTimesQuarterHourDropdownCalled = 0;

        boolean setQuarterHourActiveCalled = false;
        int numberOfTimesQuarterHourActiveCalled = 0;

        boolean navigateToDayViewCalled = false;
        boolean showMessageCalled = false;

        boolean setQuarterCalled = false;


        @Override
        public void setQuarterHourText(QUARTER quarter, String hour) {
            setQuarterHourTextCalled = true;
            numberOfTimesSetQuarterHourTextCalled++;
        }

        @Override
        public void setQuarterHourAdapterData(QUARTER quarter, String[] taskNames, int startingPosition) {
            setQuarterHourDropdownCalled = true;
            numberOfTimesQuarterHourDropdownCalled++;
        }

        @Override
        public void setQuarterHourActive(QUARTER quarter, boolean isActive) {
            setQuarterHourActiveCalled = true;
            numberOfTimesQuarterHourActiveCalled++;
        }

        @Override
        public void navigateToDayView() {
            navigateToDayViewCalled = true;
        }

        @Override
        public void showMessage(String message) {
            showMessageCalled = true;
        }

        @Override
        public void setQuarterHour(QUARTER q) {
            this.setQuarterCalled = true;
        }
    }

    private class FakeHourViewModel implements IHourContract.ViewModel {
        private Hour hour = null;
        private Tasks tasks = null;

        private boolean setHourCalled = false;
        private boolean getHourCalled = false;
        private boolean setTasksCalled = false;
        private boolean getTasksCalled = false;

        @Override
        public void setHour(Hour hour) {
            this.hour = hour;
            setHourCalled = true;
        }

        @Override
        public Hour getHour() {
            getHourCalled = true;
            return this.hour;
        }

        @Override
        public void setTaskList(Tasks tasks) {
            this.tasks = tasks;
            setTasksCalled = true;
        }

        @Override
        public Tasks getTasks() {
            getTasksCalled = true;
            return this.tasks;
        }
    }
}
