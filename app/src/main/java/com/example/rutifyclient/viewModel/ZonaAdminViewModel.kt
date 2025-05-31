package com.example.rutifyclient.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.rutifyclient.R
import com.example.rutifyclient.apiservice.network.RetrofitClient
import com.example.rutifyclient.domain.comentario.ComentarioDto
import kotlinx.coroutines.launch

class ZonaAdminViewModel : ViewModelBase() {
    private val _comentarios = MutableLiveData<List<ComentarioDto>>(emptyList())
    val comentarios: LiveData<List<ComentarioDto>> = _comentarios

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

    fun eliminarComentario(comentario: ComentarioDto) {
        Log.i("prueba",comentario.toString())
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiModeracion.eliminarComentario(comentario._id!!)
                if (response.isSuccessful) {
                    mostrarToast(R.string.comentarioEliminado)
                    val listaActual = comentarios.value?.toMutableList() ?: mutableListOf()
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
                val response = RetrofitClient.apiComunidad.aprobarComentario(comentario)
                if (response.isSuccessful) {
                    mostrarToast(R.string.comentarioAprobado)
                    val listaActual = comentarios.value?.toMutableList() ?: mutableListOf()
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
}
