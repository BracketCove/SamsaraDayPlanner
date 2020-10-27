package com.wiseassblog.samsaradayplanner;

import com.wiseassblog.samsaradayplanner.domain.Task;
import com.wiseassblog.samsaradayplanner.domain.constants.COLOR;
import com.wiseassblog.samsaradayplanner.domain.constants.ICON;
import com.wiseassblog.samsaradayplanner.ui.managetaskview.ITaskViewContract;
import com.wiseassblog.samsaradayplanner.ui.managetaskview.TaskViewEvent;
import com.wiseassblog.samsaradayplanner.ui.managetaskview.TaskViewLogic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class TaskViewTests {

    private FakeView view;
    private FakeViewModel vm;
    private FakeTaskStorage storage;
    private TaskViewLogic logic;

    @BeforeEach
    void setUp(){
        view = new FakeView();
        vm = new FakeViewModel();
        vm.setTask(TestData.getTestTask());
        vm.setTaskCalled = false;
        storage = new FakeTaskStorage();
        logic = new TaskViewLogic(view, vm, storage);
    }

    /**
     *User has clicked on the button which is supposed to open up the Color Picker Bottom Sheet
     * 1. Tell View to show the Color Sheet
     */
    @Test
    public void onColorButtonClick() {
        TaskViewEvent event = new TaskViewEvent(
                TaskViewEvent.Event.ON_COLOR_BUTTON_CLICK,
                ""
        );

        logic.onViewEvent(event);

        assert (view.showColorPickerSheetCalled);
    }

    /**
     * When users selects a color, we record that selection and change the color of the
     * "color button".
     *
     * 1. update ViewModel
     * 2. update View Button color
     * 3. hide the color picker sheet
     */
    @Test
    public void onColorSelected() {
        TaskViewEvent event = new TaskViewEvent(
                TaskViewEvent.Event.ON_COLOR_SELECTED,
                COLOR.DARK_BLUE
        );

        logic.onViewEvent(event);

        assert (view.setButtonColorCalled);
        assert (view.hideColorPickerSheetCalled);
        assert (vm.setTaskCalled);
    }

    /**
     * User has finished what they are doing; time to update the storage and navigate to other
     * view.
     *
     * 1. Create a new Task by grabbing text from Name EditText and combining that with Task state
     * in the VM.
     * 2. Give that new Task to Storage
     * 3a. Success: Navigate to TaskList View
     * 3b. Error: Display an error message, navigate to TaskListView
     */
    @Test
    public void onDoneClickSuccess() {
        TaskViewEvent event = new TaskViewEvent(
                TaskViewEvent.Event.ON_DONE_CLICK,
                ""
        );

        logic.onViewEvent(event);

        assert (view.getNameCalled);
        assert (vm.getTaskCalled);
        assert (view.goToTaskListActivityCalled);
    }

    @Test
    public void onDoneClickException() {
        storage.setWillFail(true);

        TaskViewEvent event = new TaskViewEvent(
                TaskViewEvent.Event.ON_DONE_CLICK,
                ""
        );


        logic.onViewEvent(event);

        assert (view.getNameCalled);
        assert (vm.getTaskCalled);
        assert (view.showMessageCalled);
        assert (view.goToTaskListActivityCalled);
    }


    /**
     * User has chosen an Icon from the dropdown menu (spinner) and the VM should reflect that
     * change
     * 1. Pull old Task from VM
     * 2. Create new data model
     * 3. Update VM
     */
    @Test
    public void onIconSelected() {
        TaskViewEvent event = new TaskViewEvent(
                TaskViewEvent.Event.ON_ICON_SELECTED,
                ICON.EXERCISE
        );

        logic.onViewEvent(event);

        assert (vm.getTaskCalled);
        assert (vm.setTaskCalled);
        assert (view.setIconSelectionCalled);
    }

    /**
     * NOTE: This code assumes that the VM was initialized with a TaskID that was passed in from
     * the Activity.
     *
     * When this feature starts, we want to retrieve that Task from storage using the TaskID that
     * was given initially to the VM.
     *
     * 1. Take id which was provided by Activity.onCreate -> Give ID to ViewModel
     * 2. Take ID from VM and give to storage for proper Task by ID
     * 3a. Success: Update the VM accordingly
     * 3b. Exception: show error message
     * 4a. Update View accordingly
     * 4b. return to TaskList feature
     */
    @Test
    public void onStartSuccess() {
        TaskViewEvent event = new TaskViewEvent(
                TaskViewEvent.Event.ON_START,
                0
        );

        logic.onViewEvent(event);

        assert (vm.setTaskCalled);
        assert (vm.getTaskCalled);
        assert (view.setButtonColorCalled);
        assert (view.setNameCalled);
        assert (view.setIconSelectionCalled);
        assert (view.setSpinnerItemSelection);
    }

    @Test
    public void onStartException() {
        storage.setWillFail(true);

        TaskViewEvent event = new TaskViewEvent(
                TaskViewEvent.Event.ON_START,
                ""
        );

        logic.onViewEvent(event);

        assert (vm.getTaskCalled);
        assert (view.showMessageCalled);
        assert (view.goToTaskListActivityCalled);
    }


    private class FakeViewModel implements ITaskViewContract.ViewModel {
        private Task task = null;
        boolean setTaskCalled = false;
        boolean getTaskCalled = false;

        @Override
        public void setTask(Task task) {
            this.task = task;
            setTaskCalled = true;
        }

        @Override
        public Task getTask() {
            getTaskCalled = true;
            return task;
        }
    }

    private class FakeView implements ITaskViewContract.View {

        boolean showColorPickerSheetCalled = false;
        boolean hideColorPickerSheetCalled = false;
        boolean setNameCalled = false;
        boolean getNameCalled = false;
        boolean setIconSelectionCalled = false;
        boolean setSpinnerItemSelection = false;
        boolean setButtonColorCalled = false;
        boolean goToTaskListActivityCalled = false;
        boolean showMessageCalled = false;

        @Override
        public void showColorPickerSheet() {
            showColorPickerSheetCalled = true;
        }

        @Override
        public void hideColorPickerSheet() {
            hideColorPickerSheetCalled = true;
        }

        @Override
        public void setName(String name) {
            setNameCalled = true;
        }

        @Override
        public String getName() {
            getNameCalled = true;
            return "Hunter S. Thompson";
        }

        @Override
        public void setIconSelection(ICON icon) {
            setIconSelectionCalled = true;
        }

        @Override
        public void setSelectedSpinnerItem(int position) {
            setSpinnerItemSelection = true;
        }

        @Override
        public void setButtonColor(COLOR c) {
            setButtonColorCalled = true;
        }

        @Override
        public void goToTaskListActivity() {
            goToTaskListActivityCalled = true;
        }

        @Override
        public void showMessage(String message) {
            showMessageCalled = true;
        }
    }
}
