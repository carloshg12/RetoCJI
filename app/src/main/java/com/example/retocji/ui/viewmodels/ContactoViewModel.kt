package com.example.retocji.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retocji.data.sources.remote.ApiService
import com.example.retocji.domain.models.email.ContactDTO
import com.example.retocji.domain.repositories.SharedPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactoViewModel @Inject constructor(
    private val sharedPreferencesRepository: SharedPreferencesRepository,
    private val apiService: ApiService
) : ViewModel() {

    fun sendEmail(userName: String, email: String, asunto: String, mensaje: String) {
        viewModelScope.launch {
            val contact = ContactDTO(email,"De: $userName - $asunto",mensaje)
            try {
                val authToken = "Bearer " + sharedPreferencesRepository.getAuthToken()
                val response = apiService.contactEmail(authToken, contact)
                if (response.isSuccessful) {
                    Log.d("EmailSuccess", "Correo enviado correctamente")
                } else {
                    Log.e("EmailError", "Error al enviar el correo: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("EmailException", "Excepción al enviar el correo: ${e.message}")
            }
        }
    }
}