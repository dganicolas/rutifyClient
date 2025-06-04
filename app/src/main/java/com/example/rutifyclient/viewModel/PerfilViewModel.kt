package com.example.rutifyclient.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.rutifyclient.R
import com.example.rutifyclient.apiservice.network.RetrofitClient
import com.example.rutifyclient.domain.estadisticas.EstadisticasDto
import kotlinx.coroutines.launch

class PerfilViewModel:ViewModelBase() {
    private val _noExiste = MutableLiveData(false)
    val noExiste: LiveData<Boolean> = _noExiste
    private val _perfilprivado = MutableLiveData(false)
    val perfilprivado: LiveData<Boolean> = _perfilprivado

    private val _ultimosPesos = MutableLiveData<List<Double>>(listOf(0.0, 0.0, 0.0, 0.0, 0.0))
    val ultimosPesos: LiveData<List<Double>> = _ultimosPesos


    private val _estadisticasDto = MutableLiveData(EstadisticasDto("",0.0,0.0,0.0,0.0,0.0,0.0,0.0,0,0.0))
    val estadisticas: LiveData<EstadisticasDto> = _estadisticasDto


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
                        404 -> {
                            _noExiste.value = true
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
