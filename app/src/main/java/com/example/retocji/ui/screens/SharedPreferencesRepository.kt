package com.example.retocji.ui.screens

import android.content.SharedPreferences
import javax.inject.Inject

class SharedPreferencesRepository @Inject constructor(private val sharedPreferences: SharedPreferences) {

    fun saveAuthToken(token: String) {
        sharedPreferences.edit().putString("auth_token", token).apply()
    }

    fun getAuthToken(): String? {
        return sharedPreferences.getString("auth_token", null)
    }

    // Agrega más funciones según sea necesario para manejar diferentes preferencias
}