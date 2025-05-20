package com.example.rutifyclient.viewModel.rutinas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rutifyclient.R
import com.example.rutifyclient.apiservice.network.RetrofitClient
import com.example.rutifyclient.domain.ejercicio.EjercicioDto
import com.example.rutifyclient.domain.estadisticas.EstadisticasDto
import com.example.rutifyclient.domain.estadisticas.EstadisticasPatchDto
import com.example.rutifyclient.utils.ente
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class EjercicioRutinasViewModel : ViewModel() {

    private val _ejerciciosCargados = MutableLiveData(false)
    private val _listaejercicios = MutableLiveData(listOf<EjercicioDto>())
    private val _ejercicio = MutableLiveData(EjercicioDto("","","","","","",0.0,0.0,0))
    val ejercicio = _ejercicio

    private val _contadorEjercicios = MutableLiveData(0)

    private val _mensajeToast = MutableLiveData<Int>()
    val mensajeToast: LiveData<Int> = _mensajeToast
    private val _toastMostrado = MutableLiveData<Boolean>()
    val toastMostrado: LiveData<Boolean> = _toastMostrado

    private val _finalizado = MutableLiveData(false)
    val finalizado: LiveData<Boolean> = _finalizado
    private val _cancelado = MutableLiveData(false)
    val cancelado: LiveData<Boolean> = _cancelado

    private val _estadisticasDto = MutableLiveData(EstadisticasDto("",0.0,0.0,0.0,0.0,0.0,0,0.0))
    val estadisticas: LiveData<EstadisticasDto> = _estadisticasDto

    private val _estadisticasDtoCalculadas = MutableLiveData(EstadisticasDto("",0.0,0.0,0.0,0.0,0.0,0,0.0))
    val estadisticasDtoCalculadas: LiveData<EstadisticasDto> = _estadisticasDtoCalculadas

    private val _tiempo = MutableLiveData(0) // Tiempo en segundos
    val tiempo: LiveData<Int> = _tiempo
    private var job: Job? = null

    private fun mostrarToast(mensaje: Int) {
        _toastMostrado.value = false
        _mensajeToast.value = mensaje
    }

    fun toastMostrado() {
        _mensajeToast.value = 1
        _toastMostrado.value = true
    }

    fun iniciarTemporizador() {
        job = viewModelScope.launch {
            while (isActive) {
                delay(1000)
                _tiempo.postValue((_tiempo.value ?: 0) + 1)
            }
        }
    }

    fun cargarEjercicio() {
        if (_ejerciciosCargados.value == true) return
        _listaejercicios.value = ente.listaEjercicio
        _ejercicio.value = _listaejercicios.value!![_contadorEjercicios.value!!]
    }

    fun siguienteEjercicio() {
        _contadorEjercicios.value = _contadorEjercicios.value?.plus(1)
        if(_contadorEjercicios.value!! <= _listaejercicios.value!!.size - 1){
            _ejercicio.value = _listaejercicios.value!![_contadorEjercicios.value!!]
        }else{
            _finalizado.value = true
            calcularProgreso()
        }

    }
    private fun calcularProgreso(){
        _listaejercicios.value!!.forEach{ejercicio ->
            _estadisticasDtoCalculadas.value!!.ejerciciosRealizados += ejercicio.cantidad
            _estadisticasDtoCalculadas.value!!.caloriasQuemadas += ejercicio.cantidad * ejercicio.caloriasQuemadasPorRepeticion
            when ( ejercicio.grupoMuscular.lowercase()){
                "pecho" -> { _estadisticasDtoCalculadas.value!!.lvlPecho += ejercicio.cantidad * ejercicio.puntoGanadosPorRepeticion}
                "abdominal" -> { _estadisticasDtoCalculadas.value!!.lvlAbdominal += ejercicio.cantidad * ejercicio.puntoGanadosPorRepeticion }
                "espalda" -> {_estadisticasDtoCalculadas.value!!.lvlEspalda += ejercicio.cantidad * ejercicio.puntoGanadosPorRepeticion}
                "hombro","bicep","tricep" -> {_estadisticasDtoCalculadas.value!!.lvlBrazo += ejercicio.cantidad * ejercicio.puntoGanadosPorRepeticion }
                "pierna" -> { _estadisticasDtoCalculadas.value!!.lvlPiernas += ejercicio.cantidad * ejercicio.puntoGanadosPorRepeticion }
                else -> {}
            }
        }
    }

    fun obtenerEstadisticas() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiEstadisticas.obtenerEstadisticas(FirebaseAuth.getInstance().currentUser!!.uid)
                if (response.isSuccessful) {
                    _estadisticasDto.value = response.body()
                }
            } catch (e: Exception) {
                mostrarToast(R.string.error_conexion)
            }
        }
    }

    fun guardarProgreso(guardado: (Boolean) -> Unit) {
        _estadisticasDto.value!!.lvlBrazo += _estadisticasDtoCalculadas.value!!.lvlBrazo
        _estadisticasDto.value!!.lvlPecho += _estadisticasDtoCalculadas.value!!.lvlPecho
        _estadisticasDto.value!!.lvlEspalda += _estadisticasDtoCalculadas.value!!.lvlEspalda
        _estadisticasDto.value!!.lvlAbdominal += _estadisticasDtoCalculadas.value!!.lvlAbdominal
        _estadisticasDto.value!!.lvlPiernas += _estadisticasDtoCalculadas.value!!.lvlPiernas
        _estadisticasDto.value!!.ejerciciosRealizados += _estadisticasDtoCalculadas.value!!.ejerciciosRealizados
        _estadisticasDto.value!!.caloriasQuemadas += _estadisticasDtoCalculadas.value!!.caloriasQuemadas

        if(_estadisticasDto.value!!.idFirebase.isNotEmpty()){
            actualizarEstadisticas{
                guardado(it)
            }
        }else{
            crearEstadisticas{
                guardado(it)
            }
        }
    }

    private fun actualizarEstadisticas(guardado: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val patch = EstadisticasPatchDto(
                    lvlBrazo = _estadisticasDto.value!!.lvlBrazo,
                    lvlPecho = _estadisticasDto.value!!.lvlPecho,
                    lvlEspalda = _estadisticasDto.value!!.lvlEspalda,
                    lvlAbdominal = _estadisticasDto.value!!.lvlAbdominal,
                    lvlPiernas = _estadisticasDto.value!!.lvlPiernas,
                    ejerciciosRealizados = _estadisticasDto.value!!.ejerciciosRealizados,
                    caloriasQuemadas = _estadisticasDto.value!!.caloriasQuemadas
                )

                val response = RetrofitClient.apiEstadisticas.actualizarEstadisticas(
                    usuarioId = _estadisticasDto.value!!.idFirebase,
                    estadisticasActualizadas = patch
                )

                if (response.isSuccessful && response.body() != null) {
                    _estadisticasDto.value = response.body()
                    mostrarToast(R.string.estadisticasGuardada)
                    guardado(true)
                } else {
                    mostrarToast(R.string.error_guardar)
                    guardado(false)
                }
            } catch (e: Exception) {
                mostrarToast(R.string.error_conexion)
                guardado(false)
            }
        }
    }

    private fun crearEstadisticas(guardado: (Boolean) -> Unit) {
        _estadisticasDto.value!!.idFirebase = FirebaseAuth.getInstance().currentUser!!.uid
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiEstadisticas.crearEstadisticas(_estadisticasDto.value!!)

                if (response.isSuccessful && response.body() != null) {
                    _estadisticasDto.value = response.body()
                    mostrarToast(R.string.estadisticasGuardada)
                    guardado(true)
                } else {
                    mostrarToast(R.string.error_guardar)
                    guardado(false)
                }
            } catch (e: Exception) {
                mostrarToast(R.string.error_conexion)
                guardado(false)
            }
        }
    }

    fun cancelarRutina() {
        _cancelado.value = true
    }

}
