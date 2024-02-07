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
import com.example.retocji.domain.models.logIn.UsersDTO
import com.example.retocji.domain.repositories.SharedPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
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

    private val _selectedHour = MutableStateFlow("")
    val selectedHour: StateFlow<String> = _selectedHour.asStateFlow()

    private val _selectedDate= MutableStateFlow("")
    val selectedDate: StateFlow<String> = _selectedDate.asStateFlow()

    fun setSelectedDate(date: String) {
        _selectedDate.value = date
    }
    fun setSelectedHour(hour: String) {
        _selectedHour.value = hour
    }

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
                    Log.e("CitasGestor",citas.toString())
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
    @RequiresApi(Build.VERSION_CODES.O)
    fun crearCita(gestor: String, fecha: String, horaInicio: String) {
        viewModelScope.launch {
            val date = "2024-02-07"
            try {
                val token = sharedPreferencesRepository.getAuthToken()
                var apiUserName: String = ""
                val responseUser = apiService.getUserName("Bearer $token", token ?: "")
                if (responseUser.isSuccessful) {
                    val responseBody = responseUser.body()
                    apiUserName = responseBody?.string() ?: ""
                }
                Log.e("Username", apiUserName)

                // Parsear la fecha y la hora
                val fechaParseada = LocalDate.parse(date)
                val horaParseada = LocalTime.parse(horaInicio)

                // Combinar la fecha y la hora en un objeto LocalDateTime
                val fechaHora = fechaParseada.atTime(horaParseada)

                // Formatear la fecha y la hora al formato deseado
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                val fechaHoraFormateada = fechaHora.format(formatter)

                // Crear la nueva cita con la fecha y hora formateadas
                val nuevaCita = CitasDTO(
                    horaInicio = fechaHoraFormateada,
                    horaFin = fechaHoraFormateada, // Puedes ajustar esto según sea necesario
                    usuario = UsersDTO(name = "Carlos", email = ""),
                    gestor = UsersDTO(name = "Carlos", email = "")
                )

                // Realizar la llamada a la API para crear la cita
                val response = apiService.crearCita("Bearer $token", nuevaCita)
                if (response.isSuccessful) {
                    // Manejar la respuesta si es necesaria
                    Log.d("CitasViewModel", "Cita creada con éxito")
                } else {
                    // Manejar el caso de error
                    Log.e("CitasViewModel", "Error al crear la cita: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                // Manejar las excepciones
                Log.e("CitasViewModel", "Excepción al crear la cita", e)
            }
        }
    }

    private fun calcularHorasDisponibles(citas: List<CitasDTO>): List<String> {

        val horasPredeterminadas = listOf("10:00", "11:00", "12:00", "13:00", "14:00", "16:00", "17:00", "18:00", "19:00", "20:00")


        val horasDisponibles = horasPredeterminadas.toMutableList()


        for (cita in citas) {
            val horaCita = cita.horaInicio.substring(11, 16) // Obtener solo "HH:mm" de "2024-02-09T16:00:00"
            Log.e("Hora Inicio", horaCita)
            if (horasDisponibles.remove(horaCita)) {
                Log.e("Hora Eliminada", horaCita)
            }
        }


        return horasDisponibles
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