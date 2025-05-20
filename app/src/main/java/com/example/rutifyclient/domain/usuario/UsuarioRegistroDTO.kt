package com.example.rutifyclient.domain.usuario

data class UsuarioRegistroDTO(
    var sexo: String,
    var fechaNacimiento: String,
    var nombre: String,
    var correo: String,
    var contrasena:String
)