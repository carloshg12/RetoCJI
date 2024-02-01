package com.example.retocji.domain.repositories

import java.time.LocalDateTime

data class CitasDTO(
    val horaInicio: String,
    val horaFin: String,
    val usuario: UsersDTO,
    val gestor: UsersDTO,
)
