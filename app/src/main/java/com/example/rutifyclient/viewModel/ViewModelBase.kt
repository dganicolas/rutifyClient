package com.example.rutifyclient.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rutifyclient.R
import com.example.rutifyclient.apiservice.network.RetrofitClient
import com.example.rutifyclient.domain.estadisticas.EstadisticasDto
import com.example.rutifyclient.domain.usuario.UsuarioInformacionDto
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import java.time.LocalDate

open class ViewModelBase(): ViewModel() {
    protected val _mensajeToast = MutableLiveData<Int>()
    val mensajeToast: LiveData<Int> = _mensajeToast

    protected val _toastMostrado = MutableLiveData<Boolean>()
    val toastMostrado: LiveData<Boolean> = _toastMostrado

    protected val _sinInternet = MutableLiveData<Boolean>(false)
    val sinInternet: LiveData<Boolean> = _sinInternet

    protected val _usuario = MutableLiveData(
        UsuarioInformacionDto(
            "", "", "", "", false, "", EstadisticasDto("", 0.0, 0.0, 0.0, 0.0, 0.0,0.0, 0.0, 0,0.0), 0, LocalDate.now()
        )
    )
    val usuario: LiveData<UsuarioInformacionDto> = _usuario

    protected val _estado = MutableLiveData(true)
    val estado: LiveData<Boolean> = _estado

    fun mostrarToast(mensaje: Int) {
        _toastMostrado.value = false
        _mensajeToast.value = mensaje
    }

    fun obtenerUsuario() {
        _sinInternet.value = false
        _estado.value = false
        viewModelScope.launch {
            try {
                val response =
                    RetrofitClient.apiUsuarios.obtenerDetalleUsuario(FirebaseAuth.getInstance().currentUser!!.uid)

                if (response.isSuccessful) {
                    _usuario.value = response.body()
                }
            } catch (e: Exception) {
                manejarErrorConexion(e)
                _sinInternet.value=true
                mostrarToast(R.string.error_conexion)
            }finally {
                _estado.value = true
            }
        }
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