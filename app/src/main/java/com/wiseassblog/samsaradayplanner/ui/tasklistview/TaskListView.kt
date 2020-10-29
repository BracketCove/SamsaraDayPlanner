package com.wiseassblog.samsaradayplanner.ui.tasklistview

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.GridView
import android.widget.ImageButton
import android.widget.Toast
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.wiseassblog.samsaradayplanner.R
import com.wiseassblog.samsaradayplanner.common.BaseViewLogic
import com.wiseassblog.samsaradayplanner.domain.Tasks
import com.wiseassblog.samsaradayplanner.domain.constants.Extras
import com.wiseassblog.samsaradayplanner.ui.dayview.DayActivity
import com.wiseassblog.samsaradayplanner.ui.managetaskview.TaskActivity

class TaskListView : Fragment(), ITaskListViewContract.View {
    private var logic: BaseViewLogic<TasksListViewEvent>? = null
    private lateinit var taskGrid: GridView

    fun setLogic(logic: BaseViewLogic<TasksListViewEvent>) {
        this.logic = logic
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tasks, container, false)
    }

    override fun onStart() {
        super.onStart()
        requireView().findViewById<ComposeView>(R.id.tlb_tasks).setContent {
            TaskListViewToolbar{
                logic?.onViewEvent(
                    TasksListViewEvent.OnBackPressed
                )
            }
        }

        taskGrid = requireView().findViewById(R.id.gdl_list_item_task)
    }

    override fun onResume() {
        super.onResume()
        logic?.onViewEvent(TasksListViewEvent.OnStart)
    }

    override fun setTasks(tasks: Tasks) {
        val adapter = TaskGridItemViewAdapter(
            tasks.get()
        )
        taskGrid.adapter = adapter
        taskGrid.onItemClickListener =
            OnItemClickListener { adapterView: AdapterView<*>?, clickView: View?, position: Int, id: Long ->
                logic?.onViewEvent(
                    TasksListViewEvent.OnListItemSelected(position)
                )
            }
    }

    override fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun navigateToDayView() {
        startActivity(
            Intent(
                requireActivity(),
                DayActivity::class.java
            )
        )
    }

    override fun navigateToTaskView(taskId: Int) {
        startActivity(
            Intent(
                requireActivity(),
                TaskActivity::class.java
            ).putExtra(
                Extras.EXTRA_TASK_ID,
                taskId
            )
        )
    }

    companion object {
        fun newInstance(): TaskListView {
            return TaskListView()
        }
    }
}