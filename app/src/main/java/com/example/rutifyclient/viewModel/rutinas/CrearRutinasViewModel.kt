package com.example.rutifyclient.viewModel.rutinas

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rutifyclient.R
import com.example.rutifyclient.apiservice.network.RetrofitClient
import com.example.rutifyclient.domain.ejercicio.EjercicioDto
import com.example.rutifyclient.domain.rutinas.RutinaDTO
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class CrearRutinasViewModel : ViewModel() {

    private val _calorias = MutableLiveData(0.0)
    val calorias: LiveData<Double> = _calorias

    private val _puntosGanados = MutableLiveData(0)
    val puntosGanados: LiveData<Int> = _puntosGanados

    private val _mostrarVentanaAnadirEjercicio = MutableLiveData(false)
    val mostrarVentanaAnadirEjercicio: LiveData<Boolean> = _mostrarVentanaAnadirEjercicio

    private val _mostrarVentanacambiarIcono = MutableLiveData(false)
    val mostrarVentanacambiarIcono: LiveData<Boolean> = _mostrarVentanacambiarIcono

    private val _mostrarVentanaEliminarEjercicio = MutableLiveData(false)
    val mostrarVentanaEliminarEjercicio: LiveData<Boolean> = _mostrarVentanaEliminarEjercicio

    private val _ejerciciosAgrupados = MutableLiveData<Map<String, List<EjercicioDto>>>()
    val ejerciciosAgrupados: LiveData<Map<String, List<EjercicioDto>>> = _ejerciciosAgrupados

    private val _mostrarVentanaDetallesEjercicio = MutableLiveData(false)
    val mostrarVentanaDetallesEjercicio: LiveData<Boolean> = _mostrarVentanaDetallesEjercicio

    private val _ejercicioSeleccionado =
        MutableLiveData(EjercicioDto("", "", "", "", "", "", 0.0, 0.0, 0))
    val ejercicioSeleccionado: LiveData<EjercicioDto> = _ejercicioSeleccionado

    private val _listaEquipo = MutableLiveData(listOf<String>())
    val listaEquipo: LiveData<List<String>> = _listaEquipo

    private val _ejerciciosSeleccionadosParaNuevaRutina = MutableLiveData(listOf<EjercicioDto>())
    val ejerciciosSeleccionadosParaNuevaRutina: LiveData<List<EjercicioDto>> =
        _ejerciciosSeleccionadosParaNuevaRutina

    private val _cargando = MutableLiveData<Boolean>()
    val cargando: LiveData<Boolean> = _cargando

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val _iconosRutina = MutableLiveData<List<String>>(
        listOf(
            "r1",
            "r2",
            "r3",
            "r4",
            "r5",
            "r6",
            "r7",
            "r8",
            "r9",
            "r10",
            "r11",
            "r12"
        )
    )
    val iconosRutina: LiveData<List<String>> = _iconosRutina

    private val _iconoEscogido: MutableLiveData<String> = MutableLiveData("mn")
    val iconoEscogido: LiveData<String> = _iconoEscogido

    private val _nombre: MutableLiveData<String> = MutableLiveData("")
    val nombre: LiveData<String> = _nombre

    private val _descripcion: MutableLiveData<String> = MutableLiveData("")
    val descripcion: LiveData<String> = _descripcion

    private val _limiteNombre: MutableLiveData<Int> = MutableLiveData(50)
    val limiteNombre: LiveData<Int> = _limiteNombre

    private val _limiteDescripcion: MutableLiveData<Int> = MutableLiveData(250)
    val limiteDescripcion: LiveData<Int> = _limiteDescripcion

    private val _mensajeToast = MutableLiveData<Int>()
    val mensajeToast: LiveData<Int> = _mensajeToast

    private val _posicionAEliminar  = MutableLiveData<Int>()

    private val _toastMostrado = MutableLiveData<Boolean>()
    val toastMostrado: LiveData<Boolean> = _toastMostrado
    fun cambiarIcono(icono: String) {
        _iconoEscogido.value = icono
        _mostrarVentanacambiarIcono.value = false
        mostrarToast(R.string.iconoCambiado)
    }

    fun obtenerEjerciciosDesdeApi() {
        viewModelScope.launch {
            try {
                _cargando.value = true
                val response = RetrofitClient.apiEjercicios.obtenerEjercicios()
                if (response.isSuccessful) {
                    Log.i("prueba", response.body().toString())
                    val listaEjercicios = response.body() ?: emptyList()
                    _ejerciciosAgrupados.value = listaEjercicios.groupBy { it.grupoMuscular }
                    _error.value = null
                } else {
                    _error.value = "Error al obtener ejercicios: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Excepción: ${e.message}"
            } finally {
                _cargando.value = false
            }
        }
    }

    fun cambiarNombre(nombre: String) {
        if (nombre.length <= _limiteNombre.value!!) {
            _nombre.value = nombre
        }
    }

    fun cambiarDescripcion(descripcion: String) {
        if (descripcion.length <= _limiteDescripcion.value!!) {
            _descripcion.value = descripcion
        }
    }

    fun ensenarVentanaAnadirEjercicio() {
        _mostrarVentanaAnadirEjercicio.value = true
        _mostrarVentanaDetallesEjercicio.value = false
    }

    fun mostrarDetallesEjercicio(ejercicio: EjercicioDto) {
        _mostrarVentanaAnadirEjercicio.value = false
        _mostrarVentanaDetallesEjercicio.value = true
        _ejercicioSeleccionado.value = ejercicio
    }

    fun cerrarVentanaEjercicio() {
        _mostrarVentanaAnadirEjercicio.value = false
    }

    fun anadirEjercicioALaLista() {
        val listaActual = _ejerciciosSeleccionadosParaNuevaRutina.value ?: emptyList()
        val nuevaLista = listaActual.toMutableList()
        nuevaLista.add(_ejercicioSeleccionado.value!!)
        _ejerciciosSeleccionadosParaNuevaRutina.value = nuevaLista
        _mostrarVentanaDetallesEjercicio.value = false
        anadirEquipoALaLista()
    }

    private fun anadirEquipoALaLista() {
        val equipo = _ejercicioSeleccionado.value?.equipo ?: return
        val lista = _listaEquipo.value!!.toMutableList()

        if(_ejerciciosSeleccionadosParaNuevaRutina.value!!.isEmpty()){
            _listaEquipo.value = listOf()
            return
        }
        if (!lista.contains(equipo)) {
            lista.add(equipo)
            _listaEquipo.value = lista
        }
    }

    private fun calcularCaloriasYPuntosObtenido() {
        _puntosGanados.value =
            _ejerciciosSeleccionadosParaNuevaRutina.value!!.sumOf { it.puntoGanadosPorRepeticion * it.cantidad }
                .toInt()
        _calorias.value =
            kotlin.math.floor(((_ejerciciosSeleccionadosParaNuevaRutina.value!!.sumOf { it.caloriasQuemadasPorRepeticion * it.cantidad }) * 100)) / 100
    }

    fun cambiarCantidad(index: Int, cantidad: Int) {
        val listaActual = _ejerciciosSeleccionadosParaNuevaRutina.value ?: return
        val nuevaLista = listaActual.toMutableList()
        val ejercicio = nuevaLista[index].copy(cantidad = cantidad)
        nuevaLista[index] = ejercicio
        _ejerciciosSeleccionadosParaNuevaRutina.value = nuevaLista
        calcularCaloriasYPuntosObtenido()
    }

    private fun validarRutinas(): Boolean {
        if(_nombre.value.isNullOrEmpty()){
            mostrarToast(R.string.errorNombreRutinaVacio)
            return true
        }
        if(_descripcion.value.isNullOrEmpty()){
            mostrarToast(R.string.descripcionRutinaVacia)
            return true
        }
        if (_ejerciciosSeleccionadosParaNuevaRutina.value.isNullOrEmpty()) {
            mostrarToast(R.string.listaEjerciciosVacia)
            return true
        }
        if(_ejerciciosSeleccionadosParaNuevaRutina.value!!.any { it.cantidad <= 0 }){
            mostrarToast(R.string.ejercicioCon0Repeticion)
            return true
        }
        return false
    }

    fun crearRutina(onResultado: (Boolean) -> Unit) {
        if (validarRutinas()) {
            return
        }
        viewModelScope.launch {
            try {
                _cargando.value = true
                val response = RetrofitClient.apiRutinas.crearRutina(
                    RutinaDTO(
                        nombre = _nombre.value!!,
                        imagen = _iconoEscogido.value!!,
                        descripcion = _descripcion.value!!,
                        creadorId = FirebaseAuth.getInstance().currentUser!!.uid,
                        ejercicios = _ejerciciosSeleccionadosParaNuevaRutina.value!!,
                        equipo = _listaEquipo.value?.joinToString(",") ?: "ninguno",
                        votos = 0.0f,
                        totalVotos = 0,
                        esPremium = false
                    )
                )
                if (response.isSuccessful) {
                    onResultado(true)
                    mostrarToast(R.string.rutinaCreada)
                    _error.value = null
                } else {
                    onResultado(false)
                    Log.i("prueba", response.body().toString())
                    _error.value = "Error al obtener ejercicios: ${response.code()}"
                }
            } catch (e: Exception) {
                onResultado(false)
                _error.value = "Excepción: ${e.message}"
            } finally {
                _cargando.value = false
            }
        }
    }

    fun mostrarToast(mensaje: Int) {
        _toastMostrado.value = false
        _mensajeToast.value = mensaje
    }

    fun toastMostrado() {
        _mensajeToast.value = 1
        _toastMostrado.value = true
    }

    fun abrirVentanaImagenes() {
        _mostrarVentanacambiarIcono.value = true
    }

    fun mostrarVentanaEliminarEjercicio(ejercicio: Int){
        _posicionAEliminar.value = ejercicio
        _mostrarVentanaEliminarEjercicio.value = true
    }

    fun eliminarEjercicio() {
        _ejerciciosSeleccionadosParaNuevaRutina.value =
            _ejerciciosSeleccionadosParaNuevaRutina.value
                ?.toMutableList()
                ?.apply { removeAt(_posicionAEliminar.value ?: return) }

        _listaEquipo.value = listOf()
        anadirEquipoALaLista()
        _mostrarVentanaEliminarEjercicio.value = false
    }

    fun cerrarVentanaEliminarEjercicio() {
        _mostrarVentanaEliminarEjercicio.value = false
    }
}

