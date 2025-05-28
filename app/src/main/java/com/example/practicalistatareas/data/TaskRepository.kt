package com.example.practicalistatareas.data

import com.example.practicalistatareas.domain.Task
import javax.inject.Inject

class TaskRepository @Inject constructor(private val taskDao: TaskDao) {

    suspend fun insertTask(task: Task) = taskDao.insertTask(task)

    suspend fun deleteTask(task: Task) = taskDao.deleteTask(task)

    suspend fun updateTask(task: Task) = taskDao.updateTask(task)

    fun getAllTasks() = taskDao.getAllTasks()

    suspend fun getTaskById(id : Long) = taskDao.getTaskById(id)
}