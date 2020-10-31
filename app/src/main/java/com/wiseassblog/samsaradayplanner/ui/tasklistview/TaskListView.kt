package com.wiseassblog.samsaradayplanner.ui.tasklistview

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.GridView
import android.widget.Toast
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.wiseassblog.samsaradayplanner.R
import com.wiseassblog.samsaradayplanner.common.BaseViewLogic
import com.wiseassblog.samsaradayplanner.common.Publisher
import com.wiseassblog.samsaradayplanner.common.Subscriber
import com.wiseassblog.samsaradayplanner.domain.Tasks
import com.wiseassblog.samsaradayplanner.domain.constants.Extras
import com.wiseassblog.samsaradayplanner.ui.dayview.DayActivity
import com.wiseassblog.samsaradayplanner.ui.managetaskview.TaskActivity

class TaskListView : Fragment(), ITaskListViewContract.View {
    private var logic: BaseViewLogic<TaskListViewEvent>? = null

    fun setLogic(logic: BaseViewLogic<TaskListViewEvent>) {
        this.logic = logic
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(context = requireContext())
    }

    override fun onResume() {
        super.onResume()
        logic?.onViewEvent(TaskListViewEvent.OnStart)
    }

    override fun setTasks(tasks: Tasks) {

        (requireView() as ComposeView).apply {
            setContent {
                TaskListScreen(
                    tasks = tasks,
                    backClickHandler = ::handleBackClick,
                    itemClickHandler = ::handleItemClick
                )
            }
        }
    }

    private fun handleBackClick(){
        logic?.onViewEvent(TaskListViewEvent.OnBackPressed)
    }

    private fun handleItemClick(taskId: Int) {
        logic?.onViewEvent(TaskListViewEvent.OnListItemSelected(taskId))
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