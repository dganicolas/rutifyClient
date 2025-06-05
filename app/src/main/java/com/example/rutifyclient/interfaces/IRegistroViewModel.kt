package com.example.rutifyclient.interfaces

import android.app.Activity

interface IRegistroViewModel {
    fun registrarUsuario(onResultado: (Boolean) -> Unit)
    fun validarUsuario(): Boolean
    fun mostrarPaginaTerminos(context: Activity)
}