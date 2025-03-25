package com.edwardpratt.thedailyforecast.model

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [ExpenseEntity::class, IncomeEntity::class, CategoryEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun expenseDao(): ExpenseDao

    abstract fun incomeDao(): IncomeDao

    abstract fun categoryDao(): CategoryDao
}