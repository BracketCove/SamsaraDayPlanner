package com.wiseassblog.samsaradayplanner.common;

import android.content.Context;

import com.wiseassblog.samsaradayplanner.domain.constants.ICON;

/**
 * Helps to map to and from the ICON enum and dynamically generated resource ids
 */
public class IconUtility {

    public static final  String[] iconNames = {
            "Free Time",
            "Break",
            "Study",
            "Work",
            "Exercise",
            "Mental Cultivation",
            "Eat",
            "Rest",
            "Shop"
    };

    public static int getResIdFromEnum(Context context, ICON icon) {
        switch (icon) {
            case FREE_TIME: return getId("ic_free_time", context);
            case BREAK: return getId("ic_break", context);
            case STUDY: return getId("ic_study", context);
            case WORK: return getId("ic_work", context);
            case EXERCISE: return getId("ic_exercise", context);
            case MENTAL_CULTIVATION: return getId("ic_bhavana", context);
            case EAT: return getId("ic_eat", context);
            case SLEEP: return getId("ic_rest", context);
            case SHOP: return getId("ic_shopping", context);
            default: FREE_TIME: return getId("ic_free_time", context);
        }
    }

    private static int getId(String s, Context context) {
        return context.getResources()
                .getIdentifier(
                        s,
                        "drawable",
                        context.getPackageName()
                );
    }
}
