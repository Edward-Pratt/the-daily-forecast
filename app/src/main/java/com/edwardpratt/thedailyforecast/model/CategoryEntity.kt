package com.edwardpratt.thedailyforecast.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// Represents a category in the database
@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val categoryType: String
)