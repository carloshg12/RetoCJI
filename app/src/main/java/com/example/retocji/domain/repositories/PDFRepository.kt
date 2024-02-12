package com.example.retocji.domain.repositories

import android.content.Context

interface PDFRepository {
    fun generarPDF(context: Context, citasSeleccionadas: List<Triple<String, String, String>>)

}
