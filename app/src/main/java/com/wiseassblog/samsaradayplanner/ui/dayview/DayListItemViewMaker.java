package com.wiseassblog.samsaradayplanner.ui.dayview;

import android.content.Context;

import androidx.annotation.VisibleForTesting;

import com.wiseassblog.samsaradayplanner.domain.Day;
import com.wiseassblog.samsaradayplanner.domain.Hour;
import com.wiseassblog.samsaradayplanner.domain.QuarterHour;
import com.wiseassblog.samsaradayplanner.domain.Tasks;
import com.wiseassblog.samsaradayplanner.domain.constants.COLOR;
import com.wiseassblog.samsaradayplanner.domain.constants.ICON;

import java.util.ArrayList;
import java.util.List;

import static com.wiseassblog.samsaradayplanner.common.TimeFormatUtility.getHourBlockText;

/**
 * Takes in Context, Day, and Tasks objects, and spits out a collection of DayListViewItem objects.
 * The purpose of this class is to isolate the logic and OS interaction (for resources) which
 * is required to properly render the View.
 * <p>
 * <p>
 * <p>
 * Algorithm:
 * forEach (Hour in Day.Hours)
 * Create DayListItemView
 */
public class DayListItemViewMaker {


    /**
     * Important note: DayListItemView is a data model which is exclusively concerned with rendering
     * the UI. As a result, we may not map directly from arrays of size 4 (i.e. when we call
     * hour.getQuarters()) to the content of the DayListItemView. This is because index of the
     * array fields within DayListItemView are only incremented if a given QuarterHour is "Active".
     * Inactive quarter hours are not drawn, but their extra space will be used for drawing full,
     * three quarter, and half hours.
     * <p>
     * In simple terms, I have to be careful how I perform this mapping:
     * ((Context, Day, Tasks) -> DayListItemView)
     *
     * @param context
     * @param day
     * @param tasks
     * @return
     */
    public static List<DayListItemView> getItemList(
            Context context,
            Day day,
            Tasks tasks) {

        List<DayListItemView> list = new ArrayList<>();
        for (Hour hour : day.getHours()) {
            list.add(
                    new DayListItemView(
                            getHourBlockText(hour.getHourInteger(), day.getMode()),
                            getIconResIds(hour, tasks, context),
                            getBackgroundsResIds(hour, tasks, context),
                            getTaskNames(hour, tasks),
                            getListItemType(hour)
                    )
            );
        }

        return list;
    }

    /**
     * Relative to the number of active QuarterHours and their position, determine the appropriate
     * LIST_ITEM_TYPE for this hour.
     * <p>
     * NOTE: This algorithm assumes that we never have a situation where only one QuarterHour is
     * in an Active state, but that particular QuarterHour is not the first one (i.e. not at
     * index [0] of hour.getQuarters. I have decided to enforce this in the UI itself.
     * <p>
     * 1. Check for invariants first:
     * - Only 1 active task means the whole hour is for that task
     * - Four active tasks is all Quarters
     * <p>
     * - Two active tasks can mean:
     * - Half and Half
     * - Quarter Three Quarter
     * - Three active tasks can be:
     * - Half, Quarter Quarter,
     * - Quarter, Half, Quarter,
     * - Quarter, Quarter Half,
     *
     * @param hour
     * @return
     */
    @VisibleForTesting
    private static LIST_ITEM_TYPE getListItemType(Hour hour) {
        QuarterHour[] quarters = hour.getQuarters();

        int activeTasks = 0;

        for (QuarterHour q : quarters) {
            if (q.getIsActive()) activeTasks++;
        }

        //single hour case
        if (activeTasks == 1) return LIST_ITEM_TYPE.FULL_HOUR;
        /*
        half and half: 0, 2, active
        Quarter three quarter: 0, 1 active
        Three Quarter Quarter: 0, 3 active
         */
        if (activeTasks == 2) {
            //skip zero because it's always active
            boolean one = hour.getQuarters()[1].getIsActive();
            boolean two = hour.getQuarters()[2].getIsActive();
            boolean three = hour.getQuarters()[3].getIsActive();
            if (!one && two && !three) return LIST_ITEM_TYPE.HALF_HALF;
            if (one && !two && !three) return LIST_ITEM_TYPE.QUARTER_THREE_QUARTER;
            if (!one && !two && three) return LIST_ITEM_TYPE.THREE_QUARTER_QUARTER;
        }
        if (activeTasks == 3) {
            boolean first = quarters[0].getIsActive();
            boolean second = quarters[1].getIsActive();
            boolean third = quarters[2].getIsActive();
            boolean fourth = quarters[3].getIsActive();

            //Quarter Half Quarter: 1st is active, 2nd is active, 3rd is inactive, 4th is active
            if (first && second && !third && fourth) return LIST_ITEM_TYPE.QUARTER_HALF_QUARTER;
            //Half Quarter Quarter: 1st is active, 2nd is inactive, 3rd is active, 4th is active
            if (first && !second && third && fourth) return LIST_ITEM_TYPE.HALF_QUARTER_QUARTER;
            //Quarter Quarter Half: 1st is active, 2nd is active, 3rd is active, 4th is inactive
            if (first && second && third && !fourth) return LIST_ITEM_TYPE.QUARTER_QUARTER_HALF;
        }
        if (activeTasks == 4) return LIST_ITEM_TYPE.QUARTER_QUARTER_QUARTER_QUARTER;


        return null;
    }

