package com.wiseassblog.samsaradayplanner.persistence;

import android.util.Log;

import com.wiseassblog.samsaradayplanner.ApplicationExecutors;
import com.wiseassblog.samsaradayplanner.common.Continuation;
import com.wiseassblog.samsaradayplanner.domain.Day;
import com.wiseassblog.samsaradayplanner.domain.ITaskStorage;
import com.wiseassblog.samsaradayplanner.domain.Task;
import com.wiseassblog.samsaradayplanner.domain.Tasks;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class LocalTaskStorageImpl implements ITaskStorage {
    private ApplicationExecutors exec;
    private final File pathToStorageFile;

    /**
     * The name of the file which will be stored in the Application specific storage directory
     */
    private static final String FILE_NAME = "task.txt";

    /**
     * Expected to be a File obtained by calling the command Context.getFilesDir()
     *
     * @param fileStorageDirectory
     */
    public LocalTaskStorageImpl(String fileStorageDirectory,
                                ApplicationExecutors exec
    ) {
        this.exec = exec;
        this.pathToStorageFile = new File(fileStorageDirectory, FILE_NAME);
    }

    @Override
    public void getTasks(Continuation<Tasks> continuation) {
        exec.getBackground().execute(
                () -> {
                    Object data;

                    try {
                        data = loadTasks();
                    } catch (Exception e) {
                        data = e;
                        Log.d("STORAGE", Log.getStackTraceString(e));
                    }

                    final Object finalData = data;

                    exec.getMainThread().execute(
                            () -> {
                                if (finalData instanceof Tasks) continuation.onSuccess(
                                        (Tasks) finalData
                                );
                                else continuation.onException(
                                        (Exception) finalData
                                );

                            }
                    );
                });
    }

    /**
     * @return
     * @throws IOException
     */
    private Tasks loadTasks() throws Exception {
        Tasks tasks;

        try {
        FileInputStream fileInputStream =
                new FileInputStream(this.pathToStorageFile);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            tasks = (Tasks) objectInputStream.readObject();
            objectInputStream.close();
        } catch (FileNotFoundException e) {
            tasks = preloadData();
        }

        return tasks;
    }

    private Tasks preloadData() throws Exception {
        Tasks tasks = PreloadData.getPreloadedTasks();

        FileOutputStream fileOutputStream =
                new FileOutputStream(this.pathToStorageFile);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(tasks);
        objectOutputStream.close();

        return tasks;
    }


    @Override
    public void getTask(int taskId, Continuation<Task> continuation) {
        exec.getBackground().execute(
                () -> {
                    Object data;

                    try {
                        Tasks tasks = loadTasks();

                        data = tasks.getTaskById(taskId);

                    } catch (Exception e) {
                        data = e;
                    }

                    final Object finalData = data;

                    exec.getMainThread().execute(
                            () -> {
                                if (finalData instanceof Task) continuation.onSuccess(
                                        (Task) finalData
                                );
                                else continuation.onException(
                                        (Exception) finalData
                                );

                            }
                    );
                });
    }

    /**
     * Note: If the incoming task is succesfully written to storage, we return it as it can be
     * assumed to be consistent with what is in storage.
     *
     * @param task
     * @param continuation
     */
    @Override
    public void updateTask(Task task, Continuation<Void> continuation) {
        exec.getBackground().execute(
                () -> {
                    boolean exceptionOccurred = false;

                    try {
                        updateTaskInStorage(task);
                    } catch (Exception e) {
                        exceptionOccurred = true;
                    }

                    final boolean finalExceptionOccurred = exceptionOccurred;

                    exec.getMainThread().execute(
                            () -> {
                                if (finalExceptionOccurred) continuation.onException(
                                        new Exception()
                                );
                                else continuation.onSuccess(null);
                            }
                    );
                });
    }

    /**
     * There are benefits and drawbacks to file storage. I would not use this approach if I expected
     * a large number of entries to be stored, since we must pull the entire data set just to
     * update a single item.
     *
     * @param task
     * @throws IOException
     */
    private void updateTaskInStorage(Task task) throws Exception {
        try {
            //retrieve current persisted state
            Task[] tasks = loadTasks().get();

            //update the appropriate task
            for (int index = 0; index < tasks.length; index++) {
                if (tasks[index].getTaskId() == task.getTaskId()) tasks[index] = task;
            }

            FileOutputStream fileOutputStream =
                    new FileOutputStream(this.pathToStorageFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(new Tasks(tasks));
            objectOutputStream.close();
        } catch (IOException e) {
            throw new IOException("Unable to access Game Data");
        }
    }
}
