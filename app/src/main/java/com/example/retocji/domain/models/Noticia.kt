package com.example.retocji.domain.models

data class Noticia(
    val titulo: String,
    val resumen: String,
    val imagenId: Int // Referencia a un drawable
)
