package com.example.practicalistatareas.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 1,
    val title : String,
    val description : String? = null,
    val dueDate : Long? = null,
    val priority : Int = 0,
    var isCompleted : Boolean = false,
    val createdAt : Long = Date().time,
)