package com.example.rutifyclient.viewModel

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
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime

class perfilViewModel:ViewModelBase() {
    private val _metaKcal = MutableLiveData(0)
    val metaKcal: LiveData<Int> = _metaKcal
    private val _metaRutinas = MutableLiveData(0)
    val metaRutinas: LiveData<Int> = _metaRutinas
    private val _metasMinActivos = MutableLiveData(0.0f)
    val metasMinActivos: LiveData<Float> = _metasMinActivos
    private val _perfilprivado = MutableLiveData(false)
    val perfilprivado: LiveData<Boolean> = _perfilprivado

    private val _idFirebase = MutableLiveData("")
    val idFirebase: LiveData<String> = _idFirebase
    private val _ultimosPesos = MutableLiveData<List<Double>>(listOf(0.0, 0.0, 0.0, 0.0, 0.0))
    val ultimosPesos: LiveData<List<Double>> = _ultimosPesos

    val _fecha = MutableLiveData(LocalDate.now())
    val fecha: LiveData<LocalDate> = _fecha


    private val _estadisticasDto = MutableLiveData(EstadisticasDto("",0.0,0.0,0.0,0.0,0.0,0.0,0.0,0,0.0))
    val estadisticas: LiveData<EstadisticasDto> = _estadisticasDto

    private val _estadisticasDiarias = MutableLiveData(EstadisticasDiariasDto(null,"", LocalDate.now(),0.0,0.0,0,0.0))
    val estadisticasDiarias: LiveData<EstadisticasDiariasDto> = _estadisticasDiarias


    override fun obtenerUsuario() {
        _sinInternet.value = false
        _estado.value = false
        viewModelScope.launch {
            try {
                val response =
                    RetrofitClient.apiUsuarios.obtenerDetalleUsuario(_idFirebase.value!!)

                if (response.isSuccessful) {
                    _usuario.value = response.body()
                }else {
                    when (response.code()) {
                        401 -> {
                            _perfilprivado.value = true
                        }
                        else -> {
                        }
                    }
                }
        } catch (e: Exception) {
                manejarErrorConexion(e)
                _sinInternet.value=true
                mostrarToast(R.string.error_conexion)
            }finally {
                _estado.value = true
            }
        }
    }


    fun obtenerEstadisticasDiaria() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiEstadisticasDiarias.obtenerEstadisticasDiariasDia(
                    _idFirebase.value!!,
                    _fecha.value!!
                )
                if (response.isSuccessful) {
                    if(response.body() != null) _estadisticasDiarias.value = response.body()
                }
            } catch (e: Exception) {
                manejarErrorConexion(e)
                _sinInternet.value = true
                mostrarToast(R.string.error_conexion)
            }
        }
    }

    fun obtenerUltimos5Pesos() {
        viewModelScope.launch {
            try {
                val idFirebase = _idFirebase.value!!
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

    fun obtenerIdFirebase(idFirebase: String) {
        _idFirebase.value = idFirebase
    }
}
