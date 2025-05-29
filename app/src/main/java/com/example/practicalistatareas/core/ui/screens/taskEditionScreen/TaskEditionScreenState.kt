package com.example.practicalistatareas.core.ui.screens.taskEditionScreen

data class TaskEditionScreenState(
    val id : Long = 0,
    val title : String = "",
    val description : String? = "",
    val dueDate : Long? = null,
    val priority : Int = 0,
    var isCompleted : Boolean = false,
    val createdAt : Long = 0,

    val titleErrorFormat : String? = "",

    val isTitleError : Boolean = false,

    val isNew : Boolean = false,
)