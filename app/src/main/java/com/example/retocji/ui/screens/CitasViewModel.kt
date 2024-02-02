package com.example.retocji.ui.screens

import androidx.lifecycle.ViewModel
import com.example.retocji.domain.repositories.ApiService
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