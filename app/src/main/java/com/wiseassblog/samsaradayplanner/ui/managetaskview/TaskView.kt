package com.wiseassblog.samsaradayplanner.ui.managetaskview

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.wiseassblog.samsaradayplanner.R
import com.wiseassblog.samsaradayplanner.common.BaseViewLogic
import com.wiseassblog.samsaradayplanner.common.getColorResId
import com.wiseassblog.samsaradayplanner.common.getResIdFromEnum
import com.wiseassblog.samsaradayplanner.domain.constants.COLOR
import com.wiseassblog.samsaradayplanner.domain.constants.ICON
import com.wiseassblog.samsaradayplanner.ui.managetaskview.IconSpinnerItem.Companion.getItems
import com.wiseassblog.samsaradayplanner.ui.tasklistview.TaskListActivity

class TaskView : Fragment(), ITaskViewContract.View {
    private var logic: BaseViewLogic<TaskViewEvent>? = null
    private lateinit var doneButton: ImageButton
    private lateinit var selectColorButton: Button
    private lateinit var nameField: EditText
    private lateinit var selectedIcon: ImageView
    private lateinit var iconSpinner: Spinner
    private lateinit var bottomSheetView: ColorPickerView
    private lateinit var bs: BottomSheetBehavior<*>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_manage_task, container, false)
        selectColorButton = view.findViewById(R.id.btn_select_colour)
        selectColorButton.setOnClickListener({
            logic?.onViewEvent(
                TaskViewEvent.OnColorButtonClick
            )
        }
        )
        doneButton = view.findViewById(R.id.tlb_icon)
        doneButton.setOnClickListener({
                logic?.onViewEvent(
                    TaskViewEvent.OnDoneClick
                )
            }
        )
        selectedIcon = view.findViewById(R.id.imv_icon_selection)
        iconSpinner = view.findViewById(R.id.spn_task_icon)
        val adapter = IconSpinnerAdapter(
            requireContext(),
            getItems(requireContext())
        )
        iconSpinner.setAdapter(adapter)
        iconSpinner.setOnItemSelectedListener(
            object : OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>?,
                    view: View,
                    position: Int,
                    l: Long
                ) {
                    logic?.onViewEvent(
                        TaskViewEvent.OnIconSelected(ICON.values()[position])
                    )
                }

                override fun onNothingSelected(adapterView: AdapterView<*>?) {}
            }
        )
        nameField = view.findViewById(R.id.edt_task_name)
        bottomSheetView = view.findViewById(R.id.bts_select_color)
        bottomSheetView.setCallback(::onColorSelected)
        bs = BottomSheetBehavior.from(bottomSheetView)
        bs.setHideable(true)
        bs.setState(BottomSheetBehavior.STATE_HIDDEN)
        return view
    }

    fun setLogic(logic: BaseViewLogic<TaskViewEvent>) {
        this.logic = logic
    }

    override fun onResume() {
        super.onResume()
        logic?.onViewEvent(
                TaskViewEvent.OnStart
        )
    }

    override fun showColorPickerSheet() {
        bs!!.state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun hideColorPickerSheet() {
        bs!!.state = BottomSheetBehavior.STATE_HIDDEN
    }

    override var name: String
        get() = nameField.text.toString()
        set(name) {
            nameField.setText(name)
        }

    override fun setIconSelection(icon: ICON) {
        selectedIcon!!.setImageResource(
            getResIdFromEnum(requireContext(), icon)
        )
    }

    override fun setSelectedSpinnerItem(position: Int) {
        iconSpinner!!.setSelection(position, false)
    }

    override fun setButtonColor(c: COLOR) {
        //Lol, this is all actually necessary to color the button
        val colorList = ColorStateList.valueOf(
            getColorResId(
                requireContext(),
                c
            )
        )
        selectColorButton!!.backgroundTintList = colorList
    }

    override fun goToTaskListActivity() {
        startActivity(
            Intent(
                requireActivity(),
                TaskListActivity::class.java
            )
        )
    }

    override fun showMessage(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    fun onColorSelected(color: COLOR) {
        logic?.onViewEvent(
            TaskViewEvent.OnColorSelected(color)
        )
    }

    companion object {
        fun newInstance(): TaskView {
            return TaskView()
        }
    }
}