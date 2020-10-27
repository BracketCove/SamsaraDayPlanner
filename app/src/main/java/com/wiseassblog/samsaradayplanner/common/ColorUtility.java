package com.wiseassblog.samsaradayplanner.common;

import android.content.Context;
import android.content.res.ColorStateList;

import androidx.core.content.ContextCompat;

import com.wiseassblog.samsaradayplanner.R;
import com.wiseassblog.samsaradayplanner.domain.constants.COLOR;

public class ColorUtility {

    public static int getColorResId(Context context, COLOR color) {
        switch (color) {
            case DARK_BLUE:
                return ContextCompat.getColor(
                        context,
                        R.color.darkBlue
                );
            case BURNT_ORANGE:
                return ContextCompat.getColor(
                        context,
                        R.color.burntOrange
                );
            case GREEN:
                return ContextCompat.getColor(
                        context,
                        R.color.green
                );
            case DARK_RED:
                return ContextCompat.getColor(
                        context,
                        R.color.red
                );
            case DARK_LIME:
                return ContextCompat.getColor(
                        context,
                        R.color.lime
                );
            case LIGHT_BLUE:
                return ContextCompat.getColor(
                        context,
                        R.color.lightBlue
                );
            case MAUVE:
                return ContextCompat.getColor(
                        context,
                        R.color.mauve
                );
            case BROWN:
                return ContextCompat.getColor(
                        context,
                        R.color.brown
                );
            case TEAL:
                return ContextCompat.getColor(
                        context,
                        R.color.teal
                );
            default:
                FREE_TIME:
                return ContextCompat.getColor(
                        context,
                        R.color.lightBlue
                );
        }
    }
}
