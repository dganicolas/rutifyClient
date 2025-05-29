package com.example.rutifyclient.viewModel.rutinas

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.rutifyclient.apiservice.network.RetrofitClient
import com.example.rutifyclient.domain.rutinas.RutinaBuscadorDto
import com.example.rutifyclient.viewModel.ViewModelBase
import kotlinx.coroutines.launch

class BusquedaRutinasOnlineViewModel: ViewModelBase() {
    private val _textoBusqueda: MutableLiveData<String> =
        MutableLiveData("")
    val textoBusqueda = _textoBusqueda

    private val _listaRutinas: MutableLiveData<List<RutinaBuscadorDto>> =
        MutableLiveData(emptyList())
    val listaRutinas = _listaRutinas

    fun buscarRutinas(nombre: String?) {
        viewModelScope.launch {
            try {
                _estado.value = false
                _sinInternet.value = false
                _listaRutinas.value = RetrofitClient.apiRutinas.buscarRutinas(nombre).body()

                // Manejar la respuesta
                _estado.value = true

            } catch (e: Exception) {
                _sinInternet.value = true
                manejarErrorConexion(e)
            }
        }
    }

    fun onChangeNombreBusqueda(nuevoTexto: String) {
        _textoBusqueda.value = nuevoTexto
    }
}
