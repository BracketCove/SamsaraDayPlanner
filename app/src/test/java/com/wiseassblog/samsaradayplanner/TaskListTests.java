package com.wiseassblog.samsaradayplanner;

import com.wiseassblog.samsaradayplanner.domain.Tasks;
import com.wiseassblog.samsaradayplanner.ui.tasklistview.ITaskListViewContract;
import com.wiseassblog.samsaradayplanner.ui.tasklistview.TaskListViewLogic;
import com.wiseassblog.samsaradayplanner.ui.tasklistview.TasksListViewEvent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TaskListTests {

    private TaskListViewLogic logic;
    private FakeTaskListView view;
    private FakeTaskListViewModel vm;
    private FakeTaskStorage taskStorage;

    @BeforeEach
    void setUp(){
        view = new TaskListTests.FakeTaskListView();
        vm = new TaskListTests.FakeTaskListViewModel();
        vm.setTasks(TestData.getTestTasks());
        vm.setTasksCalled = false;
        taskStorage = new FakeTaskStorage();
        logic = new TaskListViewLogic(view, vm, taskStorage);
    }

    /**
     * Retrieve the list of tasks from storage, update VM, and update View
     */
    @Test
    public void onStartSuccessful() {
        TasksListViewEvent event = new TasksListViewEvent(TasksListViewEvent.Event.ON_START, 0);

        logic.onViewEvent(event);

        assert (vm.setTasksCalled);
        assert (view.setTasksCalled);
    }

    @Test
    public void onStartUnsuccessful() {
        TasksListViewEvent event = new TasksListViewEvent(TasksListViewEvent.Event.ON_START, 0);

        taskStorage.setWillFail(true);

        logic.onViewEvent(event);

        assert (view.showMessageCalled);
        assert (view.navigateDayViewCalled);
    }

    /**
     * User has selected a task and therefore wants to edit it.
     *
     * 1. Retrieve position from selected item
     * 2. Retrieve id from VM relative to position
     * 3. Tell view to start manage task view with id as bundle extra
     *
     */
    @Test
    public void onTaskSelectedSuccess() {
        TasksListViewEvent event = new TasksListViewEvent(
                TasksListViewEvent.Event.ON_LIST_ITEM_SELECTED,
                0);

        logic.onViewEvent(event);

        assert (vm.getTasksCalled);
        assert (view.navigateManageTaskViewCalled);

    }

    @Test
    public void onBackPressed() {
        TasksListViewEvent event = new TasksListViewEvent(
                TasksListViewEvent.Event.ON_BACK_PRESSED,
                0);

        logic.onViewEvent(event);

        assert (view.navigateDayViewCalled);
    }

    private class FakeTaskListView implements ITaskListViewContract.View {

        boolean setTasksCalled = false;
        boolean showMessageCalled = false;
        boolean navigateDayViewCalled = false;
        boolean navigateManageTaskViewCalled = false;


        @Override
        public void setTasks(Tasks tasks) {
            this.setTasksCalled = true;
        }

        @Override
        public void showMessage(String message) {
            showMessageCalled = true;
        }

        @Override
        public void navigateToDayView() {
            navigateDayViewCalled = true;
        }

        @Override
        public void navigateToTaskView(int taskId) {
            navigateManageTaskViewCalled = true;
        }
    }

    private class FakeTaskListViewModel implements ITaskListViewContract.ViewModel {
        private Tasks tasks = null;

        private boolean setTasksCalled = false;
        private boolean getTasksCalled = false;
        @Override
        public void setTasks(Tasks tasks) {
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
