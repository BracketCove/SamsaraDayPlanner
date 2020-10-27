package com.wiseassblog.samsaradayplanner.ui.managehourview;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.wiseassblog.samsaradayplanner.R;
import com.wiseassblog.samsaradayplanner.common.BaseViewLogic;
import com.wiseassblog.samsaradayplanner.domain.constants.QUARTER;
import com.wiseassblog.samsaradayplanner.ui.dayview.DayActivity;

public class HourView extends Fragment implements IHourContract.View, HourToggleView.ToggleViewClickHandler {
    private BaseViewLogic<HourViewEvent> logic;
    private HourToggleView first, second, third, fourth;

    public static HourView newInstance() {
        return new HourView();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_hour_view, container, false);
        first = v.findViewById(R.id.vqht_one);

        second = v.findViewById(R.id.vqht_two);

        third = v.findViewById(R.id.vqht_three);

        fourth = v.findViewById(R.id.vqht_four);

        //toolbar action button
        v.findViewById(R.id.tlb_icon).setOnClickListener(
                view -> logic.onViewEvent(
                        new HourViewEvent(
                                HourViewEvent.Event.ON_DONE_CLICK,
                                null
                        )
                )
        );

        return v;
    }

    @Override
    public void onQuarterToggle(QUARTER quarter, boolean activate) {
        logic.onViewEvent(
                HourViewEvent.getOnQuarterToggleEvent(
                        quarter,
                        activate
                )
        );
    }

    @Override
    public void onTaskSelected(QUARTER quarter, int position) {
        logic.onViewEvent(
                HourViewEvent.getOnTaskSelectedEvent(
                        quarter,
                        position
                )
        );
    }

    /**
     * Signal onStart
     *
     * @param logic
     */
    public void setLogic(BaseViewLogic<HourViewEvent> logic) {
        this.logic = logic;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.logic.onViewEvent(
                new HourViewEvent(
                        HourViewEvent.Event.ON_START,
                        null
                )
        );
    }

    @Override
    public void setListeners() {
        first.setCallback(this);
        second.setCallback(this);
        third.setCallback(this);
        fourth.setCallback(this);
    }

    @Override
    public void setQuarterHourText(QUARTER quarter, String hour) {
        HourToggleView toggle = findToggleView(quarter);
        toggle.setTimeLabel(hour);
    }

    private HourToggleView findToggleView(QUARTER quarter) {
        switch (quarter) {
            case FIFTEEN:
                return second;
            case THIRTY:
                return third;
            case FOURTY_FIVE:
                return fourth;
            default:
                return first;
        }
    }

    @Override
    public void setQuarterHourAdapterData(QUARTER quarter, String[] taskNames, int position) {
        HourToggleView toggle = findToggleView(quarter);
        toggle.createTaskAdapter(taskNames, position);
    }

    @Override
    public void setQuarterHourActive(QUARTER quarter, boolean isActive) {
        HourToggleView toggle = findToggleView(quarter);
        toggle.setToggleState(quarter, isActive);
    }

    @Override
    public void navigateToDayView() {
        startActivity(
                new Intent(
                        requireContext(),
                        DayActivity.class
                )
        );
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Ok, the thing is that we can determine which HourToggleView by position, but we still
     * need to set the field in each HourToggleView which holds the appropriate QUARTER
     * @param q
     */
    @Override
    public void setQuarterHour(QUARTER q) {
        findToggleView(q).setQuarter(q);
    }
}
