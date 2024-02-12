package com.example.retocji.ui.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retocji.data.sources.remote.ApiService
import com.example.retocji.domain.models.gestiones.TipoCitaDTO
import com.example.retocji.domain.usescases.GenerarPDFUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GestionesViewModel @Inject constructor(
    private val generarPDFUseCase: GenerarPDFUseCase,
    private val apiService: ApiService
) : ViewModel() {
    init {
        obtenerTipoCitas()
    }

    private val _tipoCitas = MutableLiveData<List<TipoCitaDTO>>()
    val tipoCitas: LiveData<List<TipoCitaDTO>> = _tipoCitas

    fun generarPDF(context: Context, citasSeleccionadas: List<Triple<String, String, String>>) {
        viewModelScope.launch {
            generarPDFUseCase(context, citasSeleccionadas)
        }
    }

    fun obtenerTipoCitas() {
        viewModelScope.launch {
            try {
                val response = apiService.getTipoCitas()
                if (response.isSuccessful) {
                    Log.e("LISTA:", response.body().toString())
                    _tipoCitas.value = response.body() ?: emptyList()
                } else {
                    _tipoCitas.value = emptyList()
                    Log.e(
                        "GestionesViewModel",
                        "Error al obtener tipo de citas: ${response.errorBody()?.string()}"
                    )
                }
            } catch (e: Exception) {
                _tipoCitas.value = emptyList()
                Log.e("GestionesViewModel", "Excepción al obtener tipo de citas", e)
            }
        }
    }
}