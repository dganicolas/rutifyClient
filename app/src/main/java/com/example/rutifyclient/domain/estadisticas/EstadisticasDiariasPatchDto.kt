package com.example.rutifyclient.domain.estadisticas

data class EstadisticasDiariasPatchDto(
    val horasActivo: Double? = null,
    val ejerciciosRealizados: Int? = null,
    val kCaloriasQuemadas: Double? = null,
    val pesoCorporal: Double? = null,
)