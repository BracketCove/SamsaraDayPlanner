package com.wiseassblog.samsaradayplanner.ui.dayview

import androidx.annotation.IntegerRes

/**
 * In order to isolate the logic of generating appropriate data for the view and the adapter,
 * I will create a special kind of ViewModel which will contain the data in a form which simplifies
 * the View binding for the Adapter.
 *
 *
 *
 *
 *
 *
 *
 *
 * See the DayListViewItemMaker class for more details
 */
class DayListItem(
    val hourBlockText: String,
    @field:IntegerRes val iconResId: IntArray,
    @field:IntegerRes val backgroundResId: IntArray,
    val taskNames: Array<String>,
    val type: LIST_ITEM_TYPE
)