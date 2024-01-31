package com.example.login

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
import com.example.retocji.domain.repositories.AuthRequest
import com.example.retocji.domain.repositories.Citas
import com.example.retocji.domain.repositories.RetrofitInstance
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private var _email = MutableLiveData("")
    val email: LiveData<String> = _email

    private var _password = MutableLiveData("")
    val password: LiveData<String> = _password

    private var _passwordVisibility = MutableLiveData(false)
    val passwordVisibility: LiveData<Boolean> = _passwordVisibility

    private val _loginResult = MutableLiveData<String>()
    var loginResult: LiveData<String> = _loginResult

    fun login(username: String, password: String) {
        viewModelScope.launch {
            try {
                val authRequest = AuthRequest(username, password)
                val response = RetrofitInstance.api.login(authRequest)
                if (response.isSuccessful) {
                    _loginResult.value = (response.body()?.token ?: "Respuesta vacía").toString()
                } else {
                    _loginResult.value = "Error: respuesta no exitosa - ${response.code()}"
                }
            } catch (e: Exception) {
                Log.e("LoginError", "Error en login", e)
                _loginResult.value = "Error: ${e.message}"
            }
        }
    }

    fun crearCita(citas: Citas) {
        viewModelScope.launch {
            val token = "Bearer $loginResult" // Reemplazar con tu función para obtener el token
            val response = RetrofitInstance.api.crearCita(token, citas)
            if (response.isSuccessful) {
            Log.e("Exito", "Cita creada")
            } else {
                Log.e("Fracaso","No va")
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
        return Patterns.EMAIL_ADDRESS.matcher(_email.value).matches() && _password.value!!.length >= 6
    }

    fun viewIcon(): ImageVector  {
        return if (_passwordVisibility.value == true) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
    }

    fun changeColor(firstColor: Color, secondColor: Color): Color {
        return if (validateFields()) firstColor else secondColor
    }
}
