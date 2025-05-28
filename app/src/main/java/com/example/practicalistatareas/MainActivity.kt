package com.example.practicalistatareas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.practicalistatareas.core.ui.screens.taskListScreen.TaskListScreenHost
import com.example.practicalistatareas.core.ui.screens.taskListScreen.TaskListScreenViewModel
import com.example.practicalistatareas.core.ui.theme.PracticaListaTareasTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PracticaListaTareasTheme {
                TaskListScreenHost(
                    taskListScreenViewModel = hiltViewModel<TaskListScreenViewModel>(),
                    goToEdition = {}
                )
            }
        }
    }
}