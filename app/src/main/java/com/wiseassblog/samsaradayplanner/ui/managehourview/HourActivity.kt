package com.wiseassblog.samsaradayplanner.ui.managehourview

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.wiseassblog.samsaradayplanner.R
import com.wiseassblog.samsaradayplanner.SamsaraApp
import com.wiseassblog.samsaradayplanner.domain.constants.Extras
import com.wiseassblog.samsaradayplanner.domain.constants.Messages.GENERIC_ERROR_MESSAGE
import com.wiseassblog.samsaradayplanner.ui.dayview.DayActivity
import com.wiseassblog.samsaradayplanner.ui.managehourview.buildlogic.buildComponents

class HourActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_hour)
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
        val hourInteger = i!!.getIntExtra(
            Extras.EXTRA_HOUR_INTEGER,
            0
        )
        var fragment = this.supportFragmentManager.findFragmentByTag(
            HOUR_VIEW
        ) as HourView?
        if (fragment == null) {
            fragment = HourView.newInstance()
        }
        this.supportFragmentManager.beginTransaction().replace(
            R.id.root_manage_hour_view,
            fragment!!,
            HOUR_VIEW
        ).commit()

        buildComponents(
            hourInteger,
            fragment,
            (application as SamsaraApp).serviceLocator
        )
    }

    companion object {
        private const val HOUR_VIEW = "HOUR_VIEW"
    }
}