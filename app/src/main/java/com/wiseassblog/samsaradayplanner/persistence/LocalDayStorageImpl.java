package com.wiseassblog.samsaradayplanner.persistence;

import android.util.Log;

import com.wiseassblog.samsaradayplanner.ApplicationExecutors;
import com.wiseassblog.samsaradayplanner.common.Continuation;
import com.wiseassblog.samsaradayplanner.domain.Day;
import com.wiseassblog.samsaradayplanner.domain.Hour;
import com.wiseassblog.samsaradayplanner.domain.IDayStorage;
import com.wiseassblog.samsaradayplanner.domain.Tasks;
import com.wiseassblog.samsaradayplanner.domain.constants.HOUR_MODE;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Problem:
 * In order to get the appropriate files storage directory, I need to use Context.getStorageDir()
 * However,
 */
public class LocalDayStorageImpl implements IDayStorage {
    private ApplicationExecutors exec;
    private final File pathToStorageFile;

    /**
     * The name of the file which will be stored in the Application specific storage directory
     */
    private static final String FILE_NAME = "day.txt";


    /**
     * Expected to be a File obtained by calling the command Context.getFilesDir()
     *
     * @param fileStorageDirectory
     */
    public LocalDayStorageImpl(String fileStorageDirectory, ApplicationExecutors exec) {
        this.pathToStorageFile = new File(fileStorageDirectory, FILE_NAME);
        this.exec = exec;
    }


    private Day getDayFromStorage() throws Exception {
        Day day;
        try {
            FileInputStream fileInputStream =
                    new FileInputStream(this.pathToStorageFile);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            day = (Day) objectInputStream.readObject();
            objectInputStream.close();
        } catch (FileNotFoundException fileNotFoundException) {
            //assume this is the first time the user has opened the app
            day = preloadData();
        }

        return day;
    }

    /**
     * Note: we are already on the background thread
     *
     * @return
     */
    private Day preloadData() throws Exception {
        Day day = PreloadData.getPreloadedDay();
        updateDayToStorage(day);
        return day;
    }


    @Override
    public void getDay(Continuation<Day> continuation) {
        exec.getBackground().execute(
                () -> {
                    //this is where we do background stuff
                    Object data;
                    try {
                        data = getDayFromStorage();
                    } catch (Exception e) {
                        data = e;
                        Log.d("STORAGE", Log.getStackTraceString(e));
                    }

                    Log.d("CURRENT_THREAD", Long.toString(Thread.currentThread().getId()));

                    final Object finalData = data;

                    exec.getMainThread().execute(
                            () -> {

                                Log.d("CURRENT_THREAD", Long.toString(Thread.currentThread().getId()));

                                if (finalData instanceof Day) continuation.onSuccess(
                                        (Day) finalData
                                );

                                else continuation.onException(
                                        (Exception) finalData
                                );
                            }
                    );
                }
        );
    }

    /**
     * Writes a day to storage
     *
     * @param day
     * @param continuation
     */
    @Override
    public void updateDay(Day day, Continuation<Void> continuation) {
        exec.getBackground().execute(
                () -> {

                    boolean exception = false;
                    try {
                        updateDayToStorage(day);
                    } catch (Exception e) {
                        exception = true;
                    }

                    final boolean exceptionHasOccurred = exception;

                    exec.getMainThread().execute(
                            () -> {
                                if (exceptionHasOccurred) continuation.onException(new Exception());
                                else continuation.onSuccess(null);
                            }
                    );
                });


    }

    private void updateDayToStorage(Day day) throws Exception {
        FileOutputStream fileOutputStream =
                new FileOutputStream(this.pathToStorageFile);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(day);
        objectOutputStream.close();
    }


    @Override
    public void getHourWithMode(int hour, Continuation<Object[]> continuation) {
        exec.getBackground().execute(
                () -> {
                    //TODO this code is pretty ugly; can definitely be improved
                    //object so it can cast to Error if necessary
                    Object data;
                    HOUR_MODE mode;
                    try {
                        Day day = getDayFromStorage();
                        data = day.getHours()[hour];
                        mode = day.getMode();
                    } catch (Exception e) {
                        data = e;
                        //lol, silly java
                        mode = null;
                    }

                    final Object finalData = data;
                    final HOUR_MODE finalMode = mode;
                    exec.getMainThread().execute(
                            () -> {
                                if (finalData instanceof Hour) continuation.onSuccess(
                                        new Object[]{
                                                finalData,
                                                finalMode
                                        }

                                );

                                else continuation.onException(
                                        (Exception) finalData
                                );
                            }
                    );
                });

    }

    @Override
    public void updateHour(Hour hour, Continuation<Void> continuation) {
        exec.getBackground().execute(
                () -> {

                    boolean exceptionOccured = false;

                    try {
                        Day day = getDayFromStorage();

                        day.getHours()[hour.getHourInteger()] = hour;

                        updateDayToStorage(day);
                    } catch (Exception e) {
                        exceptionOccured = true;
                    }

                    final boolean exceptionHasOccurred = exceptionOccured;

                    exec.getMainThread().execute(
                            () -> {
                                if (exceptionHasOccurred) continuation.onException(new Exception());
                                else continuation.onSuccess(null);
                            }
                    );
                });

    }
}
