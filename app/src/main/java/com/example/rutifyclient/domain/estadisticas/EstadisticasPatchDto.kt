package com.example.rutifyclient.domain.estadisticas

data class EstadisticasPatchDto(
    val lvlBrazo: Float? = null,
    val lvlPecho: Float? = null,
    val lvlEspalda: Float? = null,
    val lvlPiernas: Float? = null,
    val ejerciciosRealizados: Int? = null,
    val caloriasQuemadas: Float? = null
)