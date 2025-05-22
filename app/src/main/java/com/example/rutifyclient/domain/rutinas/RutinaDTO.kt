package com.example.rutifyclient.domain.rutinas

import com.example.rutifyclient.domain.ejercicio.EjercicioDto

data class RutinaDTO(
    val id: String? = null,
    val nombre: String,
    val imagen:String,
    val descripcion: String,
    val creadorId: String,
    val ejercicios: List<EjercicioDto>,
    val equipo:String = "no especificado",
    val votos:Float,
    val totalVotos: Int,
    val esPremium: Boolean
)