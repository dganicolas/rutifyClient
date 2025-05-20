package com.example.rutifyclient.domain.usuario

data class ActualizarUsuarioDTO(
    val correo: String,
    val nombre: String?,
    val sexo: String?,
    val edad: Int?,
    val perfilPublico: Boolean?,
    val avatar: String?
)