package com.example.practicalistatareas.base

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.toStringDate() : String?{
    val date = Date(this)

    return SimpleDateFormat("d MMMM yyyy", Locale.getDefault()).format(date)
}