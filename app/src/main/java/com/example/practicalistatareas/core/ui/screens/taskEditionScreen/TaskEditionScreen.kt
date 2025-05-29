package com.example.practicalistatareas.core.ui.screens.taskEditionScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class TaskEditionEvents(
    val onTitleChanged: (String) -> Unit,
    val onDescriptionChanged: (String) -> Unit,
    val onDueDateChanged: (Long?) -> Unit,
    val onPriorityChanged: (Int) -> Unit,
    val onCompletedChanged: (Boolean) -> Unit,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskEditionScreenHost(
    taskEditionScreenViewModel: TaskEditionScreenViewModel,
    id: Long,
    goBack: () -> Unit
) {
    LaunchedEffect(Unit) {
        taskEditionScreenViewModel.getTask(id)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(if (taskEditionScreenViewModel.state.isNew) "Crear tarea" else "Editar tarea")
                },
                navigationIcon = {
                    IconButton(
                        onClick = goBack
                    ) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    if (!taskEditionScreenViewModel.state.isNew) {
                        IconButton(
                            onClick = {
                                taskEditionScreenViewModel.deleteTask()
                                goBack()
                            }
                        ) {
                            Icon(Icons.Default.Delete, contentDescription = null)
                        }
                    }
                    IconButton(
                        onClick = {
                            taskEditionScreenViewModel.saveChanges()
                            goBack()
                        }
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null)
                    }
                }
            )
        }
    ) { innerPadding ->
        TaskEditionScreen(
            state = taskEditionScreenViewModel.state,
            events = TaskEditionEvents(
                onTitleChanged = taskEditionScreenViewModel::onTitleChanged,
                onDescriptionChanged = taskEditionScreenViewModel::onDescriptionChanged,
                onPriorityChanged = taskEditionScreenViewModel::onPriorityChanged,
                onCompletedChanged = taskEditionScreenViewModel::onIsCompletedChanged,
                onDueDateChanged = taskEditionScreenViewModel::onDateChanged
            ),
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskEditionScreen(
    state: TaskEditionScreenState,
    events: TaskEditionEvents,
    modifier: Modifier
) {
    var showDatePicker = remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState())
            .padding(vertical = 8.dp, horizontal = 14.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Titulo")
            },
            value = state.title,
            onValueChange = events.onTitleChanged,
            singleLine = true,
            isError = state.isTitleError,
        )
        TextField(
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Descripcion")
            },
            value = state.description ?: "Sin descripción",
            onValueChange = events.onDescriptionChanged,
        )
        ElevatedButton(
            onClick = {
                showDatePicker.value = true
            }
        ) {
            Text("Fecha de vencimiento")
        }
        Text("Prioridad")
        SingleChoiceSegmentedButtonRow(
            space = (-6).dp
        ) {
            SegmentedButton(
                selected = state.priority == 0,
                onClick = {
                    events.onPriorityChanged(0)
                },
                shape = CircleShape
            ) {
                Text("Baja")
            }

            SegmentedButton(
                selected = state.priority == 1,
                onClick = {
                    events.onPriorityChanged(1)
                },
                shape = CircleShape
            ) {
                Text("Media")
            }

            SegmentedButton(
                selected = state.priority == 2,
                onClick = {
                    events.onPriorityChanged(2)
                },
                shape = CircleShape
            ) {
                Text("Alta")
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = state.isCompleted,
                onCheckedChange = events.onCompletedChanged
            )
            Text("Completado")
        }
        if(showDatePicker.value){
            ShowDatePickerDialog(
                onDismiss = { showDatePicker.value = false },
                onClick = { events.onDueDateChanged(datePickerState.selectedDateMillis) },
                datePickerState
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowDatePickerDialog(onDismiss : () -> Unit,onClick : () -> Unit,datePickerState: DatePickerState){
    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = {
                    onClick()
                    onDismiss()
                }
            ) {
                Text("Aceptar")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}