package com.example.rutifyclient.viewModel.usuario

import android.content.Context
import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.rutifyclient.R
import com.example.rutifyclient.apiservice.local.room.database.RutinaDatabase
import com.example.rutifyclient.apiservice.network.RetrofitClient
import com.example.rutifyclient.domain.ejercicio.EjercicioDto
import com.example.rutifyclient.domain.estadisticas.EstadisticasDiariasDto
import com.example.rutifyclient.domain.estadisticas.EstadisticasDto
import com.example.rutifyclient.domain.usuario.ActualizarUsuarioDTO
import com.example.rutifyclient.viewModel.ViewModelBase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime

class MiZonaViewModel : ViewModelBase() {

    private val _metaKcal = MutableLiveData(0)
    val metaKcal: LiveData<Int> = _metaKcal
    private val _metaRutinas = MutableLiveData(0)
    val metaRutinas: LiveData<Int> = _metaRutinas
    private val _metasMinActivos = MutableLiveData(0.0f)
    val metasMinActivos: LiveData<Float> = _metasMinActivos
    private val _ultimosPesos = MutableLiveData<List<Double>>(listOf(0.0, 0.0, 0.0, 0.0, 0.0))
    val ultimosPesos: LiveData<List<Double>> = _ultimosPesos
    private val _imagenes = MutableLiveData<List<String>>(listOf(
        "a1", "a2", "a3", "a4", "a5", "a6", "a7",
        "a8", "a9", "a10", "a11", "a12", "a13", "a14",
        "a15", "a16", "a17", "a18", "a19", "a20", "a21"
    ))
    val imagenes: LiveData<List<String>> = _imagenes

    val _fecha = MutableLiveData(LocalDate.now())
    val fecha: LiveData<LocalDate> = _fecha

    val _ventanaReto = MutableLiveData(false)
    val ventanaReto: LiveData<Boolean> = _ventanaReto

    val _ejerciciosReto = MutableLiveData(EjercicioDto("","","","","","",0.0,0.0,0))
    val ejerciciosReto: LiveData<EjercicioDto> = _ejerciciosReto

    private val _mostrarVentanacambiarIcono = MutableLiveData(false)
    val mostrarVentanacambiarIcono: LiveData<Boolean> = _mostrarVentanacambiarIcono

    private val _tiempoRestante = MutableLiveData(0)
    val tiempoRestante: LiveData<Int> = _tiempoRestante

    private var _countDownTimer: CountDownTimer? = null

    private val _estadisticasDto = MutableLiveData(EstadisticasDto("",0.0,0.0,0.0,0.0,0.0,0.0,0.0,0,0.0))
    val estadisticas: LiveData<EstadisticasDto> = _estadisticasDto

    private val _estadisticasDiarias = MutableLiveData(EstadisticasDiariasDto(null,"", LocalDate.now(),0.0,0.0,0,0.0))
    val estadisticasDiarias: LiveData<EstadisticasDiariasDto> = _estadisticasDiarias

    private val _countRutinasFavoritas = MutableLiveData<Int>()
    val countRutinasFavoritas: LiveData<Int> = _countRutinasFavoritas

    fun obtenerCountRutinasFavoritas(context: Context) {
        val db = RutinaDatabase.obtenerInstancia(context)
        val rutinaDao = db.rutinaDao()

        viewModelScope.launch {
            try {
                val count = rutinaDao.contarRutinasFavoritas()
                _countRutinasFavoritas.value = count
            } catch (e: Exception) {
                // Manejar error si quieres
                _countRutinasFavoritas.value = 0
            }
        }
    }

    fun obtenerSegundosRestantesDelDia(): Int {
        val ahora = LocalDateTime.now()
        val finDelDia = ahora.toLocalDate().atTime(23, 59, 59)
        return Duration.between(ahora, finDelDia).seconds.toInt().coerceAtLeast(0)
    }

    fun iniciarContadorTiempoRestante() {
        _countDownTimer?.cancel() // Cancelar si ya existe
        val segundos = obtenerSegundosRestantesDelDia().toLong() * 1000
        _countDownTimer = object : CountDownTimer(segundos, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                _tiempoRestante.value = (millisUntilFinished / 1000).toInt()
            }

            override fun onFinish() {
                _tiempoRestante.value = 0
            }
        }.start()
    }

    fun obtenerEstadisticasDiaria() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiEstadisticasDiarias.obtenerEstadisticasDiariasDia(FirebaseAuth.getInstance().currentUser!!.uid,
                    _fecha.value!!
                )
                if (response.isSuccessful && response.body() != null) {
                   _estadisticasDiarias.value = response.body()
                }
            } catch (e: Exception) {
                manejarErrorConexion(e)
                _sinInternet.value = true
                mostrarToast(R.string.error_conexion)
            }
        }
    }

    fun obtenerObjetivosLocal(context: Context) {
        val db = RutinaDatabase.obtenerInstancia(context)
        val objetivosDao = db.objetivosDao()
        viewModelScope.launch {
            try {
                val idUsuario = FirebaseAuth.getInstance().currentUser?.uid
                idUsuario?.let {
                    val data = objetivosDao.obtenerPorId(it)
                    data?.let { objetivos ->
                        _metaKcal.value = objetivos.kcalDiarias
                        _metaRutinas.value = objetivos.rutinasObjetivo
                        _metasMinActivos.value = objetivos.minutosActivosObjetivo
                    }
                }
            } catch (e: Exception) {
                manejarErrorConexion(e)
            }
        }
    }

    fun obtenerUltimos5Pesos() {
        viewModelScope.launch {
            try {
                val idFirebase = FirebaseAuth.getInstance().currentUser!!.uid
                val response = RetrofitClient.apiEstadisticasDiarias.obtenerUltimos5Pesos(idFirebase)
                if (response.isSuccessful) {
                    val pesos = response.body() ?: listOf(0.0, 0.0, 0.0, 0.0, 0.0)
                    _ultimosPesos.value = pesos
                } else {
                    _ultimosPesos.value = listOf(0.0, 0.0, 0.0, 0.0, 0.0)
                }
            } catch (e: Exception) {
                manejarErrorConexion(e)
            }
        }
    }

    fun mostrarVentanaRetoDiario() {
        _estado.value = false
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiEjercicios.retoDiario()
                if (response.isSuccessful) {
                    _estado.value = true
                    _ventanaReto.value = true
                    _ejerciciosReto.value = response.body()
                }
            } catch (e: Exception) {
                manejarErrorConexion(e)
            }
        }
    }

    fun cerrarVentanaReto() {
        _ventanaReto.value = false
    }

    fun puntuarReto() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiUsuarios.marcarRetoDiario()
                if (response.isSuccessful) {
                    _ventanaReto.value = false

                }
            } catch (e: Exception) {
                manejarErrorConexion(e)
            }finally {
                obtenerUsuario()
            }
        }
    }

    fun mostrarVentanacambiarIcono(){
        _mostrarVentanacambiarIcono.value = !_mostrarVentanacambiarIcono.value!!
    }

    fun cambiarIcono(nuevoIcono: String) {
        viewModelScope.launch {

            val dto = ActualizarUsuarioDTO(
                correo = _usuario.value!!.correo,
                avatar = nuevoIcono
            )
                try {
                    val response = RetrofitClient.apiUsuarios.actualizarCuenta(dto)
                    if (response.isSuccessful) {
                        mostrarToast(R.string.avatarCambiado)
                        obtenerUsuario()
                    }
                } catch (e: Exception) {
                    mostrarToastApi(e.toString())
                }
            }
        }


}
