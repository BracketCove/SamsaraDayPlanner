package com.wiseassblog.samsaradayplanner.ui.tasklistview;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.wiseassblog.samsaradayplanner.R;

class TaskGridItemView extends ConstraintLayout {
    private ImageView background, icon;
    private TextView taskName;

    public TaskGridItemView(@NonNull Context context) {
        super(context);

        build();
    }

    private void build() {
        inflate(getContext(), R.layout.list_item_task, this);

        background = findViewById(R.id.imv_background_color);
        icon = findViewById(R.id.imv_list_item_task);
        taskName = findViewById(R.id.lbl_task_name);
    }

    public void setListItemText(String text) {
        taskName.setText(text);
    }

    public void setListItemIcon(int resId) {
        icon.setImageResource(resId);
    }

    public void setListItemBackground(int resId) {
        background.setBackgroundColor(resId);
    }
}
