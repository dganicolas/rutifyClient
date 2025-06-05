package com.example.rutifyclient.viewModel.register

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.rutifyclient.R
import com.example.rutifyclient.apiservice.network.RetrofitClient
import com.example.rutifyclient.domain.usuario.UsuarioRegistroDTO
import com.example.rutifyclient.interfaces.IRegistroViewModel
import com.example.rutifyclient.viewModel.ViewModelBase
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class RegistroViewModel : ViewModelBase(), IRegistroViewModel {

    private val _opcionesSexo = MutableLiveData(listOf(R.string.hombre, R.string.mujer, R.string.otroSexo))
    val opcionesSexo = _opcionesSexo

    private val _opcionEscogida = MutableLiveData<Int>()
    val opcionEscogida = _opcionEscogida

    private val _textoNombre = MutableLiveData("")
    val textoNombre: LiveData<String> = _textoNombre

    private val _textoContrasena = MutableLiveData("")
    val contrasena: LiveData<String> = _textoContrasena

    private val _textoContrasenaConfirmacion = MutableLiveData("")
    val contrasenaConfirmacion: LiveData<String> = _textoContrasenaConfirmacion

    private val _textoCorreo = MutableLiveData("")
    val correo = _textoCorreo

    private val _mostrarContrasena = MutableLiveData(true)
    val mostrarContrasena: LiveData<Boolean> = _mostrarContrasena

    private val _mostrarContrasenaConfirmacion = MutableLiveData(true)
    val mostrarContrasenaConfirmacion: LiveData<Boolean> = _mostrarContrasenaConfirmacion

    private val _checboxTerminos = MutableLiveData(false)
    val checboxTerminos: LiveData<Boolean> = _checboxTerminos

    private val _FechaNacimiento = MutableLiveData<LocalDate?>()
    private val _textoFechaNacimiento = MutableLiveData<String>()
    val textoFechaNacimiento: LiveData<String> = _textoFechaNacimiento.map { date ->
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        date.format(formatter)
    }

    fun cambiarCorreo(mensaje: String) {
        _textoCorreo.value = mensaje
    }

    fun cambiarContrasena(mensaje: String) {
        _textoContrasena.value = mensaje
    }

    fun esFechaValida(fecha: String): Boolean {
        val regex = "^([0-2][0-9]|(3)[0-1])/(0[1-9]|1[0-2])/(\\d{4})$".toRegex()
        return regex.matches(fecha)
    }

    fun cambiarFechaNacimiento(fechaEntrante: String) {
        val fecha = fechaEntrante.replace("/","")
        _textoFechaNacimiento.value = fecha
        val dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        try {
            if (fecha.length == 8) {
                _FechaNacimiento.value = LocalDate.parse(
                    "${fecha.substring(0, 2)}/${
                        fecha.substring(
                            2,
                            4
                        )
                    }/${fecha.substring(4)}", dateFormat
                )
            }else{
                throw DateTimeParseException("Fecha incompleta", fecha, 0)
            }
            } catch (e: DateTimeParseException) {
            _FechaNacimiento.value = null
            Log.w("FechaNacimiento", "Fecha inválida: $fecha")
        }
    }

    fun cambiarNombre(nombre: String) {
        _textoNombre.value = nombre
    }

    fun mostrarContrasena(estado: Boolean) {
        _mostrarContrasena.value = estado
    }


    fun cambiarSexo(sexo: Int) {
        if (sexo == R.string.hombre) {
            _opcionEscogida.value = R.string.hombre
        } else {
            _opcionEscogida.value = R.string.mujer
        }
    }

    fun cambiarContrasenaConfirmacion(contrasena: String) {
        _textoContrasenaConfirmacion.value = contrasena
    }

    fun cambiarTerminos(it: Boolean) {
        _checboxTerminos.value = it
    }

    private fun comprobarCorreo(correo: String): Boolean {
        val regex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
        return regex.matches(correo)
    }

    override fun validarUsuario(): Boolean {
        if (_textoNombre.value == null || _textoNombre.value.orEmpty().isBlank()) {
            mostrarToast(R.string.error_nombre_vacio)
            return false
        }

        if (_FechaNacimiento.value == null) {
            mostrarToast(R.string.error_fecha_nacimiento_vacia)
            return false
        }

        if(_FechaNacimiento.value!!.isAfter(LocalDate.now().minusYears(16))) {
            mostrarToast(R.string.error_fecha_futura)
            return false
        }


        if (_opcionEscogida.value == null) {
            mostrarToast(R.string.error_sexo_no_seleccionado)
            return false
        }

        if (!comprobarCorreo(_textoCorreo.value ?: "")) {
            mostrarToast(R.string.error_correo_invalido)
            return false
        }
        if (_textoContrasena.value.orEmpty()
                .isBlank() || _textoContrasena.value.orEmpty().length < 6
        ) {
            mostrarToast(R.string.error_contrasena_invalida)
            return false
        }
        if (_textoContrasena.value != _textoContrasenaConfirmacion.value) {
            mostrarToast(R.string.error_contrasenas_diferentes)
            return false
        }
        if (_checboxTerminos.value == false) {
            mostrarToast(R.string.error_terminos_no_aceptados)
            return false
        }
        return true
    }

    override fun mostrarPaginaTerminos(context: Activity) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(RetrofitClient.BASE_URL + "terminos-y-condiciones.html")
        context.startActivity(intent)
    }

    private fun sexoEscogido(): String {
        return if(_opcionEscogida.value == R.string.otroSexo){
             "O"
        }else if (_opcionEscogida.value == R.string.hombre) {
             "H"
        } else {
             "M"
        }
    }

    override fun registrarUsuario(onResultado: (Boolean) -> Unit) {
        _estado.value = false
        if (validarUsuario()) {
            val sexo = sexoEscogido()
            val usuario = UsuarioRegistroDTO(
                nombre = _textoNombre.value.orEmpty(),
                fechaNacimiento = _FechaNacimiento.value!!.toString(),
                sexo = sexo,
                correo = _textoCorreo.value.orEmpty(),
                contrasena = _textoContrasena.value.orEmpty()
            )
            viewModelScope.launch {
                try {
                    val response = RetrofitClient.apiUsuarios.registrarUsuario(usuario)
                    if (response.isSuccessful) {
                        val usuarioRegistrado = response.body()
                        if (usuarioRegistrado != null) {
                            mostrarToast(R.string.registro_exitoso)
                            onResultado(true)
                        }
                    } else {
                        mostrarToastApi(response.errorBody()?.string() ?:"")
                        onResultado(false)
                    }
                    _estado.value = true
                } catch (e: Exception) {
                    manejarErrorConexion(e)
                    _sinInternet.value = true
                    onResultado(false)
                    mostrarToast(R.string.error_conexion)
                }finally {
                    _estado.value = true
                }
            }
        }else{
            _estado.value = true
        }
    }

    fun mostrarContrasenaConfirmacion(mostrarContrasenaConfirmacion: Boolean) {
        _mostrarContrasenaConfirmacion.value = mostrarContrasenaConfirmacion
    }

}
