package com.example.retocji.domain.repositories

import java.time.LocalDateTime

data class Citas(
    val id: Long,
    val fecha: LocalDateTime,
    val horaInicio: LocalDateTime,
    val horaFin: LocalDateTime,
    val usuario: UsersDTO,
    val gestor: UsersDTO,
)
