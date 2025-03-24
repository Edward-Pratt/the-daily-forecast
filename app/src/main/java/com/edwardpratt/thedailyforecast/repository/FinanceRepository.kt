package com.edwardpratt.thedailyforecast.repository

import android.content.Context
import com.edwardpratt.thedailyforecast.model.ExpenseEntity
import com.edwardpratt.thedailyforecast.model.IncomeEntity


class FinanceRepository(context: Context) {

    private val expenseDao = DatabaseProvider.getDatabase(context).expenseDao()
    private val incomeDao = DatabaseProvider.getDatabase(context).incomeDao()

    suspend fun addExpense(expense: ExpenseEntity) {
        expenseDao.insertExpense(expense)
    }

    suspend fun addIncome(income: IncomeEntity) {
        incomeDao.insertIncome(income)
    }

    suspend fun getExpenses(): List<ExpenseEntity> {
        return expenseDao.getAllExpenses()
    }

    suspend fun getIncomes(): List<IncomeEntity> {
        return incomeDao.getAllIncomes()
    }
}