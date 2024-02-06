package com.example.retocji.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retocji.data.sources.remote.ApiService
import com.example.retocji.domain.repositories.SharedPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserNameViewModel @Inject constructor(
    private val sharedPreferencesRepository: SharedPreferencesRepository,
    private val apiService: ApiService
) : ViewModel() {

    private val _userName = MutableLiveData<String>("Cargando...")
    val userName: LiveData<String> = _userName

    init {
        getUserName()
    }

    private fun getUserName() {
        viewModelScope.launch {
            val token = sharedPreferencesRepository.getAuthToken()
            if (token != null) {
                try {
                    val response = apiService.getUserName("Bearer $token",token)
                    if (response.isSuccessful) {
                        val responseBodyString = response.body()?.string() ?: "Respuesta vacía"
                        _userName.value = responseBodyString
                        Log.e("USER",_userName.value!!)
                    } else {
                        _userName.value = "Error al obtener el nombre de usuario: ${response.errorBody()?.string()}"
                    }
                } catch (e: Exception) {
                    _userName.value = "Error de conexión: ${e.message}"
                }
            } else {
                _userName.value = "Token no encontrado"
            }
        }
    }
}

