package com.example.rutifyclient.viewModel.rutinas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rutifyclient.domain.ejercicio.EjercicioDto
import com.example.rutifyclient.domain.estadisticas.EstadisticasDto
import com.example.rutifyclient.utils.ente
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class EjercicioRutinasViewModel : ViewModel() {

    private val _ejerciciosCargados = MutableLiveData(false)
    private val _listaejercicios = MutableLiveData(listOf<EjercicioDto>())
    private val _ejercicio = MutableLiveData(EjercicioDto("","","","","","",0.0,0.0,0))
    val ejercicio = _ejercicio
    private val estadisticasDto = MutableLiveData(EstadisticasDto(0f,0f,0f,0f,0,0f))
    private val _contadorEjercicios = MutableLiveData(0)


    private val _tiempo = MutableLiveData(0) // Tiempo en segundos
    val tiempo: LiveData<Int> = _tiempo
    private var job: Job? = null

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

    fun finalizarRutina() {

    }

    fun siguienteEjercicio() {
        _contadorEjercicios.value = _contadorEjercicios.value?.plus(1)
        if(_contadorEjercicios.value!! <= _listaejercicios.value!!.size - 1){
            _ejercicio.value = _listaejercicios.value!![_contadorEjercicios.value!!]
        }

    }

    fun obtenerEstadisticas() {
        TODO("Not yet implemented")
    }

}
