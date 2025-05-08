package com.example.rutifyclient.domain

data class RutinaDTO(
    val nombre: String,
    val descripcion: String,
    val creadorId: String,
    val ejercicios: List<Ejercicio>,
    val equipo:String = "no especificado",
    val esPremium: Boolean
)