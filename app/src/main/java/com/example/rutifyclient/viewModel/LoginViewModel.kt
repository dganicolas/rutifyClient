package com.example.rutifyclient.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rutifyclient.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.coroutines.launch

class LoginViewModel() : ViewModel() {

    private val _iniciandoSesion = MutableLiveData(true)
    val iniciandoSesion: LiveData<Boolean> = _iniciandoSesion

    private val _textoCorreo = MutableLiveData("")
    val textoCorreo: LiveData<String> = _textoCorreo

    private val _textoContrasena = MutableLiveData("")
    val textoContrasena: LiveData<String> = _textoContrasena

    private val _mostrarVentanaModal = MutableLiveData(false)
    val mostrarVentanaModal: LiveData<Boolean> = _mostrarVentanaModal

    private val _tituloVentanaModal = MutableLiveData<Int>()
    val tituloVentanaModal: LiveData<Int> = _tituloVentanaModal

    private val _mensajeVentanaModal = MutableLiveData<Int>()
    val mensajeVentanaModal: LiveData<Int> = _mensajeVentanaModal

    private val _mostrarVentanaContrasenaPerdida = MutableLiveData(false)
    val mostrarVentanaContrasenaPerdida = _mostrarVentanaContrasenaPerdida

    private val _mostrarContrasena = MutableLiveData(true)
    val mostrarContrasena = _mostrarContrasena

    private val _mensajeToast = MutableLiveData<Int>()
    val mensajeToast: LiveData<Int> = _mensajeToast

    private val _toastMostrado = MutableLiveData<Boolean>()
    val toastMostrado: LiveData<Boolean> = _toastMostrado

    fun cambiarCorreo(mensaje: String) {
        _textoCorreo.value = mensaje
    }

    fun cambiarContrasena(mensaje: String) {
        _textoContrasena.value = mensaje
    }

    fun iniciarSesion(correo: String, contrasena: String, onResultado: (Boolean) -> Unit) {
        _iniciandoSesion.value = false
        if (!comprobarInicioSesion(correo, contrasena)) {
            viewModelScope.launch {
                try {
                    FirebaseAuth.getInstance()
                        .signInWithEmailAndPassword(correo, contrasena)
                        .addOnSuccessListener {
                            mostrarToast(R.string.sesion_iniciada)
                            onResultado(true)

                        }.addOnFailureListener { exception ->
                            onResultado(false)
                            when (exception) {
                                is FirebaseAuthInvalidUserException -> {
                                    mostrarToast(R.string.error_firebase_no_cuenta)
                                }

                                is FirebaseAuthInvalidCredentialsException -> {
                                    mostrarToast(R.string.error_firebase_credenciales_incorrecta)
                                }

                                else -> {
                                    mostrarToast(R.string.error_general)
                                }
                            }
                        }
                } catch (e: Exception) {
                    onResultado(false)
                    mostrarToast(R.string.error_conexion)
                }
            }
        } else {
            _mensajeVentanaModal.value = R.string.datos_no_rellenados
            _tituloVentanaModal.value = R.string.error_inicio_sesion
            _mostrarVentanaModal.value = true
        }
        _iniciandoSesion.value = true
    }

    fun mostrarContrasena(estado: Boolean) {
        _mostrarContrasena.value = estado
    }

    private fun mostrarToast(mensaje: Int) {
        _toastMostrado.value = false
        _mensajeToast.value = mensaje
    }


    fun toastMostrado() {
        _mensajeToast.value = 1
        _toastMostrado.value = true
    }

    fun recuperarContrasena() {
        _mostrarVentanaContrasenaPerdida.value = false
        val correo = _textoCorreo.value ?: ""
        if (!comprobarCorreo(correo)) {
            mostrarToast(R.string.error_envio_correo)
            return
        }
        FirebaseAuth.getInstance().sendPasswordResetEmail(correo)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    mostrarToast(R.string.contrasena_enviada)
                }
            }.addOnFailureListener { exception ->
                mostrarToast(R.string.error_general)
            }
    }


    fun comprobarContrasena(contrasena: String): Boolean {
        return contrasena.isBlank() ||contrasena.length < 6
    }

    fun comprobarCorreo(correo: String): Boolean {
        val regex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
        return regex.matches(correo)
    }

    private fun comprobarInicioSesion(correo: String, pass: String): Boolean {
        return !comprobarCorreo(correo) && comprobarContrasena(pass)
    }

    fun ventanaContrasenaPerdida() {

        _tituloVentanaModal.value = R.string.recuperar_contrasena
        _mensajeVentanaModal.value = R.string.indique_correo
        _mostrarVentanaContrasenaPerdida.value = true
    }

    fun cerrarVentanaModal() {
        _mostrarVentanaModal.value = false
    }
}