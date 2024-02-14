package com.example.retocji.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.retocji.data.sources.local.SharedPreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val sharedPreferencesManager: SharedPreferencesManager
) : ViewModel() {
    private var authToken: String? = null
    suspend fun setAuthToken(token: String) {
        authToken = token
        sharedPreferencesManager.saveAuthToken(token)
    }
    suspend fun getAuthToken(): String? {
        return authToken
    }
}
