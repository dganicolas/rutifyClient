package com.example.rutifyclient.domain.estadisticas

data class EstadisticasDto(
    var idFirebase: String,
    var lvlBrazo: Double,
    var lvlPecho: Double,
    var lvlEspalda: Double,
    var lvlAbdominal: Double,
    var lvlPiernas: Double,
    var ejerciciosRealizados: Int,
    var kcaloriasQuemadas: Double
)