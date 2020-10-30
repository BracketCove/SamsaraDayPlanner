package com.wiseassblog.samsaradayplanner.ui.dayview

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.wiseassblog.samsaradayplanner.R
import com.wiseassblog.samsaradayplanner.common.BaseViewLogic
import com.wiseassblog.samsaradayplanner.domain.Day
import com.wiseassblog.samsaradayplanner.domain.Tasks
import com.wiseassblog.samsaradayplanner.domain.constants.Extras
import com.wiseassblog.samsaradayplanner.ui.managehourview.HourActivity
import com.wiseassblog.samsaradayplanner.ui.tasklistview.TaskListActivity

/**
 * I did not bother using the Pub/Sub pattern here because ultimately the presentation logic is
 * properly decoupled via DayListItemViewMaker.java and the Adapter itself
 */
class DayView : Fragment(), IDayViewContract.View {
    private var logic: BaseViewLogic<DayViewEvent>? = null
    private var rec: RecyclerView? = null
    private var adapter: DayAdapter? = null

    fun setLogic(logic: BaseViewLogic<DayViewEvent>?) {
        this.logic = logic
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_day_view, container, false)
    }

    override fun onStart() {
        super.onStart()

        requireView().findViewById<ComposeView>(R.id.tlb_day_view).setContent {
            DayViewToolbar {
                logic?.onViewEvent(
                    DayViewEvent.OnManageTasksSelected
                )
            }
        }

        rec = requireView().findViewById(R.id.rec_day_list)
    }


    override fun onResume() {
        super.onResume()
        logic?.onViewEvent(
            DayViewEvent.OnStart
        )
    }

    override fun setData(day: Day, tasks: Tasks) {
        adapter = DayAdapter(
            DayListItemMaker.getItemList(
                requireContext(),
                day,
                tasks
            )
        ) { position: Int ->
            logic?.onViewEvent(
                DayViewEvent.OnHourSelected(position)
            )
        }
        rec!!.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        rec!!.adapter = null
    }

    override fun navigateToHourView(hourInteger: Int) {
        val i = Intent(
            requireContext(),
            HourActivity::class.java
        )
        i.putExtra(Extras.EXTRA_HOUR_INTEGER, hourInteger)
        startActivity(i)
    }

    override fun navigateToTasksView() {
        startActivity(
            Intent(requireContext(), TaskListActivity::class.java)
        )
    }

    override fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun restartFeature() {
        requireActivity().recreate()
        startActivity(
            Intent(
                requireActivity(),
                DayActivity::class.java
            )
        )
    }

    companion object {
        fun newInstance(): DayView {
            return DayView()
        }
    }
}