package com.example.rutifyclient.domain

data class ComentarioDto(
    val idFirebase: String,
    val nombreUsuario: String,
    val avatarUrl: String, // URL o recurso local
    val fechaPublicacion: String, // o Date si lo vas a formatear
    val estadoAnimo: String, // ejemplo: "Motivado", "Cansado", etc.
    val texto: String
)