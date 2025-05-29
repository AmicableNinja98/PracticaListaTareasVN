package com.example.practicalistatareas.core.ui.screens.taskEditionScreen

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
class TaskEditionScreenViewModel @Inject constructor(private val repository: TaskRepository) :
    ViewModel() {
    var state by mutableStateOf<TaskEditionScreenState>(TaskEditionScreenState())
        private set

    fun getTask(id: Long) {
        if (id.toInt() == 0) {
            state = state.copy(isNew = true)
            return
        }

        viewModelScope.launch {
            val task = repository.getTaskById(id)
            if (task != null) {
                state = state.copy(
                    id = task.id,
                    title = task.title,
                    description = task.description,
                    dueDate = task.dueDate,
                    priority = task.priority,
                    isCompleted = task.isCompleted,
                    createdAt = task.createdAt,
                )
            }
        }

    }

    fun onTitleChanged(newTitle: String) {
        state.titleErrorFormat.let {
            state = state.copy(titleErrorFormat = "", isTitleError = false)
        }

        state = if (newTitle.isEmpty()) {
            state.copy(
                title = newTitle,
                titleErrorFormat = "El título no puede estar vacío",
                isTitleError = true
            )
        } else
            state.copy(title = newTitle)
    }

    fun onDescriptionChanged(newDescription: String) {
        state = state.copy(description = newDescription)
    }

    fun onDateChanged(newDate: Long?) {
        if(newDate != null)
            state = state.copy(dueDate = newDate)
    }

    fun onPriorityChanged(newPriority: Int) {
        state = state.copy(priority = newPriority)
    }

    fun onIsCompletedChanged(newValue: Boolean) {
        state = state.copy(isCompleted = newValue)
    }

    private fun validate() : Boolean = !state.isTitleError

    fun saveChanges(){
        if(validate()){
            viewModelScope.launch {
                if(state.isNew){
                    repository.insertTask(getTaskFromState())
                }
                else{
                    repository.updateTask(getTaskFromState())
                }
            }
        }
    }

    fun deleteTask(){
        viewModelScope.launch {
            repository.deleteTask(getTaskFromState())
        }
    }

    private fun getTaskFromState() : Task = Task(
        id = state.id,
        title = state.title,
        description = state.description,
        dueDate = state.dueDate,
        isCompleted = state.isCompleted,
    )
}