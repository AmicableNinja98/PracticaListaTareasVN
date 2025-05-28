package com.example.practicalistatareas.core.ui.screens.taskListScreen

import com.example.practicalistatareas.domain.Task

sealed class TaskListScreenState {
    data object Loading : TaskListScreenState()
    data class Success(val taskList : MutableList<Task>) : TaskListScreenState()
    data object NoData : TaskListScreenState()
}