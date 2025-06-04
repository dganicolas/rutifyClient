package com.example.rutifyclient.domain.usuario

data class ActualizarUsuarioDTO(
    val correo: String,
    val nombre: String? = null,
    val sexo: String? = null,
    val edad: Int? = null,
    val perfilPublico: Boolean? = null,
    val avatar: String? = null
)