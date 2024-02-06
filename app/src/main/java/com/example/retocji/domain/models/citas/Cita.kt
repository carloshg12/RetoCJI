package com.example.retocji.domain.models.citas

data class Cita(
    val id: Int,
    val dia: String?,
    val inicio: String?,
    val fin: String?,
    var disponible: Boolean?,
    val asesor: String?,
    val gestion: String?
)
