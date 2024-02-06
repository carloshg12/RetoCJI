package com.example.retocji.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.retocji.domain.repositories.ApiService
import com.example.retocji.ui.screens.logIn.SharedPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class UserNameViewModel @Inject constructor(
    private val sharedPreferencesRepository: SharedPreferencesRepository,
    private val apiService: ApiService
) : ViewModel() {

    fun getUserName() = liveData(Dispatchers.IO) {
        emit("Cargando...") // Emitir un valor inicial mientras se carga el nombre de usuario
        val token = sharedPreferencesRepository.getAuthToken()
        if (token != null) {
            try {
                val response = apiService.getUserName("Bearer $token")
                if (response.isSuccessful) {
                    emit(response.body() ?: "Nombre de usuario no encontrado")
                } else {
                    emit("Error al obtener el nombre de usuario: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                emit("Error de conexión: ${e.message}")
            }
        } else {
            //emit("Token no encontrado")
        }
    }
}
