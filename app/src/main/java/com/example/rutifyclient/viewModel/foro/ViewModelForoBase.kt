package com.example.rutifyclient.viewModel.foro

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.rutifyclient.R
import com.example.rutifyclient.apiservice.network.RetrofitClient
import com.example.rutifyclient.domain.comentario.ComentarioDto
import com.example.rutifyclient.viewModel.ViewModelBase
import java.time.LocalDate

open class ViewModelForoBase:ViewModelBase() {
    protected val _comentarios = MutableLiveData<List<ComentarioDto>>(emptyList())
    val comentarios: LiveData<List<ComentarioDto>> = _comentarios

    protected val _textoComentario = MutableLiveData<String>("")
    val textoComentario: LiveData<String> = _textoComentario

    protected val _comentarioAEliminar =
        MutableLiveData(ComentarioDto("", "", "", "", LocalDate.now(), "", "", ""))

    protected val _mostrarVentanaEliminarComentario = MutableLiveData(false)
    val mostrarVentanaEliminarComentario: LiveData<Boolean> = _mostrarVentanaEliminarComentario

    fun setTextoComentario(texto: String) {
        _textoComentario.value = texto
    }

    fun mostrarventanaEliminar() {
        _mostrarVentanaEliminarComentario.value = !_mostrarVentanaEliminarComentario.value!!
    }

    fun guardarComentarioAEliminar(comentario: ComentarioDto) {
        _comentarioAEliminar.value = comentario
    }

    suspend fun comentarioAEliminar() {
        try {
            val response =
                RetrofitClient.apiComunidad.eliminarComentario(_comentarioAEliminar.value!!)
            if (response.isSuccessful) {
                val listaActual = comentarios.value?.toMutableList() ?: mutableListOf()
                listaActual.removeAll { it._id == _comentarioAEliminar.value!!._id }
                _comentarios.postValue(listaActual)
                mostrarToast(R.string.comentarioEliminado)
            }
        } catch (e: Exception) {
            manejarErrorConexion(e)
            _sinInternet.value = true
        }
    }
}