package com.edwardpratt.thedailyforecast.ui.finance

import android.R.id.input
import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.edwardpratt.thedailyforecast.R
import com.edwardpratt.thedailyforecast.databinding.FragmentFinanceBinding
import com.edwardpratt.thedailyforecast.model.CategoryEntity
import com.edwardpratt.thedailyforecast.model.ExpenseEntity
import com.edwardpratt.thedailyforecast.model.IncomeEntity

class FinanceFragment : Fragment() {
    private var _binding: FragmentFinanceBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: FinanceViewModel
    private lateinit var expenseAdapter: ExpenseAdapter
    private lateinit var incomeAdapter: IncomeAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFinanceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[FinanceViewModel::class.java]



        binding.rvIncomes.layoutManager = LinearLayoutManager(requireContext())
        incomeAdapter = IncomeAdapter(emptyList()) { income ->
            viewModel.deleteIncome(income)
        }
        binding.rvIncomes.adapter = incomeAdapter

        // Set up RecyclerView
        binding.rvExpenses.layoutManager = LinearLayoutManager(requireContext())
        expenseAdapter = ExpenseAdapter(emptyList()) { expense ->
            viewModel.deleteExpense(expense)
        }
        binding.rvExpenses.adapter = expenseAdapter

        viewModel.incomes.observe(viewLifecycleOwner) { incomes ->
            incomeAdapter.updateData(incomes)
            binding.rvIncomes.adapter = incomeAdapter
        }

        // Observe expenses and income
        viewModel.expenses.observe(viewLifecycleOwner) { expenses ->
            expenseAdapter.updateData(expenses)
            binding.rvExpenses.adapter = expenseAdapter
        }

        viewModel.incomes.observe(viewLifecycleOwner) { incomes ->
            val totalIncome = incomes.sumOf { it.amount }
            binding.tvTotalIncome.text = "Total Incomes: $totalIncome"
        }

        viewModel.expenses.observe(viewLifecycleOwner) { expenses ->
            val totalExpense = expenses.sumOf { it.amount }
            binding.tvTotalExpenses.text = "Total Expenses: $totalExpense"
        }





        binding.btnAddIncome.setOnClickListener {
            showAddTransactionDialog(isExpense = false)
        }


        binding.btnAddExpense.setOnClickListener {
            showAddTransactionDialog(isExpense = true)
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun showAddCategoryDialog(isExpense: Boolean, spinnerCategory: Spinner) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(if (isExpense) "Add Expense Category" else "Add Income Category")

        val input = EditText(requireContext())
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)

        builder.setPositiveButton("Add") { _, _ ->
            val newCategory = input.text.toString().trim()
            if (newCategory.isNotEmpty()) {
                val categoryType = if (isExpense) "expense" else "income"
                viewModel.addCategory(newCategory, categoryType)  // Add Category

                // After adding the category, update the spinner
                viewModel.allCategories.observe(viewLifecycleOwner) { categories ->
                    Log.d("Categories", "Categories in DB after adding: $categories")
                    val categoryType = if (isExpense) "expense" else "income"
                    val filteredCategories = categories.filter { it.categoryType == categoryType }
                    val categoryNames = filteredCategories.map { it.name } + "Add New Category..."

                    // Update the spinner with the new categories
                    val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, categoryNames)
                    spinnerCategory.adapter = adapter
                }
            }
        }

        builder.setNegativeButton("Cancel", null)
        builder.show()
    }







    private fun showAddTransactionDialog(isExpense: Boolean) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_transaction, null)
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle(if (isExpense) "Add Expense" else "Add Income")
            .setView(dialogView)
            .setNegativeButton("Cancel", null)
            .create()
        dialog.show()

        // Get views from the dialog
        val spinnerCategory = dialogView.findViewById<Spinner>(R.id.spinnerCategory)
        val btnAddNewCategory = dialogView.findViewById<Button>(R.id.btnAddNewCategory)
        val etAmount = dialogView.findViewById<EditText>(R.id.etAmount)
        val etDescription = dialogView.findViewById<EditText>(R.id.etDescription)
        val etDate = dialogView.findViewById<EditText>(R.id.etDate)
        val btnSaveTransaction = dialogView.findViewById<Button>(R.id.btnSaveTransaction)

        viewModel.allCategories.observe(viewLifecycleOwner) { categories ->
            Log.d("Categories", "Categories in DB: $categories")
            val categoryType = if (isExpense) "expense" else "income"
            val filteredCategories = categories.filter { it.categoryType == categoryType }
            val categoryNames = filteredCategories.map { it.name } + "Add New Category..."
            val adapter = CategoryAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, categoryNames) { categoryName ->
                // This lambda is triggered on long-click.
                // Show confirmation dialog for deletion:
                AlertDialog.Builder(requireContext())
                    .setTitle("Delete Category")
                    .setMessage("Are you sure you want to delete the category \"$categoryName\"?")
                    .setPositiveButton("Delete") { _, _ ->
                        viewModel.deleteCategoryByName(categoryName, categoryType)
                        // Optionally, show a Toast:
                        Toast.makeText(requireContext(), "Category deleted", Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton("Cancel", null)
                    .show()
            }
            spinnerCategory.adapter = adapter
        }




        // If user selects "Add New Category...", show the add category dialog
        spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedCategory = parent.getItemAtPosition(position).toString()
                if (selectedCategory == "Add New Category...") {
                    showAddCategoryDialog(isExpense, spinnerCategory)  // Now categoryType is defined and accessible
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Handle case when nothing is selected
            }
        }



        // Set up date picker for etDate
        etDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(requireContext(),
                { _, year, month, dayOfMonth ->
                    etDate.setText("$year-${month + 1}-$dayOfMonth")
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        // Save transaction when the button is clicked
        btnSaveTransaction.setOnClickListener {
            val category = spinnerCategory.selectedItem.toString()
            val amountText = etAmount.text.toString()
            if (amountText.isBlank()) {
                etAmount.error = "Enter an amount"
                return@setOnClickListener
            }
            val amount = amountText.toDoubleOrNull() ?: 0.0
            val description = etDescription.text.toString()
            val date = etDate.text.toString()
            if (date.isBlank()) {
                etDate.error = "Select a date"
                return@setOnClickListener
            }
            // For expense, create ExpenseEntity; for income, create IncomeEntity.
            if (isExpense) {
                viewModel.addExpense(ExpenseEntity(amount = amount, category = category, description = description, date = date))
            } else {
                viewModel.addIncome(IncomeEntity(amount = amount, category = category, description = description, date = date))
            }
            dialog.dismiss()
        }
    }




}