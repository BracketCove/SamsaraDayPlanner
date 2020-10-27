package com.wiseassblog.samsaradayplanner.common;

import com.wiseassblog.samsaradayplanner.domain.Hour;
import com.wiseassblog.samsaradayplanner.domain.constants.HOUR_MODE;
import com.wiseassblog.samsaradayplanner.domain.constants.QUARTER;

public class TimeFormatUtility {
    public static String getHourBlockText(int hour, HOUR_MODE mode) {
        String suffix = "";
        String hourText = Integer.toString(hour);

        //Twelve hour clock is stupid
        if (mode == HOUR_MODE.TWELVE_HOUR) {
            if (hour == 0) {
                suffix = "AM";
                hourText = "12";
            }
            if (hour == 12) suffix = "PM";
            else if (hour > 12) {
                //hour is 13-23 (1PM - 11PM)
                suffix = "PM";
                hourText = Integer.toString(hour - 12);
            } else {
                //hour is 01-11 (1PM - 11PM)
                suffix = "AM";
            }
        } else if (mode == HOUR_MODE.TWENTY_FOUR_HOUR) {
            //else if included for legibility, in case you were wondering
            if (hour == 0) {
                //military clock uses 00, not 0 for 12:00AM equivalent
                hourText = "00";
            }
            //either way suffix is the same
            suffix = ":00";
        }

        return hourText + suffix;
    }

    public static String getHourToggleViewFormattedText(QUARTER q, int hour, HOUR_MODE mode) {
        String suffix = "";
        String quarterText = getQuarterText(q);
        String hourText = Integer.toString(hour);

        //Twelve hour clock is stupid
        if (mode == HOUR_MODE.TWELVE_HOUR) {
            if (hour == 0) {
                suffix = "AM";
                hourText = "12";
            } else if  (hour == 12) suffix = "PM";
            else if (hour > 12) {
                //hour is 13-23 (1PM - 11PM)
                suffix = "PM";
                hourText = Integer.toString(hour - 12);
            } else {
                //hour is 01-11 (1PM - 11PM)
                suffix = "AM";
            }
        } else if (mode == HOUR_MODE.TWENTY_FOUR_HOUR) {
            //else if included for legibility, in case you were wondering
            if (hour == 0) {
                //military clock uses 00, not 0 for 12:00AM equivalent
                hourText = "00";
            }
        }

        return hourText + quarterText + suffix;
    }

    private static String getQuarterText(QUARTER q) {
        switch (q) {
            case FIFTEEN: return ":15";
            case THIRTY: return ":30";
            case FOURTY_FIVE: return ":45";
            default: return ":00";
        }
    }
}
