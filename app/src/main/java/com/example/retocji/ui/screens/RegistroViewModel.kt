package com.example.retocji.ui.screens

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
import com.example.retocji.domain.repositories.ApiService
import com.example.retocji.domain.repositories.AuthRequest
import com.example.retocji.domain.repositories.RegisterUserDTO
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
    val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _registroExitoso = MutableLiveData<Boolean>()
    val registroExitoso: LiveData<Boolean> = _registroExitoso

    fun registrarUsuario(registerUserDTO: RegisterUserDTO) {
        viewModelScope.launch {
            try {

                val response = apiService.addNewUser(registerUserDTO)
                val responseBodyString = response.body()?.string() ?: ""

                if (response.isSuccessful) {
                    if (responseBodyString == "User Added Successfully") {
                        _registroExitoso.value = true
                        Log.d("RegistroViewModel", "Registro exitoso: $responseBodyString")
                    }else{
                        _registroExitoso.value = false
                        Log.d("RegistroViewModel", "Registro fallido: $responseBodyString")

                    }
                } else {
                    _registroExitoso.value = false
                    val errorBody = response.errorBody()?.string()
                    Log.e(
                        "RegistroViewModel",
                        "Error en el registro: ${response.code()}, $errorBody"
                    )
                }
            } catch (e: Exception) {
                _registroExitoso.value = false
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
                    } else {
                        // Manejar el caso de respuesta vacía si es necesario
                    }
                }

            } catch (e: Exception) {
                Log.e("LoginError", "Error en login", e)

            }
        }
    }

    fun obtenerTokenDesdeSharedPreferences(): String? {
        return sharedPreferencesRepository.getAuthToken()
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


    fun changeColor(firstColor: Color, secondColor: Color): Color {
        return if (validateFields()) firstColor else secondColor
    }

}



