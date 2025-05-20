package com.example.rutifyclient.viewModel.detallesRutinas

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rutifyclient.R
import com.example.rutifyclient.apiservice.local.room.dao.RutinaDao
import com.example.rutifyclient.apiservice.local.room.database.RutinaDatabase
import com.example.rutifyclient.apiservice.network.RetrofitClient
import com.example.rutifyclient.domain.rutinas.RutinaDTO
import com.example.rutifyclient.utils.MapearDeRutinaDtoARutinaDtoRoom
import kotlinx.coroutines.launch

class DetallesRutinasViewModel: ViewModel() {

    private val _favorito = MutableLiveData<Boolean>(false)
    val favorito: LiveData<Boolean> = _favorito

    private val _sinInternet = MutableLiveData<Boolean>(false)
    val sinInternet: LiveData<Boolean> = _sinInternet

    private val _estado = MutableLiveData<Boolean>(false)
    val estado: LiveData<Boolean> = _estado

    private val _rutina = MutableLiveData<RutinaDTO>()
    val rutina: LiveData<RutinaDTO> = _rutina

    private val _mensajeToast = MutableLiveData<Int>()
    val mensajeToast: LiveData<Int> = _mensajeToast

    private val _toastMostrado = MutableLiveData<Boolean>()
    val toastMostrado: LiveData<Boolean> = _toastMostrado

    private var rutinaDao: RutinaDao? = null

    fun inicializarBD(context: Context) {
        rutinaDao = RutinaDatabase.obtenerInstancia(context).rutinaDao()
    }

    fun comprobarFavorito(context: Context, idRutina: String) {
        viewModelScope.launch {
            val rutina = RutinaDatabase.obtenerInstancia(context).rutinaDao().obtenerPorId(idRutina)
            _favorito.value = rutina != null
        }
    }

    fun marcarComoFavorita() {
        _favorito.value = !_favorito.value!!
        if (favorito.value!!){
            viewModelScope.launch {
                rutinaDao?.insertar(MapearDeRutinaDtoARutinaDtoRoom(_rutina.value!!))
                mostrarToast(R.string.rutinaGuardada)
            }
        }else{
            viewModelScope.launch {
                rutinaDao?.eliminar(MapearDeRutinaDtoARutinaDtoRoom(_rutina.value!!))
                mostrarToast(R.string.rutinaNoGuardada)
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

    fun obtenerRutina(idRutina: String) {
        _sinInternet.value = false
        _estado.value = false
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiRutinas.obtenerRutina(idRutina)
                if (response.isSuccessful) {
                    _rutina.value = response.body()
                    _estado.value = true
                }
            } catch (e: Exception) {
                _sinInternet.value = true
                mostrarToast(R.string.error_conexion)
            }
        }
    }




}
