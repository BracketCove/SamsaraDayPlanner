package com.wiseassblog.samsaradayplanner;

import android.content.Context;

import com.wiseassblog.samsaradayplanner.domain.IDayStorage;
import com.wiseassblog.samsaradayplanner.domain.ITaskStorage;
import com.wiseassblog.samsaradayplanner.persistence.LocalDayStorageImpl;
import com.wiseassblog.samsaradayplanner.persistence.LocalTaskStorageImpl;

public class StorageServiceLocator {

    private final IDayStorage dayStorageImpl;
    private final ITaskStorage taskStorageImpl;

    /**
     * Why toString? By passing the String instead of context, I can keep the Android code out of
     * my backend components. This class inevitably must interact with Context, so I can work with
     * it here.
     * @param context
     */
    public StorageServiceLocator(Context context) {
        ApplicationExecutors exec = new ApplicationExecutors();

        dayStorageImpl = new LocalDayStorageImpl(
                context.getFilesDir().getPath(),
                exec
        );
        taskStorageImpl = new LocalTaskStorageImpl(
                context.getFilesDir().getPath(),
                exec
        );
    }

    public IDayStorage getDayStorageImpl() {
        return dayStorageImpl;
    }

    public ITaskStorage getTaskStorageImpl() {
        return taskStorageImpl;
    }
}
