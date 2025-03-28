package com.edwardpratt.thedailyforecast.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.edwardpratt.thedailyforecast.databinding.FragmentSettingsBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import com.edwardpratt.thedailyforecast.utils.DataStoreManager

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private lateinit var dataStoreManager: DataStoreManager

    // List of available currencies
    private val currencies = listOf("$", "€", "£", "₹", "¥", "₣")

    // Using lifecycleScope to launch coroutines
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataStoreManager = DataStoreManager(requireContext())
        // Inflate the layout for this fragment using View Binding
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        // Setup the spinner for currency selection
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCurrency.adapter = adapter

        // Get the stored currency and set the spinner selection
        lifecycleScope.launch {
            // Collect the stored currency value
            val storedCurrency = dataStoreManager.currencyFlow.first() // Get the saved currency
            val currencyPosition = currencies.indexOf(storedCurrency)
            if (currencyPosition >= 0) {
                binding.spinnerCurrency.setSelection(currencyPosition)
            }
        }

        // Handle currency selection change
        binding.spinnerCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, view: View?, position: Int, id: Long) {
                // Save the selected currency to DataStore
                val selectedCurrency = currencies[position]
                lifecycleScope.launch {
                    dataStoreManager.setCurrency(selectedCurrency) // Save the selected currency
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                // Do nothing if no item is selected
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Prevent memory leaks
    }
}
