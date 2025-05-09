package com.example.rutifyclient.domain

import java.util.Dictionary

data class RutinaDTO(
    val id: String? = null,
    val nombre: String,
    val imagen:String,
    val descripcion: String,
    val creadorId: String,
    val ejercicios: List<EjercicioDto>,
    val equipo:String = "no especificado",
    val esPremium: Boolean
)