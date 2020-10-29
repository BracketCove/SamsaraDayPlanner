package com.wiseassblog.samsaradayplanner.common

import android.content.Context
import androidx.core.content.ContextCompat
import com.wiseassblog.samsaradayplanner.R
import com.wiseassblog.samsaradayplanner.domain.constants.COLOR
import com.wiseassblog.samsaradayplanner.domain.constants.ICON



fun getResIdFromEnum(context: Context, icon: ICON?): Int {
    when (icon) {
        ICON.FREE_TIME -> return getId("ic_free_time", context)
        ICON.BREAK -> return getId("ic_break", context)
        ICON.STUDY -> return getId("ic_study", context)
        ICON.WORK -> return getId("ic_work", context)
        ICON.EXERCISE -> return getId("ic_exercise", context)
        ICON.MENTAL_CULTIVATION -> return getId("ic_bhavana", context)
        ICON.EAT -> return getId("ic_eat", context)
        ICON.SLEEP -> return getId("ic_rest", context)
        ICON.SHOP -> return getId("ic_shop", context)
        else -> return getId("ic_free_time", context)
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
        else -> FREE_TIME@ return ContextCompat.getColor(
            context!!,
            R.color.lightBlue
        )
    }
}