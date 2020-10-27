package com.wiseassblog.samsaradayplanner.domain;

import com.wiseassblog.samsaradayplanner.domain.constants.HOUR_MODE;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Question: Why Serializable instead of Parcelable; isn't that less efficient?
 * Answer: Parcelable is designed for IPC (inter-process communication), not for persistent file
 * storage. Since I am storing the object in a file instead for transferring it, and the docs
 * do not recommend Parcelable for persistent storage, I use Serializable instead.
 *
 * https://developer.android.com/reference/android/os/Parcel
 * "This class (and the corresponding Parcelable API for placing arbitrary objects into a Parcel) is designed as a high-performance IPC transport. As such, it is not appropriate to place any Parcel data in to persistent storage: changes in the underlying implementation of any of the data in the Parcel can render older data unreadable."
 */
public class Day implements Serializable {
    //We hide the data itself for security and protection
    private final HOUR_MODE mode;
    private final Hour[] hours;

    public Day(HOUR_MODE mode, Hour[] hours) {
        this.mode = mode;
        this.hours = hours;

        List<String> list = new ArrayList();
    }

    //we provide a way of accessing the data which is secure
    public HOUR_MODE getMode() {
        return mode;
    }

    public Hour[] getHours() {
        return hours;
    }
}
