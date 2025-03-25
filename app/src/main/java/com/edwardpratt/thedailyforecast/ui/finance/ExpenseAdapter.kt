package com.edwardpratt.thedailyforecast.ui.finance

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.edwardpratt.thedailyforecast.R
import com.edwardpratt.thedailyforecast.model.ExpenseEntity
import com.edwardpratt.thedailyforecast.ui.NewsAdapter

class ExpenseAdapter(private var expenses: List<ExpenseEntity>,
    private val onDeleteClick: (ExpenseEntity) -> Unit
) : RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {

    class ExpenseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvCategory: TextView = itemView.findViewById(R.id.tvCategory)
        val tvAmount: TextView = itemView.findViewById(R.id.tvAmount)
        val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        val btnDelete: ImageButton = itemView.findViewById(R.id.btnDeleteExpense)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_expense, parent, false)
        return ExpenseViewHolder(view)
    }


    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val expense = expenses[position]
        holder.tvCategory.text = expense.category
        holder.tvAmount.text = expense.amount.toString()
        holder.tvDescription.text = expense.description
        holder.tvDate.text = expense.date

        holder.btnDelete.setOnClickListener {
            onDeleteClick(expense)
        }
    }


    override fun getItemCount(): Int {
        return expenses.size
    }

    fun updateData(newExpenses: List<ExpenseEntity>){
        expenses = newExpenses
        notifyDataSetChanged()
    }
}