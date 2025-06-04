package com.example.rutifyclient.viewModel.foro.comentarios

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rutifyclient.R
import com.example.rutifyclient.apiservice.network.RetrofitClient
import com.example.rutifyclient.domain.comentario.ComentarioDto
import com.example.rutifyclient.viewModel.foro.ViewModelForoBase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import java.time.LocalDate

class DetallesComentariosViewModel : ViewModelForoBase() {

    private val _idComentarioPadre = MutableLiveData<String>("")

    private val _comentarioPadre = MutableLiveData<ComentarioDto>(
        ComentarioDto(
            "", "", "", "",
            LocalDate.now(), "", "", ""
        )
    )
    val comentarioPadre: LiveData<ComentarioDto> = _comentarioPadre

    fun obtenerComentarioPadre(idComentario: String) {
        _idComentarioPadre.value = idComentario
    }

    private val _mostrarVentanaReportarUsuario = MutableLiveData(false)
    val mostrarVentanaReportarUsuario: LiveData<Boolean> = _mostrarVentanaReportarUsuario

    fun mostrarVentanaReportarUsuario() {
        _mostrarVentanaReportarUsuario.value = !_mostrarVentanaReportarUsuario.value!!
    }

    fun obtenerComentarios() {
        viewModelScope.launch {
            try {
                val response =
                    RetrofitClient.apiComentarios.obtenerRespuestas(_idComentarioPadre.value!!)
                if (response.isSuccessful) {
                    val todosLosComentarios = response.body() ?: emptyList()

                    if (todosLosComentarios.isNotEmpty()) {
                        _comentarioPadre.value = todosLosComentarios[0]
                        _comentarios.value = todosLosComentarios.drop(1)
                    } else {
                        _comentarios.value = emptyList()
                    }
                }
            } catch (e: Exception) {
                manejarErrorConexion(e)
                _sinInternet.value = true
            }
        }
    }

    fun crearComentario() {
        val usuarioActual = FirebaseAuth.getInstance().currentUser!!

        val nuevoComentario = ComentarioDto(

            idFirebase = usuarioActual.uid,
            nombreUsuario = usuario.value!!.nombre,
            avatarUrl = usuario.value!!.avatarUrl,
            fechaPublicacion = LocalDate.now(),
            estadoAnimo = "",
            texto = _textoComentario.value ?: "",
            idComentarioPadre = _idComentarioPadre.value!!
        )
        _textoComentario.value = " "
        viewModelScope.launch {
            viewModelScope.launch {
                try {
                    val response =
                        RetrofitClient.apiComentarios.responderComentario(nuevoComentario)

                    if (response.isSuccessful) {
                        obtenerComentarios()
                    } else {
                        // Manejo de error, puede leer response.errorBody()
                    }
                } catch (e: Exception) {
                    manejarErrorConexion(e)
                    _sinInternet.value = true
                }
            }
        }
    }

    fun borrarComentario(onResultado: (Boolean) -> Unit) {
        viewModelScope.launch {
            comentarioAEliminar()
            onResultado(_comentarioAEliminar.value!!._id == _comentarioPadre.value!!._id)
        }
    }

    fun reportarusuario() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiReportes.reportarUsuario(_comentarioPadre.value!!.idFirebase)
                when {
                    response.isSuccessful -> {
                        if (response.code() == 201) {
                            mostrarToast(R.string.usuarioReportado)
                        }
                    }
                    response.code() == 409 -> {
                        mostrarToast(R.string.usuarioYaReportado)
                    }
                    response.code() == 404 -> {
                        mostrarToast(R.string.usuarioreportadorNoExiste)
                    }
                    else -> {
                        mostrarToast(R.string.errorReportando)
                    }
                }
            } catch (e: Exception) {
                mostrarToastApi(e.toString())
            }
        }
    }


}
