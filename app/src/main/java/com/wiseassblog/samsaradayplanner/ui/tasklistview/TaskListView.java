package com.wiseassblog.samsaradayplanner.ui.tasklistview;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.wiseassblog.samsaradayplanner.R;
import com.wiseassblog.samsaradayplanner.common.BaseViewLogic;
import com.wiseassblog.samsaradayplanner.domain.Tasks;
import com.wiseassblog.samsaradayplanner.ui.dayview.DayActivity;
import com.wiseassblog.samsaradayplanner.ui.dayview.DayViewEvent;
import com.wiseassblog.samsaradayplanner.ui.managetaskview.TaskActivity;

import static com.wiseassblog.samsaradayplanner.domain.constants.Extras.EXTRA_TASK_ID;
import static com.wiseassblog.samsaradayplanner.ui.tasklistview.TasksListViewEvent.Event.ON_BACK_PRESSED;
import static com.wiseassblog.samsaradayplanner.ui.tasklistview.TasksListViewEvent.Event.ON_LIST_ITEM_SELECTED;
import static com.wiseassblog.samsaradayplanner.ui.tasklistview.TasksListViewEvent.Event.ON_START;

public class TaskListView extends Fragment implements ITaskListViewContract.View {
    private BaseViewLogic<TasksListViewEvent> logic;
    private ImageButton back;
    private GridView taskGrid;
    private View view;


    public static TaskListView newInstance() {
        return new TaskListView();
    }

    public void setLogic(BaseViewLogic<TasksListViewEvent> logic) {
        this.logic = logic;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tasks, container, false);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        back = view.findViewById(R.id.tlb_icon);
        back.setOnClickListener(
                v -> {
                    logic.onViewEvent(
                            new TasksListViewEvent(
                                    ON_BACK_PRESSED,
                                    null
                            )
                    );
                }
        );

        taskGrid = view.findViewById(R.id.gdl_list_item_task);
    }

    @Override
    public void onResume() {
        super.onResume();
        logic.onViewEvent(new TasksListViewEvent(ON_START, ""));
    }

    @Override
    public void setTasks(Tasks tasks) {
        TaskGridItemViewAdapter adapter = new TaskGridItemViewAdapter(
                requireContext(),
                tasks.get()
        );

        taskGrid.setAdapter(adapter);

        taskGrid.setOnItemClickListener(
                //Note: position in adapter and TaskId are equivalent values
                (adapterView, clickView, position, id) -> logic.onViewEvent(
                        new TasksListViewEvent(
                                ON_LIST_ITEM_SELECTED,
                                position
                        )
                )
        );
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToDayView() {
        startActivity(
                new Intent(
                        requireActivity(),
                        DayActivity.class
                )
        );
    }

    @Override
    public void navigateToTaskView(int taskId) {
        startActivity(
                new Intent(
                        requireActivity(),
                        TaskActivity.class
                ).putExtra(
                        EXTRA_TASK_ID,
                        taskId
                )
        );
    }
}
