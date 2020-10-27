package com.wiseassblog.samsaradayplanner.ui.managetaskview;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.wiseassblog.samsaradayplanner.R;
import com.wiseassblog.samsaradayplanner.common.BaseViewLogic;
import com.wiseassblog.samsaradayplanner.common.ColorUtility;
import com.wiseassblog.samsaradayplanner.common.IconUtility;
import com.wiseassblog.samsaradayplanner.domain.constants.COLOR;
import com.wiseassblog.samsaradayplanner.domain.constants.ICON;
import com.wiseassblog.samsaradayplanner.ui.tasklistview.TaskListActivity;

import static com.wiseassblog.samsaradayplanner.ui.managetaskview.TaskViewEvent.Event.ON_COLOR_SELECTED;
import static com.wiseassblog.samsaradayplanner.ui.managetaskview.TaskViewEvent.Event.ON_ICON_SELECTED;
import static com.wiseassblog.samsaradayplanner.ui.managetaskview.TaskViewEvent.Event.ON_START;

public class TaskView extends Fragment implements ITaskViewContract.View, ColorPickerView.ColorPickerViewClickHandler {

    private BaseViewLogic<TaskViewEvent> logic;

    private ImageButton doneButton;
    private Button selectColorButton;
    private EditText nameField;
    private ImageView selectedIcon;
    private Spinner iconSpinner;
    private ColorPickerView bottomSheetView;
    private BottomSheetBehavior bs;

    public static TaskView newInstance() {
        return new TaskView();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage_task, container, false);

        selectColorButton = view.findViewById(R.id.btn_select_colour);
        selectColorButton.setOnClickListener(
                v -> {
                    logic.onViewEvent(
                            new TaskViewEvent(
                                    TaskViewEvent.Event.ON_COLOR_BUTTON_CLICK,
                                    null
                            )
                    );
                }
        );

        doneButton = view.findViewById(R.id.tlb_icon);
        doneButton.setOnClickListener(
                v -> {
                    logic.onViewEvent(
                            new TaskViewEvent(
                                    TaskViewEvent.Event.ON_DONE_CLICK,
                                    null
                            )
                    );
                }
        );

        selectedIcon = view.findViewById(R.id.imv_icon_selection);

        iconSpinner = view.findViewById(R.id.spn_task_icon);

        IconSpinnerAdapter adapter = new IconSpinnerAdapter(
                this.requireContext(),
                IconSpinnerItem.getItems(requireContext())
        );

        iconSpinner.setAdapter(adapter);

        iconSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView,
                                               View view,
                                               int position,
                                               long l) {
                        logic.onViewEvent(
                                new TaskViewEvent(
                                        ON_ICON_SELECTED,
                                        ICON.values()[position]
                                )
                        );
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                }
        );

        nameField = view.findViewById(R.id.edt_task_name);

        bottomSheetView = view.findViewById(R.id.bts_select_color);
        bottomSheetView.setCallback(this);

        bs = BottomSheetBehavior.from(bottomSheetView);
        bs.setHideable(true);
        bs.setState(BottomSheetBehavior.STATE_HIDDEN);

        return view;
    }

    public void setLogic(BaseViewLogic<TaskViewEvent> logic) {
        this.logic = logic;
    }

    @Override
    public void onResume() {
        super.onResume();

        logic.onViewEvent(
                new TaskViewEvent(
                        ON_START,
                        null
                )
        );
    }

    @Override
    public void showColorPickerSheet() {
        bs.setState(
                BottomSheetBehavior.STATE_EXPANDED
        );
    }

    @Override
    public void hideColorPickerSheet() {
        bs.setState(
                BottomSheetBehavior.STATE_HIDDEN
        );
    }

    @Override
    public void setName(String name) {
        nameField.setText(name);
    }

    @Override
    public String getName() {
        return nameField.getText().toString();
    }

    @Override
    public void setIconSelection(ICON icon) {
        selectedIcon.setImageResource(
                IconUtility.getResIdFromEnum(requireContext(), icon)
        );
    }

    @Override
    public void setSelectedSpinnerItem(int position) {
        iconSpinner.setSelection(position, false);
    }

    @Override
    public void setButtonColor(COLOR c) {
        //Lol, this is all actually necessary to color the button
        ColorStateList colorList =
                ColorStateList.valueOf(

                        ColorUtility.getColorResId(
                                requireContext(),
                                c
                        )
                );

        selectColorButton.setBackgroundTintList(
                colorList
        );
    }


    @Override
    public void goToTaskListActivity() {
        startActivity(
                new Intent(
                        requireActivity(),
                        TaskListActivity.class
                )
        );
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onColorSelected(COLOR color) {
        logic.onViewEvent(
                new TaskViewEvent(
                        ON_COLOR_SELECTED,
                        color
                )
        );
    }
}
