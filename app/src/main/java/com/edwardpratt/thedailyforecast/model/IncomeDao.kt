package com.edwardpratt.thedailyforecast.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface IncomeDao {
    @Insert
    suspend fun insertIncome(income: IncomeEntity)

    @Query("SELECT * FROM incomes ORDER BY date DESC")
    suspend fun getAllIncomes(): List<IncomeEntity>

    @Query("DELETE FROM incomes WHERE id = :incomeId")
    suspend fun deleteIncome(incomeId: Long)
}