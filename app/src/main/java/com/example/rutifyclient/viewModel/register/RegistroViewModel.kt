package com.example.rutifyclient.viewModel.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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

class RegistroViewModel : ViewModelBase(), IRegistroViewModel {

    private val _opcionesSexo = MutableLiveData(listOf(R.string.hombre, R.string.mujer))
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
    val mostrarContrasena = _mostrarContrasena

    private val _checboxTerminos = MutableLiveData(false)
    val checboxTerminos = _checboxTerminos

    private val _textoFechaNacimiento = MutableLiveData<LocalDate>()
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

    fun cambiarFechaNacimiento(fecha: String) {
        val dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val fechaLocalDate = LocalDate.parse(fecha, dateFormat)

        // Asigna el valor a _textoFechaNacimiento
        _textoFechaNacimiento.value = fechaLocalDate
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

    private fun validarUsuario(): Boolean {
        if (_textoNombre.value == null || _textoNombre.value.orEmpty().isBlank()) {
            mostrarToast(R.string.error_nombre_vacio)
            return false
        }
        if (_textoFechaNacimiento.value == null) {
            mostrarToast(R.string.error_fecha_nacimiento_vacia)
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

    private fun sexoEscogido(): String {
        if (_opcionEscogida.value == R.string.hombre) {
            return "H"
        } else {
            return "M"
        }
    }

    override fun registrarUsuario(onResultado: (Boolean) -> Unit) {
        if (validarUsuario()) {
            val sexo = sexoEscogido()
            val usuario = UsuarioRegistroDTO(
                nombre = _textoNombre.value.orEmpty(),
                fechaNacimiento = _textoFechaNacimiento.value!!.toString(),
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
                        mostrarToast(R.string.error_en_respuesta)
                        onResultado(false)
                    }
                } catch (e: Exception) {
                    onResultado(false)
                    mostrarToast(R.string.error_conexion)
                }
            }
        }
    }

}
