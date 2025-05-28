package com.example.practicalistatareas.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id : Long,
    val title : String,
    val description : String?,
    val dueDate : Long?,
    val priority : Int = 0,
    var isCompleted : Boolean,
    val createdAt : Long,
)