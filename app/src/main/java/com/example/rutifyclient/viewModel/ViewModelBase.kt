package com.example.rutifyclient.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rutifyclient.R

open class ViewModelBase(): ViewModel() {
    protected val _mensajeToast = MutableLiveData<Int>()
    val mensajeToast: LiveData<Int> = _mensajeToast

    protected val _toastMostrado = MutableLiveData<Boolean>()
    val toastMostrado: LiveData<Boolean> = _toastMostrado

    protected val _sinInternet = MutableLiveData<Boolean>(false)
    val sinInternet: LiveData<Boolean> = _sinInternet

    protected val _estado = MutableLiveData(true)
    val estado: LiveData<Boolean> = _estado

    fun mostrarToast(mensaje: Int) {
        _toastMostrado.value = false
        _mensajeToast.value = mensaje
    }

    fun toastMostrado() {
        _mensajeToast.value = 1
        _toastMostrado.value = true
    }

    fun reiniciarInternet() {
        _sinInternet.value = false
    }

    fun manejarErrorConexion(e: Exception) {
        e.printStackTrace()
        _sinInternet.value = true
        mostrarToast(R.string.error_conexion)
    }
}