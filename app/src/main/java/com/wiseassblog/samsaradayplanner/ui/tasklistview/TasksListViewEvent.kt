package com.wiseassblog.samsaradayplanner.ui.tasklistview

sealed class TasksListViewEvent {
    object OnStart: TasksListViewEvent()
    data class OnListItemSelected(val position: Int): TasksListViewEvent()
    object OnBackPressed: TasksListViewEvent()
}