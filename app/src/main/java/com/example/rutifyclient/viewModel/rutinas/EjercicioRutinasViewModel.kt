package com.example.rutifyclient.viewModel.rutinas

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.rutifyclient.R
import com.example.rutifyclient.apiservice.network.RetrofitClient
import com.example.rutifyclient.domain.ejercicio.EjercicioDto
import com.example.rutifyclient.domain.estadisticas.EstadisticasDiariasPatchDto
import com.example.rutifyclient.domain.estadisticas.EstadisticasDto
import com.example.rutifyclient.domain.estadisticas.EstadisticasPatchDto
import com.example.rutifyclient.domain.voto.VotodDto
import com.example.rutifyclient.utils.ente
import com.example.rutifyclient.viewModel.ViewModelBase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class EjercicioRutinasViewModel : ViewModelBase() {

    private val _ejerciciosCargados = MutableLiveData(false)

    private val _listaejercicios = MutableLiveData(listOf<EjercicioDto>())

    private val _ejercicio = MutableLiveData(EjercicioDto("","","","","","",0.0,0.0,0))
    val ejercicio: LiveData<EjercicioDto> = _ejercicio

    private val _contadorEjercicios = MutableLiveData(0)

    private val _voto = MutableLiveData<VotodDto>(VotodDto(null,FirebaseAuth.getInstance().currentUser!!.uid,ente.rutina.value!!,"",0.0f))
    val voto: LiveData<VotodDto> = _voto

    private val _finalizado = MutableLiveData(false)
    val finalizado: LiveData<Boolean> = _finalizado

    private val _VentanaPuntuarRutina = MutableLiveData(false)
    val VentanaPuntuarRutina: LiveData<Boolean> = _VentanaPuntuarRutina

    private val _cancelado = MutableLiveData(false)
    val cancelado: LiveData<Boolean> = _cancelado

    private val _estadisticasDto = MutableLiveData(EstadisticasDto("",0.0,0.0,0.0,0.0,0.0,0.0,0.0,0,0.0))
    val estadisticas: LiveData<EstadisticasDto> = _estadisticasDto

    private val _estadisticasDtoCalculadas = MutableLiveData(EstadisticasDto("",0.0,0.0,0.0,0.0,0.0,0.0,0.0,0,0.0))
    val estadisticasDtoCalculadas: LiveData<EstadisticasDto> = _estadisticasDtoCalculadas

    private val _tiempo = MutableLiveData(0) // Tiempo en segundos
    val tiempo: LiveData<Int> = _tiempo

    private var job: Job? = null

    fun iniciarTemporizador() {
        job?.cancel()
        _tiempo.value = 0
        job = viewModelScope.launch {
            while (isActive) {
                delay(1000)
                _tiempo.postValue((_tiempo.value ?: 0) + 1)
            }
        }
    }
    fun detenerTemporizador() {
        job?.cancel()
    }

    fun cargarEjercicio() {
        _sinInternet.value = false
        if (_ejerciciosCargados.value == true) return
        _listaejercicios.value = ente.listaEjercicio.value!!
        _ejercicio.value = _listaejercicios.value!![_contadorEjercicios.value!!]
        _estado.value = true
    }

    fun obtenervoto(){
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiVotos.obtenerVoto(
                        idFirebase = FirebaseAuth.getInstance().currentUser!!.uid,
                        idRutina =  ente.rutina.value!!,
                )
                if (response.isSuccessful && response.body() != null) {
                    _voto.value = response.body()
                }
            } catch (e: Exception) {
                _sinInternet.value = true
                manejarErrorConexion(e)
                mostrarToast(R.string.error_conexion)
            }
        }
    }


    fun siguienteEjercicio() {

        if(_contadorEjercicios.value!!.plus(1) <= _listaejercicios.value!!.size - 1){
            _contadorEjercicios.value = _contadorEjercicios.value?.plus(1)
            _ejercicio.value = _listaejercicios.value!![_contadorEjercicios.value!!]
        }else{
            _finalizado.value = true
            calcularProgreso()
        }

    }
    private fun calcularProgreso(){
        _listaejercicios.value!!.forEach{ejercicio ->
            _estadisticasDtoCalculadas.value!!.ejerciciosRealizados += 1
            _estadisticasDtoCalculadas.value!!.kCaloriasQuemadas += (ejercicio.cantidad * ejercicio.caloriasQuemadasPorRepeticion)/1000
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
                    Log.i("prueba", response.body().toString())
                    _estadisticasDto.value = response.body()
                }
            } catch (e: Exception) {
                _sinInternet.value = true
                mostrarToast(R.string.error_conexion)
            }
        }
    }

    fun guardarProgreso(guardado: (Boolean) -> Unit) {
        _estado.value = false
        _estadisticasDto.value!!.lvlBrazo += _estadisticasDtoCalculadas.value!!.lvlBrazo
        _estadisticasDto.value!!.lvlPecho += _estadisticasDtoCalculadas.value!!.lvlPecho
        _estadisticasDto.value!!.lvlEspalda += _estadisticasDtoCalculadas.value!!.lvlEspalda
        _estadisticasDto.value!!.lvlAbdominal += _estadisticasDtoCalculadas.value!!.lvlAbdominal
        _estadisticasDto.value!!.lvlPiernas += _estadisticasDtoCalculadas.value!!.lvlPiernas
        Log.i("prueba", _estadisticasDto.value!!.kCaloriasQuemadas.toString())
        _estadisticasDto.value!!.ejerciciosRealizados += _estadisticasDtoCalculadas.value!!.ejerciciosRealizados
        _estadisticasDto.value!!.kCaloriasQuemadas += _estadisticasDtoCalculadas.value!!.kCaloriasQuemadas
        _estadisticasDto.value!!.horasActivo += _tiempo.value!!.toDouble() / 3600.0
        Log.i("prueba", _estadisticasDto.value!!.kCaloriasQuemadas.toString())
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
                    horasActivo = _estadisticasDto.value!!.horasActivo,
                    lvlEspalda = _estadisticasDto.value!!.lvlEspalda,
                    lvlAbdominal = _estadisticasDto.value!!.lvlAbdominal,
                    lvlPiernas = _estadisticasDto.value!!.lvlPiernas,
                    ejerciciosRealizados = _estadisticasDto.value!!.ejerciciosRealizados,
                    kCaloriasQuemadas = _estadisticasDto.value!!.kCaloriasQuemadas
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
                _sinInternet.value = true
                manejarErrorConexion(e)
                mostrarToast(R.string.error_conexion)
                guardado(false)
            }finally {
                _estado.value = true
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
                _sinInternet.value = true
                manejarErrorConexion(e)
                mostrarToast(R.string.error_conexion)
                guardado(false)
            }finally {
                _estado.value = true
            }
        }
    }

    fun cancelarRutina() {
        _cancelado.value = true
    }

    fun VentanaPuntuarRutina(estado:Boolean) {
        _VentanaPuntuarRutina.value = estado
    }

    fun cambiarPuntuacion(puntuacion: Float) {
        _voto.value = _voto.value?.copy(puntuacion = puntuacion)
    }

    fun guardarVoto() {
        _estado.value = false
        _VentanaPuntuarRutina.value = false
        if(!_voto.value!!.id.isNullOrEmpty()){
            actualizarVoto()
        }else{
            crearVoto()
        }
    }

    private fun crearVoto() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiVotos.registrarVoto(
                    _voto.value!!
                )
                if (response.isSuccessful && response.body() != null) {
                    _voto.value = response.body()
                    mostrarToast(R.string.votoGuardado)
                }else{
                    mostrarToast(R.string.dato_defecto)
                }
            } catch (e: Exception) {
                manejarErrorConexion(e)
                _sinInternet.value = true
                mostrarToast(R.string.error_conexion)
            }finally {
                _estado.value = true
            }
        }
    }

    private fun actualizarVoto() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiVotos.actualizarVoto(
                    _voto.value!!
                )
                if (response.isSuccessful && response.body() != null) {
                    _voto.value = response.body()
                    mostrarToast(R.string.votoGuardado)
                }else{
                    mostrarToast(R.string.dato_defecto)
                }

            } catch (e: Exception) {
                manejarErrorConexion(e)
                _sinInternet.value = true
                mostrarToast(R.string.error_conexion)
            }finally {
                _estado.value = true
            }
        }
    }

    fun guardarEstadisticaDiaria() {
        viewModelScope.launch {
            try {
                val patch = EstadisticasDiariasPatchDto(
                    ejerciciosRealizados = _estadisticasDtoCalculadas.value!!.ejerciciosRealizados,
                    kCaloriasQuemadas = _estadisticasDtoCalculadas.value!!.kCaloriasQuemadas,
                    horasActivo = _tiempo.value!!.toDouble() / 3600.0
                )

                val idFirebase = FirebaseAuth.getInstance().currentUser!!.uid
                val fecha = java.time.LocalDate.now().toString() // Formato: yyyy-MM-dd

                val response = RetrofitClient.apiEstadisticasDiarias.actualizarEstadisticasDiarias(
                    idFirebase = idFirebase,
                    fecha = fecha,
                    patch = patch
                )

                if (response.isSuccessful && response.body() != null) {
                    mostrarToast(R.string.estadisticas_diarias_guardadas)
                } else {
                    mostrarToast(R.string.error_guardar)
                }

            } catch (e: Exception) {
                manejarErrorConexion(e)
                _sinInternet.value = true
                mostrarToast(R.string.error_conexion)
            }
        }
    }

}
