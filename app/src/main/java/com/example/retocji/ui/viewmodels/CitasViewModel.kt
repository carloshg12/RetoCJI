package com.example.retocji.ui.viewmodels

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retocji.data.sources.remote.ApiService
import com.example.retocji.domain.models.citas.CitasDTO
import com.example.retocji.ui.screens.logIn.SharedPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CitasViewModel @Inject constructor(
    private val sharedPreferencesRepository: SharedPreferencesRepository,
    private val apiService: ApiService
) : ViewModel() {

    private val _gestoresLiveData = MutableLiveData<List<String>>()
    val gestoresLiveData: LiveData<List<String>> = _gestoresLiveData

    private val _citasPorGestorYDia = MutableLiveData<List<CitasDTO>>()
    val citasPorGestorYDia: LiveData<List<CitasDTO>> = _citasPorGestorYDia
    private val _horasDisponibles = MutableStateFlow<List<String>>(emptyList())
    val horasDisponibles: StateFlow<List<String>> = _horasDisponibles.asStateFlow()

    private val _nombresTipoCitas = MutableStateFlow<List<String>>(emptyList())
    val nombresTipoCitas: StateFlow<List<String>> = _nombresTipoCitas.asStateFlow()
    private val _asesorDeseado = MutableStateFlow("")
    val asesorDeseado = _asesorDeseado.asStateFlow()

    @RequiresApi(Build.VERSION_CODES.O)
    fun actualizarHorasDisponibles(name: String, dia: String) {
        // Aquí deberías implementar la lógica para calcular las horas disponibles
        // basándote en el asesor seleccionado y la fecha seleccionada. Por ejemplo:
        val asesor = _asesorDeseado.value
        val fecha = _fechaSeleccionada.value.toString() // Asegúrate de formatearlo como necesario

        viewModelScope.launch {
            try {
                val response = apiService.getCitasPorGestorYDia(asesor, fecha)
                if (response.isSuccessful) {
                    val citas = response.body() ?: emptyList()
                    val nuevasHorasDisponibles = calcularHorasDisponibles(citas)
                    _horasDisponibles.value = nuevasHorasDisponibles
                } else {
                    Log.e("CitasViewModel", "Error al obtener citas por gestor y día: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("CitasViewModel", "Excepción al obtener citas por gestor y día", e)
            }
        }
    }
    private fun calcularHorasDisponibles(citas: List<CitasDTO>): List<String> {

        // Implementa la lógica para calcular las horas disponibles aquí.
        // Esto es solo un ejemplo y necesitarás adaptarlo a tus necesidades específicas.
        return listOf("08:00", "09:00" /*, más horas disponibles */)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private val _fechaSeleccionada = MutableStateFlow(LocalDate.now())

    @RequiresApi(Build.VERSION_CODES.O)
    val fechaSeleccionada: StateFlow<LocalDate> = _fechaSeleccionada.asStateFlow()

    @RequiresApi(Build.VERSION_CODES.O)
    fun setFechaSeleccionada(fecha: LocalDate) {
        _fechaSeleccionada.value = fecha
        actualizarHorasDisponibles(asesorDeseado.value, fecha.toString())

    }

    fun setAsesorDeseado(asesor: String) {
        _asesorDeseado.value = asesor
        obtenerTipoCitas()

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
                    Log.e(
                        "CitasViewModel",
                        "Error al obtener gestores: ${response.errorBody()?.string()}"
                    )
                    _gestoresLiveData.postValue(emptyList())
                }
            } catch (e: Exception) {
                Log.e("CitasViewModel", "Excepción al obtener gestores: ${e.message}")
                _gestoresLiveData.postValue(emptyList())
            }
        }
    }

    fun obtenerTipoCitas() {
        viewModelScope.launch {
            try {
                val response = apiService.getTipoCitas()
                if (response.isSuccessful) {
                    val nombres = response.body()?.map { it.nombre } ?: emptyList()
                    _nombresTipoCitas.value = nombres
                } else {
                    _nombresTipoCitas.value = emptyList()
                    Log.e(
                        "CitasViewModel",
                        "Error al obtener tipo de citas: ${response.errorBody()?.string()}"
                    )
                }
            } catch (e: Exception) {
                _nombresTipoCitas.value = emptyList()
                Log.e("CitasViewModel", "Excepción al obtener tipo de citas", e)
            }
        }
    }


    fun obtenerCitasPorGestorYDia(name: String, dia: String) {
        viewModelScope.launch {
            try {
                val response = apiService.getCitasPorGestorYDia(name, dia)
                if (response.isSuccessful) {
                    _citasPorGestorYDia.postValue(response.body() ?: emptyList())
                } else {
                    _citasPorGestorYDia.postValue(emptyList())
                    Log.e(
                        "CitasViewModel",
                        "Error al obtener citas por gestor y día: ${response.errorBody()?.string()}"
                    )
                }
            } catch (e: Exception) {
                _citasPorGestorYDia.postValue(emptyList())
                Log.e("CitasViewModel", "Excepción al obtener citas por gestor y día", e)
            }
        }
    }


}