package com.example.rutifyclient.domain.usuario

import com.example.rutifyclient.domain.estadisticas.EstadisticasDto
import java.time.LocalDate

data class UsuarioInformacionDto(
    val idFirebase: String,
    val nombre: String,
    val correo: String,
    val sexo: String,
    val esPremium: Boolean,
    val avatarUrl: String,
    var estadisticas: EstadisticasDto,
    val countRutinas: Long,
    val fechaUltimoReto: LocalDate,
    val countComentarios: Long = 0,
    val countVotos: Long = 0
)