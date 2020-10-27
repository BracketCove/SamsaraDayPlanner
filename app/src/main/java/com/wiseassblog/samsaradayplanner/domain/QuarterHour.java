package com.wiseassblog.samsaradayplanner.domain;

import com.wiseassblog.samsaradayplanner.domain.constants.COLOR;
import com.wiseassblog.samsaradayplanner.domain.constants.ICON;
import com.wiseassblog.samsaradayplanner.domain.constants.QUARTER;

import java.io.Serializable;

/**
 * A virtual representation of a quarter of an hour; i.e. a 15  minute interval.
 *
 *
 *
 */
public class QuarterHour implements Serializable {
    private final int taskId;
    private final QUARTER quarter;
    private final boolean isActive;

    public QuarterHour(int taskId, QUARTER quarter, boolean isActive) {
        this.taskId = taskId;
        this.quarter = quarter;
        this.isActive = isActive;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public int getTaskId() {
        return taskId;
    }

    public QUARTER getQuarter() {
        return quarter;
    }
}
