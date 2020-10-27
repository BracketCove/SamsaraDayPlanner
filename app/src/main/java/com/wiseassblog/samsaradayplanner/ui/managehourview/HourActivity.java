package com.wiseassblog.samsaradayplanner.ui.managehourview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.wiseassblog.samsaradayplanner.R;
import com.wiseassblog.samsaradayplanner.SamsaraApp;
import com.wiseassblog.samsaradayplanner.domain.constants.Extras;
import com.wiseassblog.samsaradayplanner.ui.dayview.DayActivity;
import com.wiseassblog.samsaradayplanner.ui.managehourview.buildlogic.HourViewInjector;
import com.wiseassblog.samsaradayplanner.ui.tasklistview.TaskListView;
import com.wiseassblog.samsaradayplanner.ui.tasklistview.buildlogic.TaskListViewInjector;

import static com.wiseassblog.samsaradayplanner.common.Messages.GENERIC_ERROR_MESSAGE;

public class HourActivity extends AppCompatActivity {

    private static final String HOUR_VIEW = "HOUR_VIEW";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_hour);

        final Intent i = this.getIntent();

        if (i == null) {
            Toast.makeText(this,
                    GENERIC_ERROR_MESSAGE,
                    Toast.LENGTH_SHORT
            ).show();

            startActivity(
                    new Intent(this, DayActivity.class)
            );
        }

        int hourInteger = i.getIntExtra(
                Extras.EXTRA_HOUR_INTEGER,
                0
        );

        HourView fragment = (HourView) this.getSupportFragmentManager().findFragmentByTag(
                HOUR_VIEW
        );

        if (fragment == null) {
            fragment = HourView.newInstance();
        }

        this.getSupportFragmentManager().beginTransaction().replace(
                R.id.root_manage_hour_view,
                fragment,
                HOUR_VIEW
        ).commit();


        HourViewInjector.build(
                hourInteger,
                fragment,
                ((SamsaraApp) getApplication()).getServiceLocator()
        );
    }
}