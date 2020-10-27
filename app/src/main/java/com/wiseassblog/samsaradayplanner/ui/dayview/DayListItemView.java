package com.wiseassblog.samsaradayplanner.ui.dayview;

import androidx.annotation.IntegerRes;
import androidx.annotation.VisibleForTesting;

import java.util.List;

/**
 * In order to isolate the logic of generating appropriate data for the view and the adapter,
 * I will create a special kind of ViewModel which will contain the data in a form which simplifies
 * the View binding for the Adapter.
 * <p>
 * <p>
 * <p>
 * <p>
 * See the DayListViewItemMaker class for more details
 */
public
class DayListItemView {
    private final String hourBlockText;

    @IntegerRes
    private final int[] iconResId;

    @IntegerRes
    private final int[] backgroundResId;

    private final String[] taskNames;

    private final LIST_ITEM_TYPE type;

    public String getHourBlockText() {
        return hourBlockText;
    }

    public int[] getIconResId() {
        return iconResId;
    }

    public int[] getBackgroundResId() {
        return backgroundResId;
    }

    public String[] getTaskNames() {
        return taskNames;
    }

    public LIST_ITEM_TYPE getType() {
        return type;
    }

    public DayListItemView(String hourBlockText, int[] iconResId, int[] backgroundResId,  String[] taskNames, LIST_ITEM_TYPE type) {
        this.hourBlockText = hourBlockText;
        this.backgroundResId = backgroundResId;
        this.iconResId = iconResId;
        this.taskNames = taskNames;
        this.type = type;
    }
}
