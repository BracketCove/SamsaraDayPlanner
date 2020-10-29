package com.wiseassblog.samsaradayplanner.ui.managehourview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.wiseassblog.samsaradayplanner.R
import com.wiseassblog.samsaradayplanner.domain.constants.QUARTER

class HourToggleView(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {
    private lateinit var quarterHourIsActive: Switch
    private lateinit var timeLabel: TextView
    private lateinit var selectTask: Spinner
    private lateinit var bottomBorder: View

    lateinit var quarter: QUARTER

    var onQuarterToggle: ((quarter: QUARTER, activate: Boolean) -> Unit)? = null

    fun setToggleState(quarter: QUARTER, isActive: Boolean) {
        if (quarter != QUARTER.ZERO) {
            quarterHourIsActive!!.isChecked = isActive
            setTimeLabelColor(isActive)
        } else {
            quarterHourIsActive!!.visibility = INVISIBLE
            setTimeLabelColor(true)
        }
        if (quarter == QUARTER.FOURTY_FIVE) hideBottomDivider()
    }

    private fun setTimeLabelColor(isAccentColor: Boolean) {
        if (isAccentColor) timeLabel!!.setTextColor(
            ContextCompat.getColor(
                context,
                R.color.colorAccent
            )
        ) else {
            timeLabel!!.setTextColor(
                ContextCompat.getColor(
                    context,
                    android.R.color.white
                )
            )
        }
    }

    private fun build() {
        inflate(context, R.layout.view_quarter_hour_toggle, this)
        quarterHourIsActive = findViewById(R.id.swi_activate_quarter_hour)
        quarterHourIsActive.setOnCheckedChangeListener(
            { view: CompoundButton?, isChecked: Boolean ->
                onQuarterToggle?.let { it(quarter, isChecked) }
                setTimeLabelColor(isChecked)
            }
        )
        timeLabel = findViewById(R.id.lbl_quarter_hour)
        selectTask = findViewById(R.id.spn_quarter_hour)
        bottomBorder = findViewById(R.id.view_bottom_border)
    }

    fun setCallback(
        onQuarterToggle: (quarter: QUARTER, activate: Boolean) -> Unit,
        onTaskSelected: (quarter: QUARTER, position: Int) -> Unit
    ) {
        this.onQuarterToggle = onQuarterToggle


        selectTask.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                onTaskSelected.invoke(
                    quarter,
                    position
                )
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
    }

    /**
     * @param text Expected to be a properly formatted time display like 1:00PM or 13:00
     */
    fun setTimeLabel(text: String?) {
        timeLabel!!.text = text
    }

    fun hideBottomDivider() {
        bottomBorder!!.visibility = INVISIBLE
    }

    fun createTaskAdapter(tasks: Array<String>, startingPosition: Int) {
        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, tasks)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        selectTask!!.adapter = adapter
        selectTask!!.setSelection(startingPosition, false)
    }

    init {
        build()
    }
}