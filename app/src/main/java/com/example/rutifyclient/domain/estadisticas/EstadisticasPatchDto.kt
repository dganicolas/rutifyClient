package com.example.rutifyclient.domain.estadisticas

data class EstadisticasPatchDto(
    val lvlBrazo: Double? = null,
    val lvlPecho: Double? = null,
    val lvlEspalda: Double? = null,
    var lvlAbdominal: Double,
    val lvlPiernas: Double? = null,
    val horasActivo: Double? = null,
    val ejerciciosRealizados: Int? = null,
    val kCaloriasQuemadas: Double? = null
)