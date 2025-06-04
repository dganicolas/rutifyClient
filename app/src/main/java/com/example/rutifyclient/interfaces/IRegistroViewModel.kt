package com.example.rutifyclient.interfaces

interface IRegistroViewModel {
    fun registrarUsuario(onResultado: (Boolean) -> Unit)
    fun validarUsuario(): Boolean
}