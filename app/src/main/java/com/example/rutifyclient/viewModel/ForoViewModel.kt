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
import com.google.gson.Gson
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
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ForoViewModel: ViewModelBase() {

    private val _comentarios = MutableLiveData<List<ComentarioDto>>(emptyList())
    val comentarios: LiveData<List<ComentarioDto>> = _comentarios

    private val _mostrarDialogoComentario = MutableLiveData(false)
    val mostrarDialogoComentario: LiveData<Boolean> = _mostrarDialogoComentario

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

    private val _expanded = MutableLiveData<Boolean>(false)
    val expanded: LiveData<Boolean> = _expanded


    private val _estadoAnimoSeleccionado = MutableLiveData<String>("F")
    val estadoAnimoSeleccionado: LiveData<String> = _estadoAnimoSeleccionado

    fun setEstadoAnimo(codigo: String) {
        _estadoAnimoSeleccionado.value = codigo
    }

    fun abrirDialogoComentario() {
        _mostrarDialogoComentario.value = true
        _textoComentario.value = ""
    }

    fun cerrarDialogoComentario() {
        _mostrarDialogoComentario.value = false
    }

    fun setTextoComentario(texto: String) {
        _textoComentario.value = texto
    }

    fun obtenerComentarios() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiComunidad.obtenerComentarios()
                if (response.isSuccessful) {
                    _comentarios.value = response.body() ?: emptyList()
                }
            } catch (e: Exception) {
                manejarErrorConexion(e)
                _sinInternet.value = true
            }
        }
    }

    fun comprimirImagenA100KB(context: Context, uri: Uri): File {
        val inputStream = context.contentResolver.openInputStream(uri) ?: throw Exception("No se pudo abrir imagen")
        var bitmap = BitmapFactory.decodeStream(inputStream)
        inputStream.close()

        var calidad = 100
        var streamLength: Int
        var baos = ByteArrayOutputStream()

        do {
            baos.reset()
            bitmap.compress(Bitmap.CompressFormat.JPEG, calidad, baos)
            val byteArray = baos.toByteArray()
            streamLength = byteArray.size
            calidad -= 5
        } while (streamLength > 100 * 1024 && calidad > 5) // Máximo 100KB y calidad > 5 para no degradar mucho

        val archivoTemporal = File.createTempFile("img_comprimida", ".jpg", context.cacheDir)
        val fos = FileOutputStream(archivoTemporal)
        fos.write(baos.toByteArray())
        fos.flush()
        fos.close()

        return archivoTemporal
    }

    fun crearComentario(context: Context) {
        val usuarioActual = FirebaseAuth.getInstance().currentUser!!

        val nuevoComentario = ComentarioDto(

            idFirebase = usuarioActual.uid,
            nombreUsuario = usuario.value!!.nombre,
            avatarUrl = usuario.value!!.avatarUrl,
            fechaPublicacion = LocalDate.now(),
            estadoAnimo = _estadoAnimoSeleccionado.value!!,
            texto = _textoComentario.value ?: "",
            idComentarioPadre = null
        )
        viewModelScope.launch {
            viewModelScope.launch {
                try {
                    val gson = GsonBuilder()
                        .registerTypeAdapter(LocalDate::class.java, JsonSerializer<LocalDate> { src, _, _ ->
                            JsonPrimitive(src.toString()) // convierte LocalDate a "yyyy-MM-dd"
                        })
                        .create()
                    val jsonComentario = gson.toJson(nuevoComentario)
                    val comentarioRequestBody = jsonComentario.toRequestBody("application/json; charset=utf-8".toMediaType())
                    // Preparar MultipartBody.Part para la imagen si existe
                    val imagenPart: MultipartBody.Part? = _imagenUri.value?.let { uri ->
                        val archivoComprimido = comprimirImagenA100KB(context, uri)
                        val requestFile = archivoComprimido.asRequestBody("image/jpeg".toMediaType())
                        MultipartBody.Part.createFormData("imagen", archivoComprimido.name, requestFile)
                    }

                    val response = RetrofitClient.apiComunidad.crearComentario(comentarioRequestBody, imagenPart)

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

    fun setExpanded(estado: Boolean) {
        _expanded.value = estado
    }
}
