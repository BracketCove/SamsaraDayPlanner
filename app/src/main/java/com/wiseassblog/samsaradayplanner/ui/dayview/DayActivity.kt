package com.wiseassblog.samsaradayplanner.ui.dayview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wiseassblog.samsaradayplanner.R
import com.wiseassblog.samsaradayplanner.ui.dayview.DayView
import com.wiseassblog.samsaradayplanner.ui.dayview.DayActivity
import com.wiseassblog.samsaradayplanner.ui.dayview.buildlogic.DayViewInjector
import com.wiseassblog.samsaradayplanner.SamsaraApp

//Feature level container
class DayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day)
        var fragment = this.supportFragmentManager.findFragmentByTag(
            DAY_VIEW
        ) as DayView?
        if (fragment == null) {
            fragment = DayView.newInstance()
        }
        this.supportFragmentManager.beginTransaction().replace(
            R.id.root_day_view,
            fragment!!,
            DAY_VIEW
        ).commit()
        DayViewInjector.build(fragment, (application as SamsaraApp).serviceLocator)
    }

    companion object {
        private const val DAY_VIEW = "DAY_VIEW"
    }
}