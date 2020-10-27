package com.wiseassblog.samsaradayplanner.domain;

import java.io.Serializable;

/**
 * Represents an hour of a given day. An hour is divided into four quarters (15 minute intervals)
 */
public class Hour implements Serializable {
    /**
     * NOTE: the first QuarterHour in quarters may never be inactive!
     * This is enforced in the UI but must be respected elsewhere
     */
    private final QuarterHour[] quarters;

    /**
     * One of:
     * Integers 0-23 where 0 is 12:00AM or 0:00, 23 is 11:00pm or 23:00hrs
     */
    private final int hourInteger;

    public Hour(QuarterHour[] quarters, int hourInteger) {
        this.quarters = quarters;
        this.hourInteger = hourInteger;
    }

    public QuarterHour[] getQuarters() {
        return quarters;
    }

    public int getHourInteger() {
        return hourInteger;
    }
}
