package com.example.retocji.domain.models.email

data class ContactDTO(
    private val to: String,
    private val subject: String,
    private val message: String,
)