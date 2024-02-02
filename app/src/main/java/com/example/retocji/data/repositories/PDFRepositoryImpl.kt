package com.example.retocji.data.repositories

import android.content.Context
import com.example.retocji.data.datasource.local.PDFGenerator
import javax.inject.Inject

class PDFRepositoryImpl @Inject constructor(private val pdfGenerator: PDFGenerator): PDFRepository {
    override fun generarPDF(context: Context, citasSeleccionadas: List<Triple<String, String, String>>) {
        pdfGenerator.generarPDF(context, citasSeleccionadas)
    }
}
