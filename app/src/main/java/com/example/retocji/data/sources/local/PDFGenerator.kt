package com.example.retocji.data.sources.local

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph
import javax.inject.Inject

class PDFGenerator @Inject constructor() {
    fun generarPDF(
        context: Context,
        citasSeleccionadas: List<Triple<String, String, String>>
    ): Uri? {
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "citasSeleccionadas.pdf")
            put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
            }
        }

        val resolver = context.contentResolver
        val uri = resolver.insert(MediaStore.Files.getContentUri("external"), contentValues)
        val outputStream = uri?.let { resolver.openOutputStream(it) }

        outputStream?.let {
            PdfWriter(it).use { pdfWriter ->
                PdfDocument(pdfWriter).use { pdfDocument ->
                    Document(pdfDocument).use { document ->
                        document.add(Paragraph("Citas Seleccionadas:"))
                        citasSeleccionadas.forEach { cita ->
                            val citaInfo = "${cita.first} - ${cita.second} - ${cita.third}"
                            document.add(Paragraph(citaInfo))
                        }
                    }
                }
            }
            it.close()
        }
        abrirPDF(context, uri)

        return uri
    }
}

fun abrirPDF(context: Context, uri: Uri?) {
    uri?.let {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(it, "application/pdf")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(Intent.createChooser(intent, "Abrir PDF con:"))
    }
}