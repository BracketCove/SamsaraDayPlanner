package com.wiseassblog.samsaradayplanner.ui.managetaskview;

import android.content.Context;

import com.wiseassblog.samsaradayplanner.common.IconUtility;
import com.wiseassblog.samsaradayplanner.domain.constants.ICON;

import java.util.ArrayList;
import java.util.List;

class IconSpinnerItem {

    public static final int NUMBER_OF_ICONS = 9;

    public static List<IconSpinnerItem> getItems(Context context) {
        List<IconSpinnerItem> list = new ArrayList<>();

        for (int index = 0; index < NUMBER_OF_ICONS; index++) {
            list.add(
                    new IconSpinnerItem(
                            IconUtility.iconNames[index],
                            IconUtility.getResIdFromEnum(
                                    context,
                                    ICON.values()[index]
                            )
                    )
            );
        }

        return list;
    }

    private final String itemText;
    private final int itemIconResId;

    public IconSpinnerItem(String itemText, int itemIconResId) {
        this.itemText = itemText;
        this.itemIconResId = itemIconResId;
    }

    public String getItemText() {
        return itemText;
    }

    public int getItemIconResId() {
        return itemIconResId;
    }
}
