package com.example.rutifyclient.domain

data class Ejercicio(
    val id: String,
    val nombreEjercicio: String,
    val descripcion: String,
    val imagen: String,
    val equipo: String,
    val grupoMuscular: String,
    val caloriasQuemadasPorRepeticion: Double,
    val puntoGanadosPorRepeticion: Double
)