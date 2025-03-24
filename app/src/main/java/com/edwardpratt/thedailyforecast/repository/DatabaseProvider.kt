package com.edwardpratt.thedailyforecast.repository

import android.content.Context
import androidx.room.Room
import com.edwardpratt.thedailyforecast.model.AppDatabase

object DatabaseProvider {
    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "finance_database"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}
