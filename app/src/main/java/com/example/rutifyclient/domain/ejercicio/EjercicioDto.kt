package com.example.rutifyclient.domain.ejercicio

data class EjercicioDto(
    val id:String,
    val nombreEjercicio: String,
    val descripcion: String,
    val imagen: String,
    val equipo: String,
    val grupoMuscular: String,
    val caloriasQuemadasPorRepeticion: Double,
    val puntoGanadosPorRepeticion: Double,
    var cantidad: Int
)