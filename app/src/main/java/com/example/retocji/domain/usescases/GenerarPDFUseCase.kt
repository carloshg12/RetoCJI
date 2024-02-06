package com.example.retocji.domain.usescases

import android.content.Context
import com.example.retocji.domain.repositories.PDFRepository
import javax.inject.Inject

class GenerarPDFUseCase @Inject constructor(private val pdfRepository: PDFRepository) {
    operator fun invoke(
        context: Context,
        citasSeleccionadas: List<Triple<String, String, String>>
    ) =
        pdfRepository.generarPDF(context, citasSeleccionadas)
}
