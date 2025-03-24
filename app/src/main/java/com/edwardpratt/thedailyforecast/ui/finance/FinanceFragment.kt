package com.edwardpratt.thedailyforecast.ui.finance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.edwardpratt.thedailyforecast.databinding.FragmentFinanceBinding
import com.edwardpratt.thedailyforecast.model.Expense
import com.edwardpratt.thedailyforecast.model.ExpenseEntity

class FinanceFragment : Fragment() {
    private var _binding: FragmentFinanceBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: FinanceViewModel
    private lateinit var expenseAdapter: ExpenseAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFinanceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(FinanceViewModel::class.java)

        // Set up RecyclerView
        binding.rvExpenses.layoutManager = LinearLayoutManager(requireContext())
        expenseAdapter = ExpenseAdapter(emptyList())
        binding.rvExpenses.adapter = expenseAdapter

        // Observe expenses and income
        viewModel.expenses.observe(viewLifecycleOwner) { expenses ->
            expenseAdapter = ExpenseAdapter(expenses)
            binding.rvExpenses.adapter = expenseAdapter
        }
        viewModel.incomes.observe(viewLifecycleOwner) { total ->
            binding.tvIncomeTotal.text = "Total Income: $total€"
        }

        // Button to add expense (for simplicity, we add a sample expense)
        binding.btnAddExpense.setOnClickListener {
            viewModel.addExpense(
                ExpenseEntity(
                    amount = 50.0,
                    category = "Food",
                    description = "Dinner",
                    date = "2025-03-24"
                )
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}