package com.example.rutifyclient.viewModel.rutinas

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rutifyclient.R
import com.example.rutifyclient.apiservice.network.RetrofitClient
import com.example.rutifyclient.domain.rutinas.RutinaBuscadorDto
import com.example.rutifyclient.viewModel.ViewModelBase
import kotlinx.coroutines.launch

class BuscarRutinasViewModel : ViewModelBase() {
    private val _listaRutinas: MutableLiveData<List<RutinaBuscadorDto>> =
        MutableLiveData(emptyList())
    val listaRutinas = _listaRutinas
    val _pagina = MutableLiveData(0)
    val _elementos = MutableLiveData(10)
    private val ultimaPagina = MutableLiveData(false)
    private val _equipo: MutableLiveData<String?> = MutableLiveData(null)
    private val _finalDeRutinas = MutableLiveData(false)

    private val _estado = MutableLiveData<Boolean>(false)
    val estado: LiveData<Boolean> = _estado

    private val _toastFinalDeRutinaMostrado = MutableLiveData<Boolean>(false)

    fun obtenerRutinas() {
        _sinInternet.value = false
        if (_finalDeRutinas.value == true) {
            if (_toastFinalDeRutinaMostrado.value == false) {
                mostrarToast(R.string.noHayMasResultados)
                _toastFinalDeRutinaMostrado.value = true
            }
            return
        }
        _estado.value = false
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiRutinas.verRutinas(
                    _pagina.value!!,
                    _elementos.value!!,
                    _equipo.value
                )
                if (response.isSuccessful) {
                    _estado.value = true
                    _finalDeRutinas.value = response.body()!!.isEmpty()
                    if (!_finalDeRutinas.value!!) {
                        mostrarToast(R.string.cargandoMas)
                    } else {
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

    fun reiniciarPaginacion() {
        _finalDeRutinas.value = false
        _pagina.value = 0
        ultimaPagina.value = false
        _listaRutinas.value = emptyList()
    }
}