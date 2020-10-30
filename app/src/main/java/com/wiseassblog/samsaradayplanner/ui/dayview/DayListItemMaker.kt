package com.wiseassblog.samsaradayplanner.ui.dayview

import android.content.Context
import com.wiseassblog.samsaradayplanner.common.getHourBlockText
import com.wiseassblog.samsaradayplanner.domain.Day
import com.wiseassblog.samsaradayplanner.domain.Hour
import com.wiseassblog.samsaradayplanner.domain.Tasks
import com.wiseassblog.samsaradayplanner.domain.constants.COLOR
import com.wiseassblog.samsaradayplanner.domain.constants.ICON
import java.util.*

/**
 * Takes in Context, Day, and Tasks objects, and spits out a collection of DayListViewItem objects.
 * The purpose of this class is to isolate the logic and OS interaction (for resources) which
 * is required to properly render the View.
 *
 *
 *
 *
 *
 *
 * Algorithm:
 * forEach (Hour in Day.Hours)
 * Create DayListItemView
 */
object DayListItemMaker {
    /**
     * Important note: DayListItemView is a data model which is exclusively concerned with rendering
     * the UI. As a result, we may not map directly from arrays of size 4 (i.e. when we call
     * hour.getQuarters()) to the content of the DayListItemView. This is because index of the
     * array fields within DayListItemView are only incremented if a given QuarterHour is "Active".
     * Inactive quarter hours are not drawn, but their extra space will be used for drawing full,
     * three quarter, and half hours.
     *
     *
     * In simple terms, I have to be careful how I perform this mapping:
     * ((Context, Day, Tasks) -> DayListItemView)
     *
     * @param context
     * @param day
     * @param tasks
     * @return
     */
    fun getItemList(
        context: Context,
        day: Day,
        tasks: Tasks
    ): List<DayListItem> {
        val list: MutableList<DayListItem> = ArrayList()

        day.hours.forEach { hour ->
            list.add(
                DayListItem(
                    getHourBlockText(hour.hourInteger, day.mode),
                    getIconResIds(hour, tasks, context),
                    getBackgroundsResIds(hour, tasks, context),
                    getTaskNames(hour, tasks),
                    getListItemType(hour)
                )
            )
        }

        return list
    }

