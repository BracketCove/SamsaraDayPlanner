package com.wiseassblog.samsaradayplanner.ui.tasklistview;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.wiseassblog.samsaradayplanner.R;
import com.wiseassblog.samsaradayplanner.SamsaraApp;
import com.wiseassblog.samsaradayplanner.ui.tasklistview.buildlogic.TaskListViewInjector;

public class TaskListActivity extends AppCompatActivity {

    private static final String TASK_LIST_VIEW = "TASK_LIST_VIEW";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        TaskListView fragment = (TaskListView) this.getSupportFragmentManager().findFragmentByTag(
                TASK_LIST_VIEW
        );

        if (fragment == null) {
            fragment = TaskListView.newInstance();
        }

        this.getSupportFragmentManager()
                .beginTransaction()
                .replace(
                R.id.root_task_list_view,
                fragment,
                TASK_LIST_VIEW
        ).commit();


        TaskListViewInjector.build(
                fragment,
                ((SamsaraApp) getApplication()).getServiceLocator()
        );
    }
}