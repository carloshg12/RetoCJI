package com.example.retocji.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retocji.domain.repositories.ApiService
import com.example.retocji.ui.screens.logIn.SharedPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class CitasViewModel @Inject constructor(
    private val sharedPreferencesRepository: SharedPreferencesRepository,
    private val apiService: ApiService
) : ViewModel() {
    private val _gestoresLiveData = MutableLiveData<List<String>>()
    val gestoresLiveData: LiveData<List<String>> = _gestoresLiveData

    init {
        obtenerGestores()
    }


    fun obtenerTokenDesdeSharedPreferences(): String? {
        return sharedPreferencesRepository.getAuthToken()
    }

     fun obtenerGestores() {
        viewModelScope.launch {
            try {
                // Suponiendo que tu SharedPreferencesRepository maneja la posibilidad de un token nulo correctamente
                val token = obtenerTokenDesdeSharedPreferences() ?: return@launch
                val response = apiService.getGestores("Bearer $token")
                if (response.isSuccessful) {
                    // Actualizar _gestoresLiveData con la lista de gestores obtenida
                    _gestoresLiveData.postValue(response.body() ?: emptyList())
                } else {
                    Log.e("CitasViewModel", "Error al obtener gestores: ${response.errorBody()?.string()}")
                    _gestoresLiveData.postValue(emptyList())
                }
            } catch (e: Exception) {
                Log.e("CitasViewModel", "Excepción al obtener gestores: ${e.message}")
                _gestoresLiveData.postValue(emptyList())
            }
        }
    }

}