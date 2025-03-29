package com.edwardpratt.thedailyforecast.ui.finance

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter

class CategoryAdapter(
    context: Context,
    resource: Int,
    private var items: List<String>,
    private val onLongClick: (String) -> Unit
) : ArrayAdapter<String>(context, resource, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        view.setOnLongClickListener {
            val item = getItem(position)
            // Don't allow long click on "Add New Category..."
            if (item != null && item != "Add New Category...") {
                onLongClick(item)
            }
            true
        }
        return view
    }

    // Override the getDropDownView method to set the long click listener for dropdown items
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent)
        view.setOnLongClickListener {
            val item = getItem(position)
            if (item != null && item != "Add New Category...") {
                onLongClick(item)
            }
            true
        }
        return view
    }

    // Optionally, provide a method to update the adapter's data
    fun updateItems(newItems: List<String>) {
        items = newItems
        clear()
        addAll(newItems)
        notifyDataSetChanged()
    }
}
