package com.example.rutifyclient.domain.estadisticas

import java.time.LocalDate

data class EstadisticasDiariasDto(
    val _id: String? = null,
    val idFirebase: String,
    val fecha: LocalDate,
    val horasActivo: Double,
    val pesoCorporal: Double,
    var ejerciciosRealizados: Int,
    var kCaloriasQuemadas: Double
)