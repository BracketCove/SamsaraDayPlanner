package com.wiseassblog.samsaradayplanner.persistence;

import com.wiseassblog.samsaradayplanner.domain.Day;
import com.wiseassblog.samsaradayplanner.domain.Hour;
import com.wiseassblog.samsaradayplanner.domain.QuarterHour;
import com.wiseassblog.samsaradayplanner.domain.Task;
import com.wiseassblog.samsaradayplanner.domain.Tasks;
import com.wiseassblog.samsaradayplanner.domain.constants.COLOR;
import com.wiseassblog.samsaradayplanner.domain.constants.HOUR_MODE;
import com.wiseassblog.samsaradayplanner.domain.constants.ICON;
import com.wiseassblog.samsaradayplanner.domain.constants.QUARTER;

import static com.wiseassblog.samsaradayplanner.domain.constants.COLOR.*;
import static com.wiseassblog.samsaradayplanner.domain.constants.ICON.*;

/**
 * Question: What the hell is this class and why did I choose to hard code this data in Java?
 * The purpose of this class is to pre-load my storage mechanism (which is for now just using Java/
 * Android File storage) with the data that I want to preload.
 *
 * I could have placed this data in the assets folder as a JSON or XML file, or download it from
 * a remote server (in later iterations I will probably do that as it would allow for language
 * localization), but for the time being, adding the extra step when it ends up being deserialized
 * to Java objects anyways is basically just a waste of time for no net benefit to memory space as
 * far as I'm aware.
 *
 * Understand that THIS IS THE BETA VERSION.
 *
 * Class is left without access modifier to denote "package private"
 */
class PreloadData {
    /**
     * Returns the initial set of tasks which the user can work with. I allow the user to update
     * the names of these tasks as they see fit, and to assign various ICONs to the tasks from a
     * finite set of Icons, but the user is not allowed to add or remove tasks in this version of
     * the App.
     * @return
     */
    static Tasks getPreloadedTasks() {
        return new Tasks(
                new Task[]{
                        new Task(0, "Free Time", FREE_TIME, GREEN),
                        new Task(1, "Break", BREAK, DARK_BLUE),
                        new Task(2, "Study", STUDY, TEAL),
                        new Task(3, "Work", WORK, DARK_RED),
                        new Task(4, "Exercise", EXERCISE, BURNT_ORANGE),
                        new Task(5, "Meditate", MENTAL_CULTIVATION, LIGHT_BLUE),
                        new Task(6, "Eat", EAT, BROWN),
                        new Task(7, "Sleep", SLEEP, MAUVE),
                        new Task(8, "Shop", SHOP, DARK_LIME)
                }
        );
    }

    static Day getPreloadedDay() {
        return new Day(
                HOUR_MODE.TWELVE_HOUR,
                new Hour[]{
                        //sleep 22-6 hours (10pm-6am)
                        getHour(7,0),
                        getHour(7,1),
                        getHour(7,2),
                        getHour(7,3),
                        getHour(7,4),
                        getHour(7,5),
                        //Exercise half hour
                        //Mental Cultivation half hour
                        new Hour(
                                new QuarterHour[]{
                                        new QuarterHour(4, QUARTER.ZERO, true),
                                        new QuarterHour(4, QUARTER.FIFTEEN, false),
                                        new QuarterHour(5, QUARTER.THIRTY, true),
                                        new QuarterHour(5, QUARTER.FOURTY_FIVE, false)
                                },
                                6
                        ),
                        //work from 7-11
                        getHour(3,7),
                        getHour(3,8),
                        getHour(3,9),
                        getHour(3,10),
                        //eat half hour
                        //break half hour
                        new Hour(
                                new QuarterHour[]{
                                        new QuarterHour(6, QUARTER.ZERO, true),
                                        new QuarterHour(6, QUARTER.FIFTEEN, false),
                                        new QuarterHour(1, QUARTER.THIRTY, true),
                                        new QuarterHour(1, QUARTER.FOURTY_FIVE, false)
                                },
                                11
                        ),
                        //work four hours
                        getHour(3,12),
                        getHour(3,13),
                        getHour(3,14),
                        getHour(3,15),
                        // 4pm Eat half hour
                        // Shop half hour
                        new Hour(
                                new QuarterHour[]{
                                        new QuarterHour(6, QUARTER.ZERO, true),
                                        new QuarterHour(6, QUARTER.FIFTEEN, false),
                                        new QuarterHour(8, QUARTER.THIRTY, true),
                                        new QuarterHour(8, QUARTER.FOURTY_FIVE, false)
                                },
                                16
                        ),
                        // 5pm Free time four hours
                        getHour(0, 17),
                        getHour(0, 18),
                        getHour(0, 19),
                        getHour(0, 20),
                        //9pm half hour study
                        //half hour meditate
                        new Hour(
                                new QuarterHour[]{
                                        new QuarterHour(2, QUARTER.ZERO, true),
                                        new QuarterHour(2, QUARTER.FIFTEEN, false),
                                        new QuarterHour(5, QUARTER.THIRTY, true),
                                        new QuarterHour(5, QUARTER.FOURTY_FIVE, false)
                                },
                                21
                        ),
                        //10pm sleep
                        getHour(7,22),
                        getHour(7,23)
                }
        );
    }


    static Hour getHour(int taskId, int hourInt) {
        return new Hour(
                new QuarterHour[]{
                        new QuarterHour(taskId, QUARTER.ZERO, true),
                        new QuarterHour(taskId, QUARTER.FIFTEEN, false),
                        new QuarterHour(taskId, QUARTER.THIRTY, false),
                        new QuarterHour(taskId, QUARTER.FOURTY_FIVE, false)
                },
                hourInt
        );
    }
}
