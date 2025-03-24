package com.edwardpratt.thedailyforecast.ui.finance

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.edwardpratt.thedailyforecast.model.ExpenseEntity
import com.edwardpratt.thedailyforecast.model.IncomeEntity
import com.edwardpratt.thedailyforecast.repository.FinanceRepository
import kotlinx.coroutines.launch

class FinanceViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = FinanceRepository(application)

    private val _expenses = MutableLiveData<List<ExpenseEntity>>()
    val expenses: LiveData<List<ExpenseEntity>> get() = _expenses

    private val _incomes = MutableLiveData<List<IncomeEntity>>()
    val incomes: LiveData<List<IncomeEntity>> get() = _incomes

    init {
        loadExpenses()
        loadIncomes()
    }

    fun loadExpenses() {
        viewModelScope.launch {
            _expenses.value = repository.getExpenses()
        }
    }

    fun loadIncomes(){
        viewModelScope.launch {
            _incomes.value = repository.getIncomes()
        }
    }

    fun addExpense(expense: ExpenseEntity) {
        viewModelScope.launch {
            repository.addExpense(expense)
            loadExpenses() // Refresh data after insertion
        }
    }

    fun addIncome(income: IncomeEntity) {
        viewModelScope.launch {
            repository.addIncome(income)
            loadIncomes() // Refresh data after insertion
        }
    }
}