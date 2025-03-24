package com.edwardpratt.thedailyforecast.model


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val amount: Double,
    val category: String,
    val description: String?,
    val date: String
)
