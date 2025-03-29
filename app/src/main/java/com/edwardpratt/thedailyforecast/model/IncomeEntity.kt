package com.edwardpratt.thedailyforecast.model

import androidx.room.Entity
import androidx.room.PrimaryKey


// Represents an income in the database
@Entity(tableName = "incomes")
data class IncomeEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val amount: Double,
    val category: String,
    val description: String?,
    val date: String
)