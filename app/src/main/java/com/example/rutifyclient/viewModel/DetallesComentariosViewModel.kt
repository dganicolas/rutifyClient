package com.example.rutifyclient.viewModel

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.rutifyclient.apiservice.network.RetrofitClient
import com.example.rutifyclient.domain.comentario.ComentarioDto
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.GsonBuilder
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializer
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDate

class DetallesComentariosViewModel : ViewModelBase() {

    private val _idComentarioPadre = MutableLiveData<String>("")

    private val _comentarios = MutableLiveData<List<ComentarioDto>>(emptyList())
    val comentarios: LiveData<List<ComentarioDto>> = _comentarios

    private val _comentarioPadre = MutableLiveData<ComentarioDto>(
        ComentarioDto(
            "", "", "", "",
            LocalDate.now(), "", "", ""
        )
    )
    val comentarioPadre: LiveData<ComentarioDto> = _comentarioPadre

    private val _mostrarDialogoCrearVentanaComentario = MutableLiveData(false)
    val mostrarDialogoComentario: LiveData<Boolean> = _mostrarDialogoCrearVentanaComentario

    private val _textoComentario = MutableLiveData("")
    val textoComentario: LiveData<String> = _textoComentario

    private val _imagenUri = MutableLiveData<Uri?>()
    val imagenUri: LiveData<Uri?> = _imagenUri

    private val _cameraUri = MutableLiveData<Uri?>()
    val cameraUri: LiveData<Uri?> = _cameraUri


    fun setImagenUri(uri: Uri) {
        _imagenUri.value = uri
    }

    fun crearUriParaFoto(context: Context) {
        val activity = context as Activity
        val file = File.createTempFile("foto_temp_", ".jpg", activity.cacheDir).apply {
            createNewFile()
            deleteOnExit()
        }
        val uri = FileProvider.getUriForFile(activity, "${activity.packageName}.fileprovider", file)
        _cameraUri.value = uri
    }

    fun abrirDialogoComentario() {
        _mostrarDialogoCrearVentanaComentario.value = true
        _textoComentario.value = ""
    }

    fun obtenerComentarioPadre(idComentario: String) {
        _idComentarioPadre.value = idComentario
    }

    fun cerrarDialogoComentario() {
        _mostrarDialogoCrearVentanaComentario.value = false
    }

    fun setTextoComentario(texto: String) {
        _textoComentario.value = texto
    }

    fun obtenerComentarios() {
        viewModelScope.launch {
            try {
                val response =
                    RetrofitClient.apiComunidad.obtenerRespuestas(_idComentarioPadre.value!!)
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
                        RetrofitClient.apiComunidad.responderComentario(nuevoComentario)

                    if (response.isSuccessful) {
                        cerrarDialogoComentario()
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
}
