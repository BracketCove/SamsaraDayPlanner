package com.wiseassblog.samsaradayplanner.persistence

import android.util.Log
import com.wiseassblog.samsaradayplanner.ApplicationExecutors
import com.wiseassblog.samsaradayplanner.domain.ITaskStorage
import com.wiseassblog.samsaradayplanner.domain.Task
import com.wiseassblog.samsaradayplanner.domain.Tasks
import java.io.*

class LocalTaskStorageImpl(
    fileStorageDirectory: String?,
    private val exec: ApplicationExecutors
) : ITaskStorage {
    private val pathToStorageFile: File

    /**
     * @return
     * @throws IOException
     */
    @Throws(Exception::class)
    private fun loadTasks(): Tasks {
        var tasks: Tasks
        try {
            val fileInputStream = FileInputStream(pathToStorageFile)
            val objectInputStream = ObjectInputStream(fileInputStream)
            tasks = objectInputStream.readObject() as Tasks
            objectInputStream.close()
        } catch (e: FileNotFoundException) {
            tasks = preloadData()
        }
        return tasks
    }

    @Throws(Exception::class)
    private fun preloadData(): Tasks {
        val tasks = PreloadData.getPreloadedTasks()
        val fileOutputStream = FileOutputStream(pathToStorageFile)
        val objectOutputStream = ObjectOutputStream(fileOutputStream)
        objectOutputStream.writeObject(tasks)
        objectOutputStream.close()
        return tasks
    }

    /**
     * There are benefits and drawbacks to file storage. I would not use this approach if I expected
     * a large number of entries to be stored, since we must pull the entire data set just to
     * update a single item.
     *
     * @param task
     * @throws IOException
     */
    @Throws(Exception::class)
    private fun updateTaskInStorage(task: Task) {
        try {
            //retrieve current persisted state
            val tasks = loadTasks().get()

            //update the appropriate task
            for (index in tasks.indices) {
                if (tasks[index].taskId == task.taskId) tasks[index] = task
            }
            val fileOutputStream = FileOutputStream(pathToStorageFile)
            val objectOutputStream = ObjectOutputStream(fileOutputStream)
            objectOutputStream.writeObject(Tasks(tasks))
            objectOutputStream.close()
        } catch (e: IOException) {
            throw IOException("Unable to access Game Data")
        }
    }

    companion object {
        /**
         * The name of the file which will be stored in the Application specific storage directory
         */
        private const val FILE_NAME = "task.txt"
    }

    /**
     * Expected to be a File obtained by calling the command Context.getFilesDir()
     *
     * @param fileStorageDirectory
     */
    init {
        pathToStorageFile = File(fileStorageDirectory, FILE_NAME)
    }

    override fun getTasks(onSuccess: (Tasks) -> Unit, onError: (Exception) -> Unit) {
        exec.background.execute {
            var data: Any
            try {
                data = loadTasks()
            } catch (e: Exception) {
                data = e
                Log.d("STORAGE", Log.getStackTraceString(e))
            }
            val finalData = data
            exec.mainThread.execute {
                if (finalData is Tasks) onSuccess(
                    finalData
                ) else onError(
                    finalData as Exception
                )
            }
        }
    }

    override fun getTask(taskId: Int, onSuccess: (Task) -> Unit, onError: (Exception) -> Unit) {
        exec.background.execute {
            val data: Any?
            data = try {
                val tasks = loadTasks()
                tasks.getTaskById(taskId)
            } catch (e: Exception) {
                e
            }
            exec.mainThread.execute {
                if (data is Task) onSuccess.invoke(
                    data as Task
                ) else onError(
                    data as Exception
                )
            }
        }
    }

    override fun updateTask(task: Task, onSuccess: (Unit) -> Unit, onError: (Exception) -> Unit) {
        exec.background.execute {
            var exceptionOccurred = false
            try {
                updateTaskInStorage(task)
            } catch (e: Exception) {
                exceptionOccurred = true
            }
            val finalExceptionOccurred = exceptionOccurred
            exec.mainThread.execute {
                if (finalExceptionOccurred) onError.invoke(
                    Exception()
                ) else onSuccess.invoke(Unit)
            }
        }
    }
}