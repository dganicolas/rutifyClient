package com.example.rutifyclient.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.rutifyclient.R
import com.example.rutifyclient.apiservice.network.RetrofitClient
import com.example.rutifyclient.domain.comentario.ComentarioDto
import com.example.rutifyclient.domain.usuario.UsuarioBusquedaDto
import kotlinx.coroutines.launch

class ZonaAdminViewModel : ViewModelBase() {

    private val _comentarios = MutableLiveData<List<ComentarioDto>>(emptyList())
    val comentarios: LiveData<List<ComentarioDto>> = _comentarios

    private val _usuarios = MutableLiveData<List<UsuarioBusquedaDto>>(emptyList())
    val usuarios: LiveData<List<UsuarioBusquedaDto>> = _usuarios

    fun cargarComentariosNoAprobados() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiModeracion.cargarComentariosNoVerificados()
                if (response.isSuccessful) {
                    _comentarios.value = response.body()
                } else {
                    mostrarToastApi(response.errorBody()?.string() ?: "Error desconocido")
                }
            } catch (e: Exception) {
                mostrarToastApi(e.message!!)
                e.printStackTrace()
            }
        }
    }

    fun cargarUsuariosConReportes() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiModeracion.obtenerUsuariosReportados()
                if (response.isSuccessful) {
                    _usuarios.value = response.body()
                } else {
                    mostrarToastApi(response.errorBody()?.string() ?: "Error desconocido")
                }
            } catch (e: Exception) {
                mostrarToastApi(e.message!!)
                e.printStackTrace()
            }
        }
    }

    fun eliminarComentario(comentario: ComentarioDto) {
        Log.i("prueba",comentario.toString())
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiModeracion.eliminarComentario(comentario._id!!)
                if (response.isSuccessful) {
                    mostrarToast(R.string.comentarioEliminado)
                    val listaActual = _comentarios.value?.toMutableList() ?: mutableListOf()
                    listaActual.removeAll { it._id == comentario._id }
                    _comentarios.postValue(listaActual)
                } else {
                    mostrarToastApi(response.errorBody()?.string() ?: "Error desconocido")
                }
            } catch (e: Exception) {
                mostrarToastApi(e.message!!)
                e.printStackTrace()
            }
        }
    }

    fun aprobarComentario(comentario: ComentarioDto) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiComentarios.aprobarComentario(comentario)
                if (response.isSuccessful) {
                    mostrarToast(R.string.comentarioAprobado)
                    val listaActual = _comentarios.value?.toMutableList() ?: mutableListOf()
                    listaActual.removeAll { it._id == comentario._id }
                    _comentarios.postValue(listaActual)
                } else {
                    mostrarToastApi(response.errorBody()?.string() ?: "Error desconocido")
                }
            } catch (e: Exception) {
                mostrarToastApi(e.message!!)
                e.printStackTrace()
            }
        }
    }

    fun eliminarUsuario(usuario: UsuarioBusquedaDto) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiModeracion.obtenerUsuariosReportados(usuario.idFirebase)
                if (response.isSuccessful) {
                    mostrarToast(R.string.comentarioAprobado)
                    val listaActual = _usuarios.value?.toMutableList() ?: mutableListOf()
                    listaActual.removeAll { it.idFirebase == usuario.idFirebase }
                    _usuarios.postValue(listaActual)
                } else {
                    mostrarToastApi(response.errorBody()?.string() ?: "Error desconocido")
                }
            } catch (e: Exception) {
                mostrarToastApi(e.message!!)
                e.printStackTrace()
            }
        }
    }
}
