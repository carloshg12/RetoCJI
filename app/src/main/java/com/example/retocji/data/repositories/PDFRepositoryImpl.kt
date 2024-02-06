package com.example.retocji.data.repositories

import android.content.Context
import com.example.retocji.data.sources.local.PDFGenerator
import com.example.retocji.domain.repositories.PDFRepository
import javax.inject.Inject

class PDFRepositoryImpl @Inject constructor(private val pdfGenerator: PDFGenerator) :
    PDFRepository {
    override fun generarPDF(
        context: Context,
        citasSeleccionadas: List<Triple<String, String, String>>
    ) {
        pdfGenerator.generarPDF(context, citasSeleccionadas)
    }
}
