package com.example.practicalistatareas.core.ui.screens.taskListScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicalistatareas.data.TaskRepository
import com.example.practicalistatareas.domain.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskListScreenViewModel @Inject constructor(private val taskRepository: TaskRepository) : ViewModel() {
    var state by mutableStateOf<TaskListScreenState>(TaskListScreenState.Loading)
        private set

    fun getData(){
        viewModelScope.launch {
            state = TaskListScreenState.Loading
            taskRepository.getAllTasks().collect {
                tasks ->
                state = if(tasks.isNotEmpty())
                    TaskListScreenState.Success(tasks.toMutableList())
                else
                    TaskListScreenState.NoData
            }
        }
    }

    fun onTaskCompletedChange(task : Task,value : Boolean){
        task.isCompleted = value
        viewModelScope.launch {
            taskRepository.updateTask(task)
        }
    }
}