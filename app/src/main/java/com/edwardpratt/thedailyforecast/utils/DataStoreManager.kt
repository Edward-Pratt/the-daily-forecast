package com.edwardpratt.thedailyforecast.utils

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Define the dataStore extension for Context
private val Context.dataStore by preferencesDataStore(name = "user_prefs")

class DataStoreManager(private val context: Context) {

    // Define the key for storing currency
    private val CURRENCY_KEY = stringPreferencesKey("currency")

    // Flow to get the saved currency, defaulting to "£" if not set
    val currencyFlow: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[CURRENCY_KEY] ?: "£" // Default to GBP if no currency is stored
        }

    // Function to save the currency
    suspend fun setCurrency(currency: String) {
        context.dataStore.edit { preferences ->
            preferences[CURRENCY_KEY] = currency
        }
    }
}
