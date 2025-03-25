package com.edwardpratt.thedailyforecast.repository

import android.content.Context
import android.provider.ContactsContract
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.edwardpratt.thedailyforecast.model.CategoryEntity
import com.edwardpratt.thedailyforecast.model.ExpenseEntity
import com.edwardpratt.thedailyforecast.model.IncomeEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class FinanceRepository(context: Context) {

    private val expenseDao = DatabaseProvider.getDatabase(context).expenseDao()
    private val incomeDao = DatabaseProvider.getDatabase(context).incomeDao()
    private val categoryDao = DatabaseProvider.getDatabase(context).categoryDao()



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

    suspend fun deleteExpense(expense: ExpenseEntity){
        withContext(Dispatchers.IO){
            expenseDao.deleteExpense(expense.id)
        }
    }

    suspend fun deleteIncome(income: IncomeEntity){
        withContext(Dispatchers.IO){
            incomeDao.deleteIncome(income.id)
        }
    }

    fun getCategories(): LiveData<List<CategoryEntity>> {
            return categoryDao.getAllCategories() // This returns a List<CategoryEntity>

    }

    suspend fun insertCategory(category: CategoryEntity) {
        categoryDao.insertCategory(category)
    }

    suspend fun deleteCategoryByName(name: String, type: String) {
        categoryDao.deleteCategoryByName(name, type)
    }


}