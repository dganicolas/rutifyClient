package com.example.rutifyclient.domain.voto

data class VotodDto(
    var id: String? = null,
    val idFirebase: String,
    val idRutina: String,
    val nombreRutina: String,
    var puntuacion: Float,
)