    /**
     * Relative to the number of active QuarterHours and their position, determine the appropriate
     * LIST_ITEM_TYPE for this hour.
     *
     *
     * NOTE: This algorithm assumes that we never have a situation where only one QuarterHour is
     * in an Active state, but that particular QuarterHour is not the first one (i.e. not at
     * index [0] of hour.getQuarters. I have decided to enforce this in the UI itself.
     *
     *
     * 1. Check for invariants first:
     * - Only 1 active task means the whole hour is for that task
     * - Four active tasks is all Quarters
     *
     *
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
    private fun getListItemType(hour: Hour): LIST_ITEM_TYPE {
        var activeTasks = 0

        hour.quarters.forEach { q -> if (q.isActive) activeTasks++ }

        //single hour case
        if (activeTasks == 1) return LIST_ITEM_TYPE.FULL_HOUR
        /*
        half and half: 0, 2, active
        Quarter three quarter: 0, 1 active
        Three Quarter Quarter: 0, 3 active
         */if (activeTasks == 2) {
            //skip zero because it's always active
            val one = hour.quarters[1].isActive
            val two = hour.quarters[2].isActive
            val three = hour.quarters[3].isActive
            if (!one && two && !three) return LIST_ITEM_TYPE.HALF_HALF
            if (one && !two && !three) return LIST_ITEM_TYPE.QUARTER_THREE_QUARTER
            if (!one && !two && three) return LIST_ITEM_TYPE.THREE_QUARTER_QUARTER
        }
        if (activeTasks == 3) {
            val first = hour.quarters[0].isActive
            val second = hour.quarters[1].isActive
            val third = hour.quarters[2].isActive
            val fourth = hour.quarters[3].isActive

            //Quarter Half Quarter: 1st is active, 2nd is active, 3rd is inactive, 4th is active
            if (first && second && !third && fourth) return LIST_ITEM_TYPE.QUARTER_HALF_QUARTER
            //Half Quarter Quarter: 1st is active, 2nd is inactive, 3rd is active, 4th is active
            if (first && !second && third && fourth) return LIST_ITEM_TYPE.HALF_QUARTER_QUARTER
            //Quarter Quarter Half: 1st is active, 2nd is active, 3rd is active, 4th is inactive
            if (first && second && third && !fourth) return LIST_ITEM_TYPE.QUARTER_QUARTER_HALF
        }
        return LIST_ITEM_TYPE.QUARTER_QUARTER_QUARTER_QUARTER
    }

    /**
     * Assign tasks names to each Quarter Hour
     *
     * @param hour
     * @return
     */
    private fun getTaskNames(hour: Hour, tasks: Tasks): Array<String> {
        val taskNames = Array(4) { "" }

        hour.quarters.forEachIndexed { index, quarter ->
            taskNames[index] = tasks.getTaskById(quarter.taskId)!!.taskName
        }

        return taskNames
    }

    /**
     * For each task in an Hour, retrieve the appropriate resource ID based on the enum COLOR.
     *
     *
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
    private fun getBackgroundsResIds(hour: Hour, tasks: Tasks, context: Context): IntArray {
        val resIds = IntArray(4)

        hour.quarters.forEachIndexed { index, qh ->
            resIds[index] = getBackgroundImageResource(
                context,
                tasks.getTaskById(qh.taskId)!!.taskColor
            )
        }

        return resIds
    }

    private fun getBackgroundImageResource(context: Context, taskColor: COLOR): Int {
        return when (taskColor) {
            COLOR.DARK_BLUE -> context.resources
                .getIdentifier(
                    "darkBlue",
                    "color",
                    context.packageName
                )
            COLOR.BURNT_ORANGE -> context.resources
                .getIdentifier(
                    "burntOrange",
                    "color",
                    context.packageName
                )
            COLOR.GREEN -> context.resources
                .getIdentifier(
                    "green",
                    "color",
                    context.packageName
                )
            COLOR.DARK_RED -> context.resources
                .getIdentifier(
                    "red",
                    "color",
                    context.packageName
                )
            COLOR.DARK_LIME -> context.resources
                .getIdentifier(
                    "lime",
                    "color",
                    context.packageName
                )
            COLOR.LIGHT_BLUE -> context.resources
                .getIdentifier(
                    "lightBlue",
                    "color",
                    context.packageName
                )
            COLOR.MAUVE -> context.resources
                .getIdentifier(
                    "mauve",
                    "color",
                    context.packageName
                )
            COLOR.BROWN -> context.resources
                .getIdentifier(
                    "brown",
                    "color",
                    context.packageName
                )
            COLOR.TEAL -> context.resources
                .getIdentifier(
                    "teal",
                    "color",
                    context.packageName
                )
        }
    }

    private fun getIconResIds(hour: Hour, tasks: Tasks, context: Context): IntArray {
        val resIds = IntArray(4)
        var index = 0
        for (qh in hour.quarters) {
            val taskId = qh.taskId
            resIds[index] = getIconResource(context, tasks.getTaskById(taskId)!!.taskIcon)
            index++
        }
        return resIds
    }

    private fun getIconResource(context: Context, icon: ICON): Int {
        return when (icon) {
            ICON.FREE_TIME -> context.resources
                .getIdentifier(
                    "ic_free_time",
                    "drawable",
                    context.packageName
                )
            ICON.BREAK -> context.resources
                .getIdentifier(
                    "ic_break",
                    "drawable",
                    context.packageName
                )
            ICON.STUDY -> context.resources
                .getIdentifier(
                    "ic_study",
                    "drawable",
                    context.packageName
                )
            ICON.WORK -> context.resources
                .getIdentifier(
                    "ic_work",
                    "drawable",
                    context.packageName
                )
            ICON.EXERCISE -> context.resources
                .getIdentifier(
                    "ic_exercise",
                    "drawable",
                    context.packageName
                )
            ICON.MENTAL_CULTIVATION -> context.resources
                .getIdentifier(
                    "ic_bhavana",
                    "drawable",
                    context.packageName
                )
            ICON.EAT -> context.resources
                .getIdentifier(
                    "ic_eat",
                    "drawable",
                    context.packageName
                )
            ICON.SLEEP -> context.resources
                .getIdentifier(
                    "ic_rest",
                    "drawable",
                    context.packageName
                )
            ICON.SHOP -> context.resources
                .getIdentifier(
                    "ic_shop",
                    "drawable",
                    context.packageName
                )
        }
    }
}