    /**
     * Assign tasks names to each Quarter Hour
     *
     * @param hour
     * @return
     */
    @VisibleForTesting
    private static String[] getTaskNames(Hour hour, Tasks tasks) {
        String[] taskNames = new String[4];

        int index = 0;
        for (QuarterHour qh : hour.getQuarters()) {
                int taskId = qh.getTaskId();
                taskNames[index] = tasks.getTaskById(taskId).getTaskName();
                index++;
        }


        return taskNames;
    }

    /**
     * For each task in an Hour, retrieve the appropriate resource ID based on the enum COLOR.
     * <p>
     * Suppose we have this combination:
     * Array indexs:
     * 0 - Active
     * 1 - Active
     * 2 - Inactive
     * 3 - Active
     *
     * @param hour
     * @param context
     * @return
     */
    @VisibleForTesting
    private static int[] getBackgroundsResIds(Hour hour, Tasks tasks, Context context) {
        int[] resIds = new int[4];

        int index = 0;
        for (QuarterHour qh : hour.getQuarters()) {
                int taskId = qh.getTaskId();
                resIds[index] = getBackgroundImageResource(context, tasks.getTaskById(taskId).getTaskColor());
                index++;
        }

        return resIds;
    }

    @VisibleForTesting
    private static int getBackgroundImageResource(Context context, COLOR taskColor) {
        switch (taskColor) {
            case DARK_BLUE:
                return context.getResources()
                        .getIdentifier(
                                "darkBlue",
                                "color",
                                context.getPackageName()
                        );
            case BURNT_ORANGE:
                return context.getResources()
                        .getIdentifier(
                                "burntOrange",
                                "color",
                                context.getPackageName()
                        );
            case GREEN:
                return context.getResources()
                        .getIdentifier(
                                "green",
                                "color",
                                context.getPackageName()
                        );
            case DARK_RED:
                return context.getResources()
                        .getIdentifier(
                                "red",
                                "color",
                                context.getPackageName()
                        );
            case DARK_LIME:
                return context.getResources()
                        .getIdentifier(
                                "lime",
                                "color",
                                context.getPackageName()
                        );
            case LIGHT_BLUE:
                return context.getResources()
                        .getIdentifier(
                                "lightBlue",
                                "color",
                                context.getPackageName()
                        );
            case MAUVE:
                return context.getResources()
                        .getIdentifier(
                                "mauve",
                                "color",
                                context.getPackageName()
                        );
            case BROWN:
                return context.getResources()
                        .getIdentifier(
                                "brown",
                                "color",
                                context.getPackageName()
                        );
            case TEAL:
                return context.getResources()
                        .getIdentifier(
                                "teal",
                                "color",
                                context.getPackageName()
                        );
        }
        return 0;
    }

    @VisibleForTesting
    private static int[] getIconResIds(Hour hour, Tasks tasks, Context context) {
        int[] resIds = new int[4];

        int index = 0;
        for (QuarterHour qh : hour.getQuarters()) {
                int taskId = qh.getTaskId();
                resIds[index] = getIconResource(context, tasks.getTaskById(taskId).getTaskIcon());
                index++;
        }

        return resIds;
    }

    @VisibleForTesting
    private static int getIconResource(Context context, ICON icon) {
        switch (icon) {
            case FREE_TIME:
                return context.getResources()
                        .getIdentifier(
                                "ic_free_time",
                                "drawable",
                                context.getPackageName()
                        );
            case BREAK:
                return context.getResources()
                        .getIdentifier(
                                "ic_break",
                                "drawable",
                                context.getPackageName()
                        );
            case STUDY:
                return context.getResources()
                        .getIdentifier(
                                "ic_study",
                                "drawable",
                                context.getPackageName()
                        );
            case WORK:
                return context.getResources()
                        .getIdentifier(
                                "ic_work",
                                "drawable",
                                context.getPackageName()
                        );
            case EXERCISE:
                return context.getResources()
                        .getIdentifier(
                                "ic_exercise",
                                "drawable",
                                context.getPackageName()
                        );
            case MENTAL_CULTIVATION:
                return context.getResources()
                        .getIdentifier(
                                "ic_bhavana",
                                "drawable",
                                context.getPackageName()
                        );
            case EAT:
                return context.getResources()
                        .getIdentifier(
                                "ic_eat",
                                "drawable",
                                context.getPackageName()
                        );
            case SLEEP:
                return context.getResources()
                        .getIdentifier(
                                "ic_rest",
                                "drawable",
                                context.getPackageName()
                        );
            case SHOP:
                return context.getResources()
                        .getIdentifier(
                                "ic_shop",
                                "drawable",
                                context.getPackageName()
                        );
        }
        return 0;
    }

}
