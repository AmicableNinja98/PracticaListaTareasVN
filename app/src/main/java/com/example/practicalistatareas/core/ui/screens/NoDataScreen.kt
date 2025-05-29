package com.example.practicalistatareas.core.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun NoDataScreen(modifier: Modifier){
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Text("No hay tareas para mostrar")
    }
}