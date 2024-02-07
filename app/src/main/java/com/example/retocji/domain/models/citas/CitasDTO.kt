package com.example.retocji.domain.models.citas

import com.example.retocji.domain.models.gestiones.TipoCitaDTO
import com.example.retocji.domain.models.logIn.UsersDTO

data class CitasDTO(
    val horaInicio: String,
    val horaFin: String,
    val usuario: UsersDTO,
    val gestor: UsersDTO,
    val tipoCita: TipoCitaDTO
)
