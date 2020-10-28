package com.wiseassblog.samsaradayplanner

import android.content.Context
import com.wiseassblog.samsaradayplanner.domain.IDayStorage
import com.wiseassblog.samsaradayplanner.domain.ITaskStorage
import com.wiseassblog.samsaradayplanner.persistence.LocalDayStorageImpl
import com.wiseassblog.samsaradayplanner.persistence.LocalTaskStorageImpl

class StorageServiceLocator(context: Context) {
    val dayStorageImpl: IDayStorage
    val taskStorageImpl: ITaskStorage

    /**
     * Why toString? By passing the String instead of context, I can keep the Android code out of
     * my backend components. This class inevitably must interact with Context, so I can work with
     * it here.
     * @param context
     */
    init {
        val exec = ApplicationExecutors()
        dayStorageImpl = LocalDayStorageImpl(
            context.filesDir.path,
            exec
        )
        taskStorageImpl = LocalTaskStorageImpl(
            context.filesDir.path,
            exec
        )
    }
}