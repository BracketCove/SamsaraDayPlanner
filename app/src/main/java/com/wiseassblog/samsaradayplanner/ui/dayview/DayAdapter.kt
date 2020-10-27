package com.wiseassblog.samsaradayplanner.ui.dayview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wiseassblog.samsaradayplanner.R
import com.wiseassblog.samsaradayplanner.ui.dayview.DayAdapter.BaseViewHolder

/**
 * In retrospect, I should have went with an approach of dynamically building the appropriate
 * list items instead of enumerating and selecting for them.
 *
 *
 * I knew that the approach I used here would require a fair amount of repetitious code, but
 * the amount I ended up with absolutely warranted using a more complicated but more efficient
 * approach. Live and learn.
 */
internal class DayAdapter(
    private val data: List<DayListItemView>,
    private val callback: (Int) -> Unit
) : RecyclerView.Adapter<BaseViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return when (data[position].type) {
            LIST_ITEM_TYPE.HALF_HALF -> 1
            LIST_ITEM_TYPE.QUARTER_QUARTER_HALF -> 2
            LIST_ITEM_TYPE.QUARTER_HALF_QUARTER -> 3
            LIST_ITEM_TYPE.HALF_QUARTER_QUARTER -> 4
            LIST_ITEM_TYPE.QUARTER_QUARTER_QUARTER_QUARTER -> 5
            LIST_ITEM_TYPE.QUARTER_THREE_QUARTER -> 6
            LIST_ITEM_TYPE.THREE_QUARTER_QUARTER -> 7
            else ->                 //default one hour
                0
        }
    }

    /**
     * 24 hours in a day, yeah?
     *
     * @return
     */
    override fun getItemCount(): Int {
        return 24
    }

    /**
     * Upon creation of a View Holder, I must inflate the appropriate kind of layout.
     *
     *
     * Kinds:
     * 0 - Full Hour
     * 1 - Half Half
     * 2 - Quarter Quarter Half
     * 3 - Quarter Half Quarter
     * 4 - Half Quarter Quarter
     * 5 - Quarter Quarter Quarter Quarter
     *
     * @param parent
     * @param viewType
     * @return
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        var vh: BaseViewHolder? = null
        var view: View? = null
        when (viewType) {
            0 -> {
                view = inflater.inflate(
                    R.layout.list_item_day_view_full_hour,
                    parent,
                    false
                )
                vh = SingleTaskViewHolder(view)
            }
            1 -> {
                view = inflater.inflate(
                    R.layout.list_item_day_view_half_and_half,
                    parent,
                    false
                )
                vh = TwoTaskViewHolder(view)
            }
            2 -> {
                view = inflater.inflate(
                    R.layout.list_item_day_view_quarter_quarter_half,
                    parent,
                    false
                )
                vh = ThreeTaskViewHolder(view)
            }
            3 -> {
                view = inflater.inflate(
                    R.layout.list_item_day_view_quarter_half_quarter,
                    parent,
                    false
                )
                vh = ThreeTaskViewHolder(view)
            }
            4 -> {
                view = inflater.inflate(
                    R.layout.list_item_day_view_half_quarter_quarter,
                    parent,
                    false
                )
                vh = ThreeTaskViewHolder(view)
            }
            5 -> {
                view = inflater.inflate(
                    R.layout.list_item_day_view_quarters,
                    parent,
                    false
                )
                vh = FourTaskViewHolder(view)
            }
            6 -> {
                view = inflater.inflate(
                    R.layout.list_item_day_view_quarter_three_quarter,
                    parent,
                    false
                )
                vh = TwoTaskViewHolder(view)
            }
            7 -> {
                view = inflater.inflate(
                    R.layout.list_item_day_view_three_quarter_quarter,
                    parent,
                    false
                )
                vh = TwoTaskViewHolder(view)
            }
        }
        return vh!!
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val item = data[position]
        holder.hourBlockText.text = item.hourBlockText
        holder.root.setOnClickListener { view: View? -> callback.invoke(position) }
        when (item.type) {
            LIST_ITEM_TYPE.FULL_HOUR -> {
                val svh = holder as SingleTaskViewHolder
                svh.firstTaskText.text = item.taskNames[0]
                svh.firstTaskIcon.setImageResource(item.iconResId[0])
                svh.firstTaskBackground.setImageResource(item.backgroundResId[0])
            }
            LIST_ITEM_TYPE.HALF_HALF -> bindTwoTaskViewHolder(
                holder as TwoTaskViewHolder,
                item,
                0,
                2
            )
            LIST_ITEM_TYPE.QUARTER_QUARTER_HALF -> bindThreeTaskViewHolder(
                holder as ThreeTaskViewHolder,
                item,
                0,
                1,
                2
            )
            LIST_ITEM_TYPE.QUARTER_HALF_QUARTER -> bindThreeTaskViewHolder(
                holder as ThreeTaskViewHolder,
                item,
                0,
                1,
                3
            )
            LIST_ITEM_TYPE.HALF_QUARTER_QUARTER -> bindThreeTaskViewHolder(
                holder as ThreeTaskViewHolder,
                item,
                0,
                2,
                3
            )
            LIST_ITEM_TYPE.QUARTER_QUARTER_QUARTER_QUARTER -> {
                val fvh = holder as FourTaskViewHolder
                fvh.firstTaskText.text = item.taskNames[0]
                fvh.firstTaskIcon.setImageResource(item.iconResId[0])
                fvh.firstTaskBackground.setImageResource(item.backgroundResId[0])
                fvh.secondTaskText.text = item.taskNames[1]
                fvh.secondTaskIcon.setImageResource(item.iconResId[1])
                fvh.secondTaskBackground.setImageResource(item.backgroundResId[1])
                fvh.thirdTaskText.text = item.taskNames[2]
                fvh.thirdTaskIcon.setImageResource(item.iconResId[2])
                fvh.thirdTaskBackground.setImageResource(item.backgroundResId[2])
                fvh.fourthTaskText.text = item.taskNames[3]
                fvh.fourthTaskIcon.setImageResource(item.iconResId[3])
                fvh.fourthTaskBackground.setImageResource(item.backgroundResId[3])
            }
            LIST_ITEM_TYPE.QUARTER_THREE_QUARTER -> {
                bindTwoTaskViewHolder(
                    holder as TwoTaskViewHolder,
                    item,
                    0,
                    1
                )
                val twvhTwo = holder
                twvhTwo.firstTaskText.text = item.taskNames[0]
                twvhTwo.firstTaskIcon.setImageResource(item.iconResId[0])
                twvhTwo.firstTaskBackground.setImageResource(item.backgroundResId[0])
                twvhTwo.secondTaskText.text = item.taskNames[1]
                twvhTwo.secondTaskIcon.setImageResource(item.iconResId[1])
                twvhTwo.secondTaskBackground.setImageResource(item.backgroundResId[1])
            }
            LIST_ITEM_TYPE.THREE_QUARTER_QUARTER -> bindTwoTaskViewHolder(
                holder as TwoTaskViewHolder,
                item,
                0,
                3
            )
        }
    }

    /**
     * Helper method to reduce the amount of repetitious code which is alreay TOO DAMN HIGH
     *
     * @param fvh
     * @param item
     */
    private fun bindThreeTaskViewHolder(
        fvh: ThreeTaskViewHolder,
        item: DayListItemView,
        firstIndex: Int,
        secondIndex: Int,
        thirdIndex: Int
    ) {
        fvh.firstTaskText.text = item.taskNames[firstIndex]
        fvh.firstTaskIcon.setImageResource(item.iconResId[firstIndex])
        fvh.firstTaskBackground.setImageResource(item.backgroundResId[firstIndex])
        fvh.secondTaskText.text = item.taskNames[secondIndex]
        fvh.secondTaskIcon.setImageResource(item.iconResId[secondIndex])
        fvh.secondTaskBackground.setImageResource(item.backgroundResId[secondIndex])
        fvh.thirdTaskText.text = item.taskNames[thirdIndex]
        fvh.thirdTaskIcon.setImageResource(item.iconResId[thirdIndex])
        fvh.thirdTaskBackground.setImageResource(item.backgroundResId[thirdIndex])
    }

    private fun bindTwoTaskViewHolder(
        tvh: TwoTaskViewHolder,
        item: DayListItemView,
        firstIndex: Int,
        secondIndex: Int
    ) {
        tvh.firstTaskText.text = item.taskNames[firstIndex]
        tvh.firstTaskIcon.setImageResource(item.iconResId[firstIndex])
        tvh.firstTaskBackground.setImageResource(item.backgroundResId[firstIndex])
        tvh.secondTaskText.text = item.taskNames[secondIndex]
        tvh.secondTaskIcon.setImageResource(item.iconResId[secondIndex])
        tvh.secondTaskBackground.setImageResource(item.backgroundResId[secondIndex])
    }

    internal open inner class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var root: View
        var hourBlockText: TextView

        init {
            root = itemView.findViewById(R.id.list_item_root)
            hourBlockText = itemView.findViewById(R.id.lbl_hour_block)
        }
    }

    /**
     * For full hour tasks
     */
    internal inner class SingleTaskViewHolder(itemView: View) : BaseViewHolder(itemView) {
        var firstTaskText: TextView
        var firstTaskIcon: ImageView
        var firstTaskBackground: ImageView

        init {
            firstTaskText = itemView.findViewById(R.id.lbl_first_task_text)
            firstTaskIcon = itemView.findViewById(R.id.imv_first_task_icon)
            firstTaskBackground = itemView.findViewById(R.id.imv_first_task_background)
        }
    }

    /**
     * For two half hour tasks
     */
    internal inner class TwoTaskViewHolder(itemView: View) : BaseViewHolder(itemView) {
        var firstTaskText: TextView
        var firstTaskIcon: ImageView
        var firstTaskBackground: ImageView
        var secondTaskText: TextView
        var secondTaskIcon: ImageView
        var secondTaskBackground: ImageView

        init {
            firstTaskText = itemView.findViewById(R.id.lbl_first_task_text)
            firstTaskIcon = itemView.findViewById(R.id.imv_first_task_icon)
            firstTaskBackground = itemView.findViewById(R.id.imv_first_task_background)
            secondTaskText = itemView.findViewById(R.id.lbl_second_task_text)
            secondTaskIcon = itemView.findViewById(R.id.imv_second_task_icon)
            secondTaskBackground = itemView.findViewById(R.id.imv_second_task_background)
        }
    }

    /**
     * For combinations of two quarters and a half hour task
     */
    internal inner class ThreeTaskViewHolder(itemView: View) : BaseViewHolder(itemView) {
        var firstTaskText: TextView
        var firstTaskIcon: ImageView
        var firstTaskBackground: ImageView
        var secondTaskText: TextView
        var secondTaskIcon: ImageView
        var secondTaskBackground: ImageView
        var thirdTaskText: TextView
        var thirdTaskIcon: ImageView
        var thirdTaskBackground: ImageView

        init {
            firstTaskText = itemView.findViewById(R.id.lbl_first_task_text)
            firstTaskIcon = itemView.findViewById(R.id.imv_first_task_icon)
            firstTaskBackground = itemView.findViewById(R.id.imv_first_task_background)
            secondTaskText = itemView.findViewById(R.id.lbl_second_task_text)
            secondTaskIcon = itemView.findViewById(R.id.imv_second_task_icon)
            secondTaskBackground = itemView.findViewById(R.id.imv_second_task_background)
            thirdTaskText = itemView.findViewById(R.id.lbl_third_task_text)
            thirdTaskIcon = itemView.findViewById(R.id.imv_third_task_icon)
            thirdTaskBackground = itemView.findViewById(R.id.imv_third_task_background)
        }
    }

    /**
     * Four quarters
     */
    internal inner class FourTaskViewHolder(itemView: View) : BaseViewHolder(itemView) {
        var firstTaskText: TextView
        var firstTaskIcon: ImageView
        var firstTaskBackground: ImageView
        var secondTaskText: TextView
        var secondTaskIcon: ImageView
        var secondTaskBackground: ImageView
        var thirdTaskText: TextView
        var thirdTaskIcon: ImageView
        var thirdTaskBackground: ImageView
        var fourthTaskText: TextView
        var fourthTaskIcon: ImageView
        var fourthTaskBackground: ImageView

        init {
            firstTaskText = itemView.findViewById(R.id.lbl_first_task_text)
            firstTaskIcon = itemView.findViewById(R.id.imv_first_task_icon)
            firstTaskBackground = itemView.findViewById(R.id.imv_first_task_background)
            secondTaskText = itemView.findViewById(R.id.lbl_second_task_text)
            secondTaskIcon = itemView.findViewById(R.id.imv_second_task_icon)
            secondTaskBackground = itemView.findViewById(R.id.imv_second_task_background)
            thirdTaskText = itemView.findViewById(R.id.lbl_third_task_text)
            thirdTaskIcon = itemView.findViewById(R.id.imv_third_task_icon)
            thirdTaskBackground = itemView.findViewById(R.id.imv_third_task_background)
            fourthTaskText = itemView.findViewById(R.id.lbl_fourth_task_text)
            fourthTaskIcon = itemView.findViewById(R.id.imv_fourth_task_icon)
            fourthTaskBackground = itemView.findViewById(R.id.imv_fourth_task_background)
        }
    }
}