package com.example.retocji.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retocji.data.sources.remote.ApiService
import com.example.retocji.domain.models.citas.CitasDTO
import com.example.retocji.domain.repositories.SharedPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val sharedPreferencesRepository: SharedPreferencesRepository,
    private val apiService: ApiService
) : ViewModel() {

    private val _userName = MutableLiveData<String>("Cargando...")
    val userName: LiveData<String> = _userName

    private val _userEmail = MutableLiveData<String>()
    val userEmail: LiveData<String> = _userEmail

    private val _isTokenValid: MutableLiveData<Boolean> = MutableLiveData()
    val isTokenValid: LiveData<Boolean> = _isTokenValid

    private val _citasUsuario: MutableLiveData<List<CitasDTO>> = MutableLiveData()
    val citasUsuario: LiveData<List<CitasDTO>> = _citasUsuario

    private val _cantidadCitas: MutableLiveData<Int?> = MutableLiveData()
    val cantidadCitas: MutableLiveData<Int?> = _cantidadCitas

    init {
        getUserName()
        getUserEmail()
    }

    fun borrarCitaPorUsuario(fechacita: String) {
        viewModelScope.launch {
            val token = "Bearer ${sharedPreferencesRepository.getAuthToken()}"
            try {
                val response = apiService.borrarCita(token, _userName.value ?: "", fechacita)
                if (response.isSuccessful) {
                    citasPorUsuario()
                    cantidadCitas()
                    Log.e("CitaBorrada", "Cita borrada con exito")
                } else {
                    Log.e("ErrorBorrar", "Error al borrar la cita")

                }
            } catch (e: Exception) {
                Log.e("Exception", e.message ?: "")
            }
        }

    }

    fun citasPorUsuario() {
        viewModelScope.launch {
            try {
                val response = apiService.obtenerCitasPorUsuario(_userName.value ?: "")
                if (response.isSuccessful) {
                    _citasUsuario.postValue(response.body())
                } else {
                    Log.e(
                        "UserNameViewModel",
                        "Error al obtener citas por usuario: ${response.errorBody()?.string()}"
                    )
                    _citasUsuario.postValue(emptyList())
                }
            } catch (e: Exception) {
                Log.e("UserNameViewModel", "Excepción al obtener citas por usuario: ${e.message}")
                _citasUsuario.postValue(emptyList())
            }
        }

    }

    fun cantidadCitas() {
        viewModelScope.launch {
            try {
                val userNameValue = _userName.value ?: ""
                Log.e("userNameValue", _userName.value.toString())
                val response = apiService.obtenerCantidadCitas(userNameValue)
                if (response.isSuccessful) {
                    val cantidad = response.body()
                    _cantidadCitas.postValue(cantidad)
                    Log.e("Cantidad", cantidad.toString())
                    Log.e("CantidadCitas", _cantidadCitas.value.toString())
                } else {
                    Log.e(
                        "UserNameViewModel",
                        "Error al obtener citas por usuario: ${response.errorBody()?.string()}"
                    )
                    _cantidadCitas.postValue(0)
                }
            } catch (e: Exception) {
                Log.e("UserNameViewModel", "Excepción al obtener citas por usuario: ${e.message}")
                _cantidadCitas.postValue(0)
            }
        }

    }

    fun validateToken() {
        viewModelScope.launch {
            val token = sharedPreferencesRepository.getAuthToken()
            /*if(token == null){
                sharedPreferencesRepository.saveAuthToken("")
            }else {*/
                val response = apiService.validateToken(token.toString())
                if (response.isSuccessful) {
                    Log.d("ValidateToken", "Response successful: ${response.body()?.toString()}")
                    _isTokenValid.postValue(true)
                } else {
                    // En caso de error, intentamos obtener el cuerpo del error
                    val errorBody = response.errorBody()?.string()
                    Log.e("ValidateToken", "Error response: $errorBody")
                    _isTokenValid.postValue(false)
                }
        }
    }
    fun getUserName() {
        viewModelScope.launch {
            val token = sharedPreferencesRepository.getAuthToken()
            if (token != null) {
                try {
                    val response = apiService.getUserName("Bearer $token", token)
                    if (response.isSuccessful) {
                        val responseBodyString = response.body()?.string() ?: "Respuesta vacía"
                        _userName.value = responseBodyString
                        Log.e("USER", _userName.value!!)
                    } else {
                        _userName.value = "Error al obtener el nombre de usuario: ${
                            response.errorBody()?.string()
                        }"
                    }
                } catch (e: Exception) {
                    _userName.value = "Error de conexión: ${e.message}"
                }
            } else {
                _userName.value = "Token no encontrado"
            }
        }
    }

    fun getUserEmail() {
        viewModelScope.launch {
            val token = sharedPreferencesRepository.getAuthToken()
            if (token != null) {
                try {
                    val response = apiService.getUserEmail("Bearer $token", token)
                    if (response.isSuccessful) {
                        val responseBodyString = response.body()?.string() ?: "Respuesta vacía"
                        _userEmail.value = responseBodyString
                        Log.e("USER", _userName.value!!)
                        Log.e("EmailBody",responseBodyString)
                    } else {
                        _userEmail.value = "Error al obtener el email de usuario: ${
                            response.errorBody()?.string()
                        }"
                    }
                } catch (e: Exception) {
                    _userEmail.value = "Error de conexión: ${e.message}"
                }
            } else {
                _userEmail.value = "Token no encontrado"
            }
        }
    }

    fun setUserName(username: String) {
        _userName.postValue(username)
    }


    fun exit() {
        sharedPreferencesRepository.saveAuthToken("")
    }
}