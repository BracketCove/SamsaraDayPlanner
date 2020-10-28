package com.wiseassblog.samsaradayplanner

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class ApplicationExecutors {
    val background: Executor
    val mainThread: Executor

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }

    init {
        background = Executors.newSingleThreadExecutor()
        mainThread = MainThreadExecutor()
    }
}