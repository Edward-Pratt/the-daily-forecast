package com.edwardpratt.thedailyforecast.ui.finance

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.edwardpratt.thedailyforecast.R
import com.edwardpratt.thedailyforecast.model.IncomeEntity


class IncomeAdapter(private var incomes: List<IncomeEntity>,
                    private val onDeleteClick: (IncomeEntity) -> Unit,
                    private var currency: String
) : RecyclerView.Adapter<IncomeAdapter.IncomeViewHolder>() {

    class IncomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvCategory: TextView = itemView.findViewById(R.id.tvCategory)
        val tvAmount: TextView = itemView.findViewById(R.id.tvAmount)
        val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        val btnDelete: ImageButton = itemView.findViewById(R.id.btnDeleteIncome)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IncomeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_income, parent, false)
        return IncomeViewHolder(view)
    }


    override fun onBindViewHolder(holder: IncomeViewHolder, position: Int) {
        val income = incomes[position]
        holder.tvCategory.text = income.category
        holder.tvAmount.text = "$currency${income.amount}"
        holder.tvDescription.text = income.description
        holder.tvDate.text = income.date

        holder.btnDelete.setOnClickListener {
            onDeleteClick(income)
        }
    }


    override fun getItemCount(): Int {
        return incomes.size
    }

    fun updateData(newIncomes: List<IncomeEntity>, newCurrency: String){
        incomes = newIncomes
        currency = newCurrency
        notifyDataSetChanged()
    }


}