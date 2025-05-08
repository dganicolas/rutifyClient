package com.example.rutifyclient.interfaces

import androidx.lifecycle.MutableLiveData
import java.time.LocalDate
import java.util.Date

interface IRegistroViewModel {
    val _opcionesSexo: MutableLiveData<List<Int>>
    val _opcionEscogida: MutableLiveData<Int>
    val _textoNombre: MutableLiveData<String>
    val _textoContrasena: MutableLiveData<String>
    val _textoContrasenaConfirmacion: MutableLiveData<String>
    val _mensajeToast: MutableLiveData<Int>
    val _toastMostrado: MutableLiveData<Boolean>
    val _textoCorreo: MutableLiveData<String>
    val _mostrarContrasena: MutableLiveData<Boolean>
    val _checboxTerminos: MutableLiveData<Boolean>
    val _textoFechaNacimiento: MutableLiveData<LocalDate>
    fun registrarUsuario(onResultado: (Boolean) -> Unit)
}