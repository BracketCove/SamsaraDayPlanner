package com.wiseassblog.samsaradayplanner.ui.managetaskview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.wiseassblog.samsaradayplanner.R;
import com.wiseassblog.samsaradayplanner.SamsaraApp;
import com.wiseassblog.samsaradayplanner.domain.constants.Extras;
import com.wiseassblog.samsaradayplanner.ui.dayview.DayActivity;
import com.wiseassblog.samsaradayplanner.ui.dayview.DayView;
import com.wiseassblog.samsaradayplanner.ui.dayview.buildlogic.DayViewInjector;
import com.wiseassblog.samsaradayplanner.ui.managetaskview.buildlogic.TaskViewInjector;

import static com.wiseassblog.samsaradayplanner.common.Messages.GENERIC_ERROR_MESSAGE;

public class TaskActivity extends AppCompatActivity {

    private static final String TASK_VIEW = "TASK_VIEW";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_task);


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

        int taskId = i.getIntExtra(
                Extras.EXTRA_TASK_ID,
                0
        );

        TaskView fragment = (TaskView) this.getSupportFragmentManager().findFragmentByTag(
                TASK_VIEW
        );

        if (fragment == null) {
            fragment = TaskView.newInstance();
        }

        this.getSupportFragmentManager().beginTransaction().replace(
                R.id.root_manage_task_view,
                fragment,
                TASK_VIEW
        ).commit();

        TaskViewInjector.build(
                fragment,
                ((SamsaraApp) getApplication()).getServiceLocator(),
                taskId
        );
    }
}