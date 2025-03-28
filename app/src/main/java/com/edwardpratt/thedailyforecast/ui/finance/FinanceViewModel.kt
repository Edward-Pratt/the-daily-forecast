package com.edwardpratt.thedailyforecast.ui.finance

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.edwardpratt.thedailyforecast.model.CategoryEntity
import com.edwardpratt.thedailyforecast.model.ExpenseEntity
import com.edwardpratt.thedailyforecast.model.IncomeEntity
import com.edwardpratt.thedailyforecast.repository.FinanceRepository
import com.edwardpratt.thedailyforecast.utils.DataStoreManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext



class FinanceViewModel(application: Application) : AndroidViewModel(application) {
    private val dataStore = DataStoreManager(application)
    private val repository = FinanceRepository(application)


    val currency: LiveData<String> = dataStore.currencyFlow.asLiveData()

    private val _expenses = MutableLiveData<List<ExpenseEntity>>()
    val expenses: LiveData<List<ExpenseEntity>> get() = _expenses

    private val _incomes = MutableLiveData<List<IncomeEntity>>()
    val incomes: LiveData<List<IncomeEntity>> get() = _incomes

    // LiveData for all categories
    val allCategories: LiveData<List<CategoryEntity>> = repository.getCategories()

    init {
        loadFinanceData()
    }

    // Load expenses and incomes from the repository
    fun loadFinanceData() {
        viewModelScope.launch(Dispatchers.IO) {
            val expensesList = repository.getExpenses()
            val incomesList = repository.getIncomes()

            withContext(Dispatchers.Main) {
                _expenses.value = expensesList
                _incomes.value = incomesList
            }
        }
    }

    // Add a new expense
    fun addExpense(expense: ExpenseEntity) {
        viewModelScope.launch {
            repository.addExpense(expense)
            loadFinanceData() // Refresh data after insertion
        }
    }

    // Delete an expense
    fun deleteExpense(expense: ExpenseEntity) {
        viewModelScope.launch {
            repository.deleteExpense(expense)
            loadFinanceData()
        }
    }

    // Add a new income
    fun addIncome(income: IncomeEntity) {
        viewModelScope.launch {
            repository.addIncome(income)
            loadFinanceData() // Refresh data after insertion
        }
    }

    // Delete an income
    fun deleteIncome(income: IncomeEntity) {
        viewModelScope.launch {
            repository.deleteIncome(income)
            loadFinanceData()
        }
    }

    // Add a new category
    fun addCategory(name: String, type: String) {
        val category = CategoryEntity(name = name, categoryType = type)  // Create a CategoryEntity
        viewModelScope.launch {
            repository.insertCategory(category)  // Pass the CategoryEntity
        }

        val updatedCategories = repository.getCategories()
    }

    fun deleteCategoryByName(name: String, type: String) {
        viewModelScope.launch {
            repository.deleteCategoryByName(name, type)
        }
    }


    fun updateCurrency(currency: String) {
        viewModelScope.launch {
            dataStore.setCurrency(currency)
        }
    }

}
