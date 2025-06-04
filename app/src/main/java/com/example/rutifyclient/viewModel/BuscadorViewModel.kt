package com.example.rutifyclient.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.rutifyclient.R
import com.example.rutifyclient.apiservice.network.RetrofitClient
import com.example.rutifyclient.domain.comentario.ComentarioDto
import com.example.rutifyclient.domain.usuario.UsuarioBusquedaDto
import kotlinx.coroutines.launch
import java.time.LocalDate

class BuscadorViewModel : ViewModelBase() {

    private val _pestanaActiva = MutableLiveData<Int>(0)
    val pestanaActiva: LiveData<Int> = _pestanaActiva

    private val _listaUsuarios = MutableLiveData<List<UsuarioBusquedaDto>>()
    val listaUsuarios: LiveData<List<UsuarioBusquedaDto>> = _listaUsuarios

    private val _listaComentarios = MutableLiveData<List<ComentarioDto>>()
    val listaComentarios: LiveData<List<ComentarioDto>> = _listaComentarios
    private val _mostrarVentanaEliminarComentario = MutableLiveData(false)
    val mostrarVentanaEliminarComentario: LiveData<Boolean> = _mostrarVentanaEliminarComentario
    private val _textoBusqueda: MutableLiveData<String> =
        MutableLiveData("")
    val textoBusqueda = _textoBusqueda

    private val _comentarioAEliminar =
        MutableLiveData(ComentarioDto("", "", "", "", LocalDate.now(), "", "", ""))

    fun onChangeNombreBusqueda(nuevoTexto: String) {
        _textoBusqueda.value = nuevoTexto
    }

    fun setPestanaActiva(ventana: Int) {
        _pestanaActiva.value = ventana
    }

    fun buscarCuentasPorNombre() {
        _estado.value = false
        viewModelScope.launch {
            try {
                val nombre = _textoBusqueda.value?.trim() ?: ""
                if (nombre.isNotEmpty()) {
                    val response = RetrofitClient.apiUsuarios.buscarUsuariosPorNombre(nombre, 0, 10)
                    if (response.isSuccessful) {
                        _listaUsuarios.value = response.body() ?: emptyList()
                    } else {
                        // Manejo de error de respuesta
                        mostrarToast(R.string.error_busqueda)
                    }
                } else {
                    _listaUsuarios.value = emptyList() // Limpia la lista si no hay búsqueda
                }
            } catch (e: Exception) {
                manejarErrorConexion(e)
                mostrarToast(R.string.error_conexion)
            } finally {
                _estado.value = true
            }
        }
    }

    fun mostrarventanaEliminar() {
        _mostrarVentanaEliminarComentario.value = !_mostrarVentanaEliminarComentario.value!!
    }

    fun guardarComentarioAEliminar(comentario: ComentarioDto) {
        _comentarioAEliminar.value = comentario
    }

    fun buscarComentariosPorAutor() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiComentarios.obtenerComentariosPorNombre(_textoBusqueda.value!!)
                if (response.isSuccessful) {
                    _listaComentarios.value = response.body() ?: emptyList()
                } else {
                    mostrarToast(R.string.error_busqueda)
                }
            } catch (e: Exception) {
                manejarErrorConexion(e)
                mostrarToast(R.string.error_conexion)
            }
        }
    }

    fun borrarComentario() {
        viewModelScope.launch {
            try {
                val response =
                    RetrofitClient.apiComentarios.eliminarComentario(_comentarioAEliminar.value!!._id!!)
                if (response.isSuccessful) {
                    val listaActual = _listaComentarios.value?.toMutableList() ?: mutableListOf()
                    listaActual.removeAll { it._id == _comentarioAEliminar.value!!._id }
                    _listaComentarios.postValue(listaActual)
                    mostrarToast(R.string.comentarioEliminado)
                }
            } catch (e: Exception) {
                manejarErrorConexion(e)
                _sinInternet.value = true
            }
        }
    }
}
