package com.example.retocji.ui.screens

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore by preferencesDataStore(name = "app_preferences")


class SharedPreferencesManager @Inject constructor(private val application: Application) {


    private val dataStore = application.applicationContext.dataStore

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
