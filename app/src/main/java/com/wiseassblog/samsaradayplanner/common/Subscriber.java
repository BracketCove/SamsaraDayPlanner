package com.wiseassblog.samsaradayplanner.common;

public interface Subscriber<EVENT> {

    public void onUpdateString(EVENT e, String s);

    public void onUpdateInt(EVENT e, int i);

    public void onUpdateBool(EVENT e, boolean b);

    /**
     * Use with caution.
     */
    public void onUpdateObject(EVENT e, Object o);
}
