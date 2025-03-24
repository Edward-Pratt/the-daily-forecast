package com.edwardpratt.thedailyforecast.model

data class Income(
    val id: Long = 0L,
    val amount: Double,
    val category: String,
    val description: String?,
    val date: String
)
