package com.example.retocji.ui.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retocji.domain.usescases.GenerarPDFUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GestionesViewModel @Inject constructor(
    private val generarPDFUseCase: GenerarPDFUseCase
) : ViewModel() {
    fun generarPDF(context: Context, citasSeleccionadas: List<Triple<String, String, String>>) {
        viewModelScope.launch {
            generarPDFUseCase(context, citasSeleccionadas)
        }
    }
}