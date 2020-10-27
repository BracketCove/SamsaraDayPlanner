package com.wiseassblog.samsaradayplanner.ui.dayview;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.wiseassblog.samsaradayplanner.R;
import com.wiseassblog.samsaradayplanner.common.BaseViewLogic;
import com.wiseassblog.samsaradayplanner.domain.Day;
import com.wiseassblog.samsaradayplanner.domain.Tasks;
import com.wiseassblog.samsaradayplanner.ui.managehourview.HourActivity;
import com.wiseassblog.samsaradayplanner.ui.tasklistview.TaskListActivity;

import static com.wiseassblog.samsaradayplanner.domain.constants.Extras.EXTRA_HOUR_INTEGER;
import static com.wiseassblog.samsaradayplanner.ui.dayview.DayViewEvent.Event.ON_HOUR_SELECTED;
import static com.wiseassblog.samsaradayplanner.ui.dayview.DayViewEvent.Event.ON_START;

/**
 * I did not bother using the Pub/Sub pattern here because ultimately the presentation logic is
 * properly decoupled via DayListItemViewMaker.java and the Adapter itself
 */
public class DayView extends Fragment
        implements IDayViewContract.View {

    private BaseViewLogic<DayViewEvent> logic;

    private RecyclerView rec;
    private ImageButton toolbarManageTasks;
    private DayAdapter adapter;
    private View view;

    public void setLogic(BaseViewLogic<DayViewEvent> logic) {
        this.logic = logic;
    }

    public static DayView newInstance() {
        return new DayView();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_day_view, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        toolbarManageTasks = view.findViewById(R.id.imb_toolbar_manage_tasks);
        toolbarManageTasks.setOnClickListener(
                view -> {
                    logic.onViewEvent(
                            new DayViewEvent(
                                    DayViewEvent.Event.ON_MANAGE_TASKS_SELECTED,
                                    0
                            )
                    );
                }
        );

        rec = view.findViewById(R.id.rec_day_list);
    }

    @Override
    public void onResume() {
        super.onResume();
        logic.onViewEvent(new DayViewEvent(ON_START, ""));
    }



    @Override
    public void setData(Day day, Tasks tasks) {
        adapter = new DayAdapter(
                DayListItemViewMaker.getItemList(
                        this.requireContext(),
                        day,
                        tasks
                ),
                position -> logic.onViewEvent(
                        new DayViewEvent(
                                ON_HOUR_SELECTED,
                                position
                        )
                )
        );

        rec.setAdapter(adapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        rec.setAdapter(null);
    }

    @Override
    public void navigateToHourView(int hourInteger) {
        Intent i = new Intent(
                requireContext(),
                HourActivity.class
        );

        i.putExtra(EXTRA_HOUR_INTEGER, hourInteger);

        startActivity(i);
    }

    @Override
    public void navigateToTasksView() {
        startActivity(
                new Intent(requireContext(), TaskListActivity.class)
        );
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void restartFeature() {
        requireActivity().recreate();
        startActivity(
                new Intent(
                        requireActivity(),
                        DayActivity.class
                )
        );
    }
}
