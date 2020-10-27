package com.wiseassblog.samsaradayplanner.domain;

import com.wiseassblog.samsaradayplanner.common.Continuation;

/**
 * The facade pattern uses an abstraction (interface or abstract class) to hide the implementation
 * details of a sub-system, from the client (class) that use the sub-system
 *
 * We hide the implementation details of the back end, from the front end (client)
 */
public interface IDayStorage {

    public void getDay(Continuation<Day> continuation);

    public void updateDay(Day day, Continuation<Void> continuation);

    /**
     * Expect for onSuccess:
     * result[0] to be an Hour Type
     * result[1] to be an HOUR_MODE enum
     * @param hour
     * @param continuation
     */
    public void getHourWithMode(int hour, Continuation<Object[]> continuation);

    public void updateHour(Hour hour, Continuation<Void> continuation);
}
