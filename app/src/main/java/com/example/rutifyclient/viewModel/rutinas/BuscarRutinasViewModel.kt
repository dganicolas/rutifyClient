package com.example.rutifyclient.viewModel.rutinas

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rutifyclient.R
import com.example.rutifyclient.apiservice.network.RetrofitClient
import com.example.rutifyclient.domain.rutinas.RutinaBuscadorDto
import kotlinx.coroutines.launch

class BuscarRutinasViewModel: ViewModel() {
    private val _listaRutinas:MutableLiveData<List<RutinaBuscadorDto>>  = MutableLiveData(emptyList())
    val listaRutinas = _listaRutinas
    val _pagina = MutableLiveData(0)
    val _elementos = MutableLiveData(10)
    private val ultimaPagina = MutableLiveData(false)
    private val _equipo: MutableLiveData<String?> = MutableLiveData(null)
    private val _finalDeRutinas = MutableLiveData(false)
    private val _mensajeToast = MutableLiveData<Int>()
    val mensajeToast: LiveData<Int> = _mensajeToast

    private val _toastMostrado = MutableLiveData<Boolean>()
    val toastMostrado: LiveData<Boolean> = _toastMostrado

    private val _sinInternet = MutableLiveData<Boolean>(false)
    val sinInternet: LiveData<Boolean> = _sinInternet

    fun obtenerRutinas(){
        _sinInternet.value = false
        if(_finalDeRutinas.value ==true){
            mostrarToast(R.string.noHayMasResultados)
            return
        }
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiRutinas.verRutinas(_pagina.value!!,_elementos.value!!,_equipo.value)
                if (response.isSuccessful) {
                    Log.i("prueba", response.body().toString())
                    _finalDeRutinas.value = response.body()!!.isEmpty()
                    if(!_finalDeRutinas.value!!){
                        mostrarToast(R.string.cargandoMas)
                    }else{
                        mostrarToast(R.string.noHayMasResultados)
                    }
                    _listaRutinas.value = _listaRutinas.value!! + response.body()!!
                }
            } catch (e: Exception) {
                _sinInternet.value = true
                mostrarToast(R.string.error_conexion)
            }
        }
        _pagina.value = _pagina.value?.plus(1)
    }
    fun mostrarToast(mensaje: Int) {
        _toastMostrado.value = false
        _mensajeToast.value = mensaje
    }
    fun toastMostrado() {
        _mensajeToast.value = 1
        _toastMostrado.value = true
    }

    fun reiniciarPaginacion() {
        _pagina.value = 0
        ultimaPagina.value = false
        _listaRutinas.value = emptyList()
    }

}