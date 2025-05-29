package com.example.practicalistatareas.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.practicalistatareas.core.ui.screens.taskEditionScreen.TaskEditionScreenHost
import com.example.practicalistatareas.core.ui.screens.taskEditionScreen.TaskEditionScreenViewModel
import com.example.practicalistatareas.core.ui.screens.taskListScreen.TaskListScreenHost
import com.example.practicalistatareas.core.ui.screens.taskListScreen.TaskListScreenViewModel

object TaskNavGraph {
    const val ROUTE = "task"
    fun list() = "$ROUTE/list"
    fun edition(id: Long) = "$ROUTE/edition/$id"
}

fun NavGraphBuilder.taskNavGraph(navController: NavController) {
    navigation(startDestination = TaskNavGraph.list(), route = TaskNavGraph.ROUTE) {
        list(navController)
        edition(navController)
    }
}

private fun NavGraphBuilder.list(navController: NavController) {
    composable(route = TaskNavGraph.list()) {
        TaskListScreenHost(
            taskListScreenViewModel = hiltViewModel<TaskListScreenViewModel>(),
            goToEdition = { id ->
                navController.navigate(TaskNavGraph.edition(id))
            }
        )
    }
}

private fun NavGraphBuilder.edition(navController: NavController) {
    composable(
        route = "${TaskNavGraph.ROUTE}/edition/{id}", arguments = listOf(
            navArgument("id") {
                type = NavType.LongType
            }
        )
    ) { backStackEntry ->
        val id = backStackEntry.arguments?.getLong("id") ?: 0
        TaskEditionScreenHost(
            taskEditionScreenViewModel = hiltViewModel<TaskEditionScreenViewModel>(),
            id = id,
            goBack = {
                navController.popBackStack()
            }
        )
    }
}