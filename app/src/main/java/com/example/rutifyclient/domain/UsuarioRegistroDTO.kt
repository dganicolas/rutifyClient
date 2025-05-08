package com.example.rutifyclient.domain

import java.time.LocalDate
import java.util.Date

data class UsuarioRegistroDTO(
    var sexo: String,
    var fechaNacimiento: String,
    var nombre: String,
    var correo: String,
    var contrasena:String
)