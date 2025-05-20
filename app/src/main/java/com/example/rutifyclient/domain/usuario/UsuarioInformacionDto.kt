package com.example.rutifyclient.domain.usuario

import com.example.rutifyclient.domain.estadisticas.EstadisticasDto

data class UsuarioInformacionDto(
    val idFirebase: String,
    val nombre: String,
    val correo: String,
    val sexo: String,
    val esPremium: Boolean,
    val avatarUrl: String,
    val estadisticas: EstadisticasDto,
    val countRutinas: Long
)