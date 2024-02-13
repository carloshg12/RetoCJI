package com.example.retocji.ui.viewmodels

import android.util.Log
import android.util.Patterns
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retocji.data.sources.remote.ApiService
import com.example.retocji.domain.models.AuthRequest
import com.example.retocji.domain.models.email.ContactDTO
import com.example.retocji.domain.models.logIn.RegisterUserDTO
import com.example.retocji.domain.repositories.SharedPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistroViewModel @Inject constructor(
    private val apiService: ApiService,
    private val sharedPreferencesRepository: SharedPreferencesRepository
) : ViewModel() {
    val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username
    val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password
    val _email = MutableLiveData<String>("")
    val email: LiveData<String> = _email
    private val _mensajeError = MutableLiveData<String?>()
    val mensajeError: LiveData<String?> = _mensajeError


    private val _registroExitoso = MutableLiveData<Boolean>()
    val registroExitoso: MutableLiveData<Boolean> = _registroExitoso

    fun registrarUsuario(registerUserDTO: RegisterUserDTO) {

        if (!validateEmail()) {
            _mensajeError.value = "Correo electrónico no válido."
            _registroExitoso.value = false
            return
        }

        viewModelScope.launch {
            try {

                val response = apiService.addNewUser(registerUserDTO)
                val responseBodyString = response.body()?.string() ?: ""

                if (response.isSuccessful) {
                    if (responseBodyString == "User Added Successfully") {
                        _registroExitoso.value = true
                        _mensajeError.value = ""
                        Log.d("RegistroViewModel", "Registro exitoso: $responseBodyString")
                    } else {
                        _registroExitoso.value = false
                        _mensajeError.value = responseBodyString
                        Log.d("RegistroViewModel", "Registro fallido: $responseBodyString")

                    }
                } else {
                    _registroExitoso.value = false
                    _mensajeError.value = response.errorBody()?.string() ?: "Error desconocido"
                    val errorBody = response.errorBody()?.string()
                    Log.e(
                        "RegistroViewModel",
                        "Error en el registro: ${response.code()}, $errorBody"
                    )
                }
            } catch (e: Exception) {
                _registroExitoso.value = false
                _mensajeError.value = e.message ?: "Error desconocido"
                Log.e("RegistroViewModel", "Error durante el registro", e)
            }
        }
    }


    fun login(username: String, password: String) {
        viewModelScope.launch {
            try {
                val authRequest = AuthRequest(username, password)
                val response = apiService.login(authRequest)
                if (response.isSuccessful) {

                    val token = response.body()?.token
                    if (token != null) {
                        sharedPreferencesRepository.saveAuthToken(token)
                    } else {}
                }

            } catch (e: Exception) {
                Log.e("LoginError", "Error en login", e)

            }
        }
    }

    fun sendEmail(email: String, username: String) {
        viewModelScope.launch {
            val contact = ContactDTO(email,"Bienvenido", "Hola $username.\nBienvenido a I&M Asesores.\nEsperamos poder ayudarte en todo lo necesario.")
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

    fun onRegistroChanged(registro: Boolean) {
        _registroExitoso.value = registro
    }

    fun onUserChanged(newUsername: String) {
        _username.value = newUsername
    }

    fun onEmailChanged(newEmail: String) {
        _email.value = newEmail

    }

    fun onPasswordChanged(newPassword: String) {
        _password.value = newPassword
    }


    fun validateFields(): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(_email.value)
            .matches() && _password.value!!.length >= 6
    }

    fun validateEmail(): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(_email.value).matches()
    }

    fun changeColor(firstColor: Color, secondColor: Color): Color {
        return if (validateFields()) firstColor else secondColor
    }
}
