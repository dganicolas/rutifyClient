package com.example.rutifyclient.domain.rutinas

data class RutinaBuscadorDto(
    val id: String? = null,
    val nombre: String,
    val imagen: String,
    val descripcion: String,
    val cuantosEjercicios: Int,
    val esPremium: Boolean,
    val equipo:String,
)