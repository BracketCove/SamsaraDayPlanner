package com.wiseassblog.samsaradayplanner.ui.managetaskview

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.wiseassblog.samsaradayplanner.R
import com.wiseassblog.samsaradayplanner.SamsaraApp
import com.wiseassblog.samsaradayplanner.domain.constants.Extras
import com.wiseassblog.samsaradayplanner.domain.constants.Messages.GENERIC_ERROR_MESSAGE
import com.wiseassblog.samsaradayplanner.ui.dayview.DayActivity
import com.wiseassblog.samsaradayplanner.ui.managetaskview.buildlogic.buildComponents

class TaskActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_task)
        val i = this.intent
        if (i == null) {
            Toast.makeText(
                this,
                GENERIC_ERROR_MESSAGE,
                Toast.LENGTH_SHORT
            ).show()
            startActivity(
                Intent(this, DayActivity::class.java)
            )
        }
        val taskId = i!!.getIntExtra(
            Extras.EXTRA_TASK_ID,
            0
        )
        var fragment = this.supportFragmentManager.findFragmentByTag(
            TASK_VIEW
        ) as TaskView?
        if (fragment == null) {
            fragment = TaskView.newInstance()
        }
        this.supportFragmentManager.beginTransaction().replace(
            R.id.root_manage_task_view,
            fragment!!,
            TASK_VIEW
        ).commit()

        buildComponents(
            fragment,
            (application as SamsaraApp).serviceLocator,
            taskId
        )
    }

    companion object {
        private const val TASK_VIEW = "TASK_VIEW"
    }
}