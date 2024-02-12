package com.example.retocji.ui.viewmodels.logIn

import android.util.Log
import android.util.Patterns
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retocji.data.sources.remote.ApiService
import com.example.retocji.domain.models.AuthRequest
import com.example.retocji.domain.models.citas.CitasDTO
import com.example.retocji.domain.repositories.SharedPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val sharedPreferencesRepository: SharedPreferencesRepository,
    private val apiService: ApiService
) : ViewModel() {
    private var _email = MutableLiveData("")
    val email: LiveData<String> = _email

    private var _password = MutableLiveData("")
    val password: LiveData<String> = _password

    private var _passwordVisibility = MutableLiveData(false)
    val passwordVisibility: LiveData<Boolean> = _passwordVisibility

    private val _loginResult = MutableLiveData<String>()
    var loginResult: LiveData<String> = _loginResult

    private val _loginSuccess = MutableLiveData<Boolean>()
    val loginSuccess: LiveData<Boolean> = _loginSuccess



    fun login(username: String, password: String, onLoginComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val authRequest = AuthRequest(username, password)
                val response = apiService.login(authRequest)
                if (response.isSuccessful) {
                    _loginSuccess.value = true // Inicio de sesión exitoso
                    val token = response.body()?.token
                    if (token != null) {
                        sharedPreferencesRepository.saveAuthToken(token)

                    } else {
                        // Manejar el caso de respuesta vacía si es necesario
                    }
                } else {
                    _loginSuccess.value = false // Respuesta no exitosa
                    _loginResult.value = "Error: respuesta no exitosa - ${response.code()}"
                }
                // Llamar a la función de callback con el resultado
                onLoginComplete(_loginSuccess.value!!)
            } catch (e: Exception) {
                Log.e("LoginError", "Error en login", e)
                _loginSuccess.value = false // Error durante el inicio de sesión
                // Llamar a la función de callback con el resultado
                onLoginComplete(_loginSuccess.value!!)
            }
        }
    }



    fun userProfile() {
        viewModelScope.launch {
            try {
                val token = "Bearer ${loginResult.value}"
                val response = apiService.userProfile(token) // Usando
                if (response.isSuccessful) {
                    Log.e("Exito", response.body()!!.string())
                } else {
                    val code = response.code()
                    if (code == 403) {
                        Log.e("Fracaso", "Acceso prohibido: Token no válido")
                        Log.e("INFO", token)
                        // Manejar el caso de acceso prohibido aquí, por ejemplo, mostrar un mensaje al usuario
                    } else {
                        val errorResponse = response.errorBody()?.string()
                        Log.e("Fracaso", errorResponse ?: "Error desconocido")
                    }
                }
            } catch (e: Exception) {
                Log.e("Error", "Error en la creación de la cita", e)
            }
        }
    }

    fun onEmailChanged(newEmail: String) {
        _email.value = newEmail
        validateFields()
    }

    fun onPasswordChanged(newPassword: String) {
        _password.value = newPassword
        validateFields()
    }

    fun togglePasswordVisibility() {
        _passwordVisibility.value = !_passwordVisibility.value!!
    }

    fun validateFields(): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(_email.value)
            .matches() && _password.value!!.length >= 6
    }


    fun viewIcon(): ImageVector {
        return if (_passwordVisibility.value == true) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
    }

    fun changeColor(firstColor: Color, secondColor: Color): Color {
        return if (validateFields()) firstColor else secondColor
    }
}