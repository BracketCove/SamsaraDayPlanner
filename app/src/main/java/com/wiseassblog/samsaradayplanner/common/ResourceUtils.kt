package com.wiseassblog.samsaradayplanner.common

import android.content.Context
import androidx.core.content.ContextCompat
import com.wiseassblog.samsaradayplanner.R
import com.wiseassblog.samsaradayplanner.domain.constants.COLOR
import com.wiseassblog.samsaradayplanner.domain.constants.ICON



fun getResIdFromEnum(context: Context, icon: ICON?): Int {
    return when (icon) {
        ICON.FREE_TIME -> getId("ic_free_time", context)
        ICON.BREAK -> getId("ic_break", context)
        ICON.STUDY -> getId("ic_study", context)
        ICON.WORK -> getId("ic_work", context)
        ICON.EXERCISE -> getId("ic_exercise", context)
        ICON.MENTAL_CULTIVATION -> getId("ic_bhavana", context)
        ICON.EAT -> getId("ic_eat", context)
        ICON.SLEEP -> getId("ic_rest", context)
        ICON.SHOP -> getId("ic_shop", context)
        else -> getId("ic_free_time", context)
    }
}

private fun getId(s: String, context: Context): Int {
    return context.resources
        .getIdentifier(
            s,
            "drawable",
            context.packageName
        )
}

fun getColorResId(context: Context?, color: COLOR?): Int {
    when (color) {
        COLOR.DARK_BLUE -> return ContextCompat.getColor(
            context!!,
            R.color.darkBlue
        )
        COLOR.BURNT_ORANGE -> return ContextCompat.getColor(
            context!!,
            R.color.burntOrange
        )
        COLOR.GREEN -> return ContextCompat.getColor(
            context!!,
            R.color.green
        )
        COLOR.DARK_RED -> return ContextCompat.getColor(
            context!!,
            R.color.red
        )
        COLOR.DARK_LIME -> return ContextCompat.getColor(
            context!!,
            R.color.lime
        )
        COLOR.LIGHT_BLUE -> return ContextCompat.getColor(
            context!!,
            R.color.lightBlue
        )
        COLOR.MAUVE -> return ContextCompat.getColor(
            context!!,
            R.color.mauve
        )
        COLOR.BROWN -> return ContextCompat.getColor(
            context!!,
            R.color.brown
        )
        COLOR.TEAL -> return ContextCompat.getColor(
            context!!,
            R.color.teal
        )
        else -> return ContextCompat.getColor(
            context!!,
            R.color.lightBlue
        )
    }
}