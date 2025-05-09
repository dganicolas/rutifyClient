package com.example.rutifyclient.domain

import java.util.Dictionary

data class RutinaDTO(
    val nombre: String,
    val imagen:String,
    val descripcion: String,
    val creadorId: String,
    val ejercicios: Map<String,Int>,
    val equipo:String = "no especificado",
    val esPremium: Boolean
)