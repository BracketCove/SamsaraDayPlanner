package com.wiseassblog.samsaradayplanner.ui.managehourview

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.wiseassblog.samsaradayplanner.R
import com.wiseassblog.samsaradayplanner.common.BaseViewLogic
import com.wiseassblog.samsaradayplanner.domain.constants.QUARTER
import com.wiseassblog.samsaradayplanner.ui.dayview.DayActivity

class HourView : Fragment(), IHourContract.View {
    private var logic: BaseViewLogic<HourViewEvent>? = null
    private var first: HourToggleView? = null
    private var second: HourToggleView? = null
    private var third: HourToggleView? = null
    private var fourth: HourToggleView? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_hour_view, container, false)
        first = v.findViewById(R.id.vqht_one)
        second = v.findViewById(R.id.vqht_two)
        third = v.findViewById(R.id.vqht_three)
        fourth = v.findViewById(R.id.vqht_four)

        //toolbar action button
        v.findViewById<View>(R.id.tlb_icon).setOnClickListener {
            logic?.onViewEvent(
                HourViewEvent.OnDoneClick
            )
        }
        return v
    }

    fun onQuarterToggle(quarter: QUARTER, activate: Boolean) {
        logic?.onViewEvent(
            HourViewEvent.OnQuarterToggled( quarter,
                activate)
        )
    }

    fun onTaskSelected(quarter: QUARTER, position: Int) {
        logic?.onViewEvent(
            HourViewEvent.OnTaskSelected(
                quarter,
                position
            )
        )
    }

    /**
     * Signal onStart
     *
     * @param logic
     */
    fun setLogic(logic: BaseViewLogic<HourViewEvent>?) {
        this.logic = logic
    }

    override fun onResume() {
        super.onResume()
        logic?.onViewEvent(
            HourViewEvent.OnStart
        )
    }

    override fun setListeners() {
        first?.setCallback(::onQuarterToggle, ::onTaskSelected)
        second?.setCallback(::onQuarterToggle, ::onTaskSelected)
        third?.setCallback(::onQuarterToggle, ::onTaskSelected)
        fourth?.setCallback(::onQuarterToggle, ::onTaskSelected)
    }

    override fun setQuarterHourText(quarter: QUARTER, hour: String) {
        val toggle = findToggleView(quarter)
        toggle!!.setTimeLabel(hour)
    }

    private fun findToggleView(quarter: QUARTER): HourToggleView? {
        return when (quarter) {
            QUARTER.FIFTEEN -> second
            QUARTER.THIRTY -> third
            QUARTER.FOURTY_FIVE -> fourth
            else -> first
        }
    }

    override fun setQuarterHourAdapterData(
        quarter: QUARTER,
        taskNames: Array<String>,
        position: Int
    ) {
        val toggle = findToggleView(quarter)
        toggle!!.createTaskAdapter(taskNames, position)
    }

    override fun setQuarterHourActive(quarter: QUARTER, isActive: Boolean) {
        val toggle = findToggleView(quarter)
        toggle!!.setToggleState(quarter, isActive)
    }

    override fun navigateToDayView() {
        startActivity(
            Intent(
                requireContext(),
                DayActivity::class.java
            )
        )
    }

    override fun showMessage(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    /**
     * Ok, the thing is that we can determine which HourToggleView by position, but we still
     * need to set the field in each HourToggleView which holds the appropriate QUARTER
     * @param q
     */
    override fun setQuarterHour(q: QUARTER) {
        findToggleView(q)!!.quarter = q
    }

    companion object {
        fun newInstance(): HourView {
            return HourView()
        }
    }
}