package com.example.retocji.ui.screens

import androidx.lifecycle.ViewModel

class AuthViewModel : ViewModel() {
    private var authToken: String? = null

    fun setAuthToken(token: String) {
        authToken = token
    }

    fun getAuthToken(): String? {
        return authToken
    }
}
