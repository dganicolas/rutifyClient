package com.example.rutifyclient.domain.estadisticas

data class EstadisticasDto(
    var idFirebase: String,
    var lvlBrazo: Double,
    var lvlPecho: Double,
    var lvlEspalda: Double,
    var pesoCorporal: Double,
    var lvlAbdominal: Double,
    var lvlPiernas: Double,
    var horasActivo: Double,
    var ejerciciosRealizados: Int,
    var kCaloriasQuemadas: Double
)