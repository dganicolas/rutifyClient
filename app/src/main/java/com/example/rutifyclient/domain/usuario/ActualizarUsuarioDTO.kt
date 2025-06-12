package com.example.rutifyclient.domain.usuario

import java.time.LocalDate

data class ActualizarUsuarioDTO(
    val correo: String,
    val nombre: String? = null,
    val sexo: String? = null,
    val fechaNacimiento: LocalDate? = null,
    val perfilPublico: Boolean? = null,
    val avatar: String? = null
)