package com.wiseassblog.samsaradayplanner.ui.tasklistview

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.WithConstraints
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.VectorAsset
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.platform.DensityAmbient
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wiseassblog.samsaradayplanner.common.getColorResId
import com.wiseassblog.samsaradayplanner.common.getResIdFromEnum
import com.wiseassblog.samsaradayplanner.domain.Tasks
import com.wiseassblog.samsaradayplanner.ui.*
import com.wiseassblog.samsaradayplanner.ui.burntOrange
import com.wiseassblog.samsaradayplanner.ui.darkBlue
import com.wiseassblog.samsaradayplanner.ui.green
import com.wiseassblog.samsaradayplanner.ui.managehourview.HourViewToolbar
import com.wiseassblog.samsaradayplanner.ui.managetaskview.TaskViewToolbar

@Composable
fun TaskListScreen(tasks: Tasks,
                   backClickHandler:(() -> Unit)?,
                   itemClickHandler:((Int) -> Unit)?
) {
    SamsaraTheme {
        Scaffold(
            topBar = {
                TaskListViewToolbar(
                    clickHandler = {
                        backClickHandler?.invoke()
                    }
                )
            }
        ) {
            Row {
                WithConstraints {
                    val size = with(DensityAmbient.current) { (constraints.maxWidth / 2).toDp() }
                    //take half for left column
                    ScrollableColumn(modifier = Modifier.preferredWidth(size)) {
                        tasks.getFirstHalf().map {
                            TaskListItem(
                                color = Color(it.taskColor.rgb),
                                icon = vectorResource(
                                    id = getResIdFromEnum(ContextAmbient.current, it.taskIcon)
                                ),
                                text = it.taskName,
                                height = size,
                                it.taskId,
                                itemClickHandler
                            )
                        }
                    }

                    //half for right column
                    ScrollableColumn(modifier = Modifier.preferredWidth(size).offset(x = size)) {
                        tasks.getSecondHalf().map {
                            TaskListItem(
                                color = Color(it.taskColor.rgb),
                                icon = vectorResource(
                                    id = getResIdFromEnum(ContextAmbient.current, it.taskIcon)
                                ),
                                text = it.taskName,
                                height = size,
                                it.taskId,
                                itemClickHandler
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TaskListItem(color: Color,
                 icon: VectorAsset,
                 text: String,
                 height: Dp,
                 taskId: Int,
                 itemClickHandler:((Int) -> Unit)? ) {
    Box(
        modifier = Modifier
            .background(color = color)
            .fillMaxWidth()
            .preferredHeight(height = height)
            .clickable(
                onClick = { itemClickHandler?.invoke(taskId)}
            )

    ) {
        Column(Modifier.align(Alignment.Center)) {
            Text(text = text, color = Color.White, style = typography.h4)
            Icon(
                icon, tint = Color.White, modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(36.dp)
            )
        }
    }
}