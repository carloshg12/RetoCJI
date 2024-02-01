package com.example.retocji.ui.screens

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "app_preferences")

class SharedPreferencesManager(private val context: Context) {

    private val dataStore = context.dataStore

    suspend fun saveAuthToken(token: String) {
        dataStore.edit { preferences ->
            preferences[STRING_PREFERENCE_KEY] = token
        }
    }

    val authToken: Flow<String?> = dataStore.data.map { preferences ->
        preferences[STRING_PREFERENCE_KEY]
    }

    companion object {
        private val STRING_PREFERENCE_KEY = stringPreferencesKey("auth_token")
    }
}
