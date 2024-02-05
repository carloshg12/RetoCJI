package com.example.retocji.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.retocji.domain.repositories.ApiService
import com.example.retocji.ui.screens.logIn.SharedPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CitasViewModel @Inject constructor(
    private val sharedPreferencesRepository: SharedPreferencesRepository,
    private val apiService: ApiService
) : ViewModel() {

    fun obtenerTokenDesdeSharedPreferences(): String? {
        return sharedPreferencesRepository.getAuthToken()
    }
}