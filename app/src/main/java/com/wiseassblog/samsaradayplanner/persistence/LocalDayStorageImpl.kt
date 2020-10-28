package com.wiseassblog.samsaradayplanner.persistence

import android.util.Log
import com.wiseassblog.samsaradayplanner.ApplicationExecutors
import com.wiseassblog.samsaradayplanner.domain.Day
import com.wiseassblog.samsaradayplanner.domain.Hour
import com.wiseassblog.samsaradayplanner.domain.IDayStorage
import com.wiseassblog.samsaradayplanner.domain.constants.HOUR_MODE
import java.io.*

/**
 * Problem:
 * In order to get the appropriate files storage directory, I need to use Context.getStorageDir()
 * However,
 */
class LocalDayStorageImpl(fileStorageDirectory: String, exec: ApplicationExecutors) : IDayStorage {
    private val exec: ApplicationExecutors
    private val pathToStorageFile: File

    //assume this is the first time the user has opened the app
    @get:Throws(Exception::class)
    private val dayFromStorage: Day
        private get() {
            var day: Day
            try {
                val fileInputStream = FileInputStream(pathToStorageFile)
                val objectInputStream = ObjectInputStream(fileInputStream)
                day = objectInputStream.readObject() as Day
                objectInputStream.close()
            } catch (fileNotFoundException: FileNotFoundException) {
                //assume this is the first time the user has opened the app
                day = preloadData()
            }
            return day
        }

    /**
     * Note: we are already on the background thread
     *
     * @return
     */
    @Throws(Exception::class)
    private fun preloadData(): Day {
        val day = PreloadData.preloadedDay
        updateDayToStorage(day)
        return day
    }

    @Throws(Exception::class)
    private fun updateDayToStorage(day: Day) {
        val fileOutputStream = FileOutputStream(pathToStorageFile)
        val objectOutputStream = ObjectOutputStream(fileOutputStream)
        objectOutputStream.writeObject(day)
        objectOutputStream.close()
    }

    companion object {
        /**
         * The name of the file which will be stored in the Application specific storage directory
         */
        private const val FILE_NAME = "day.txt"
    }

    /**
     * Expected to be a File obtained by calling the command Context.getFilesDir()
     *
     * @param fileStorageDirectory
     */
    init {
        pathToStorageFile = File(fileStorageDirectory, FILE_NAME)
        this.exec = exec
    }

    override fun getDay(onSuccess: (Day) -> Unit, onError: (Exception) -> Unit) {
        exec.background.execute {

            //this is where we do background stuff
            var data: Any
            try {
                data = dayFromStorage
            } catch (e: Exception) {
                data = e
                Log.d("STORAGE", Log.getStackTraceString(e))
            }
            Log.d("CURRENT_THREAD", java.lang.Long.toString(Thread.currentThread().id))
            val finalData = data
            exec.mainThread.execute {
                Log.d("CURRENT_THREAD", java.lang.Long.toString(Thread.currentThread().id))
                if (finalData is Day) onSuccess.invoke(
                    finalData
                ) else onError(
                    (finalData as Exception)
                )
            }
        }
    }

    override fun updateDay(day: Day, onSuccess: (Unit) -> Unit, onError: (Exception) -> Unit) {
        exec.background.execute {
            var exception = false
            try {
                updateDayToStorage(day)
            } catch (e: Exception) {
                exception = true
            }
            val exceptionHasOccurred = exception
            exec.mainThread.execute {
                if (exceptionHasOccurred) onError.invoke(Exception())
                else onSuccess.invoke(Unit)
            }
        }
    }

    override fun getHourWithMode(
        hour: Int,
        onSuccess: (Hour, HOUR_MODE) -> Unit,
        onError: (Exception) -> Unit
    ) {
        exec.background.execute {
            var data: Any
            var mode: HOUR_MODE
            try {
                val day = dayFromStorage
                data = day.hours[hour]
                mode = day.mode
            } catch (e: Exception) {
                data = e
                //to avoid npe
                mode = HOUR_MODE.TWELVE_HOUR
            }
            val finalData = data
            val finalMode = mode
            exec.mainThread.execute {
                if (finalData is Hour) onSuccess.invoke(
                        finalData,
                        finalMode
                ) else onError.invoke(
                    (finalData as Exception)
                )
            }
        }
    }

    override fun updateHour(hour: Hour, onSuccess: (Unit) -> Unit, onError: (Exception) -> Unit) {
        exec.background.execute {
            var exceptionOccured = false
            try {
                val day = dayFromStorage
                day.hours[hour.hourInteger] = hour
                updateDayToStorage(day)
            } catch (e: Exception) {
                exceptionOccured = true
            }
            val exceptionHasOccurred = exceptionOccured
            exec.mainThread.execute {
                if (exceptionHasOccurred) onError.invoke(Exception())
                else onSuccess.invoke(
                    Unit
                )
            }
        }
    }
}