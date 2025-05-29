package com.example.practicalistatareas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.practicalistatareas.core.ui.theme.PracticaListaTareasTheme
import com.example.practicalistatareas.navigation.TaskNavGraph
import com.example.practicalistatareas.navigation.taskNavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            PracticaListaTareasTheme {
                NavHost(
                    navController = navController,
                    startDestination = TaskNavGraph.ROUTE,
                ){
                    taskNavGraph(navController)
                }
            }
        }
    }
}