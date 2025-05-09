package com.example.rutifyclient.domain

data class EjercicioDto(
    val nombreEjercicio: String,
    val descripcion: String,
    val imagen: String,
    val equipo: String,
    val grupoMuscular: String,
    val caloriasQuemadasPorRepeticion: Double,
    val puntoGanadosPorRepeticion: Double,
    val cantidad: Int
)