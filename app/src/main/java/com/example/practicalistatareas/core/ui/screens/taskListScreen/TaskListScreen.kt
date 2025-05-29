package com.example.practicalistatareas.core.ui.screens.taskListScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.practicalistatareas.R
import com.example.practicalistatareas.base.toStringDate
import com.example.practicalistatareas.core.ui.screens.LoadingScreen
import com.example.practicalistatareas.core.ui.screens.NoDataScreen
import com.example.practicalistatareas.domain.Task

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreenHost(
    taskListScreenViewModel: TaskListScreenViewModel,
    goToEdition: (Long) -> Unit
) {
    LaunchedEffect(Unit) {
        taskListScreenViewModel.getData()
    }

    Scaffold(
        modifier = Modifier.background(Color.White),
        topBar = {
            TopAppBar(
                title = { Text("Lista de tareas") },
                modifier = Modifier.padding(horizontal = 8.dp),
                actions = {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_filter_alt_24),
                            contentDescription = null
                        )
                    }
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.sort_alpha_down),
                            contentDescription = null
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    goToEdition(0)
                }
            ) {
                Icon(Icons.Default.Add,contentDescription = null)
            }
        }
    ) { innerPadding ->
        when (taskListScreenViewModel.state) {
            TaskListScreenState.Loading -> LoadingScreen(modifier = Modifier.padding(innerPadding))
            is TaskListScreenState.Success -> TaskListScreen(
                taskList = (taskListScreenViewModel.state as TaskListScreenState.Success).taskList,
                onTaskCompletedChange = taskListScreenViewModel::onTaskCompletedChange,
                goToEdition = goToEdition,
                modifier = Modifier.padding(innerPadding)
            )

            TaskListScreenState.NoData -> NoDataScreen(modifier = Modifier.padding(innerPadding))
        }
    }
}

@Composable
fun TaskListScreen(taskList: List<Task>,onTaskCompletedChange : (Task, Boolean) -> Unit, goToEdition: (Long) -> Unit, modifier: Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(taskList) { task ->
            TaskListItem(task, onTaskCompletedChange,goToEdition)
        }
    }
}

@Composable
fun TaskListItem(task: Task, onTaskCompletedChange : (Task, Boolean) -> Unit ,goToEdition: (Long) -> Unit) {
    var completed = remember { mutableStateOf(task.isCompleted) }
    Card(
        modifier = Modifier.padding(10.dp),
        onClick = {
            goToEdition(task.id)
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(6.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(task.title)
                Text(if (task.dueDate != null) "Fecha de vencimiento: ${task.dueDate.toStringDate()}" else "Sin fecha de vencimiento")
            }
            Checkbox(
                checked = completed.value,
                onCheckedChange = { value ->
                    completed.value = value
                    onTaskCompletedChange(task,value)
                }
            )
        }
    }
}