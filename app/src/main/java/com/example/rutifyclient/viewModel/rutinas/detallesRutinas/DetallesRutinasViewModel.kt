package com.example.rutifyclient.viewModel.rutinas.detallesRutinas

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.rutifyclient.R
import com.example.rutifyclient.apiservice.local.room.dao.RutinaDao
import com.example.rutifyclient.apiservice.local.room.database.RutinaDatabase
import com.example.rutifyclient.apiservice.network.RetrofitClient
import com.example.rutifyclient.domain.rutinas.RutinaDTO
import com.example.rutifyclient.utils.MapearDeRutinaDtoARutinaDtoRoom
import com.example.rutifyclient.utils.ente
import com.example.rutifyclient.viewModel.ViewModelBase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DetallesRutinasViewModel: ViewModelBase() {

    private val _favorito = MutableLiveData<Boolean>(false)
    val favorito: LiveData<Boolean> = _favorito



    private val _ventanaEliminarRutina = MutableLiveData<Boolean>(false)
    val ventanaEliminarRutina: LiveData<Boolean> = _ventanaEliminarRutina

    private val _rutina = MutableLiveData<RutinaDTO>()
    val rutina: LiveData<RutinaDTO> = _rutina

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

    fun obtenerRutina(idRutina: String) {
        _sinInternet.value = false
        _estado.value = false
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiRutinas.obtenerRutina(idRutina)
                if (response.isSuccessful) {
                    _rutina.value = response.body()

                    if(_rutina.value!!.creadorId == FirebaseAuth.getInstance().currentUser!!.uid){
                        _esSuyaOEsAdmin.value = true
                    }else{
                        comprobarAdmin()
                    }
                    _estado.value = true
                }
            } catch (e: Exception) {
                manejarErrorConexion(e)
                _sinInternet.value = true
                mostrarToast(R.string.error_conexion)
            }
        }
    }



    fun hacerRutina() {
        ente.listaEjercicio.value = _rutina.value!!.ejercicios
        ente.rutina.value = _rutina.value!!.id!!
        _estado.value = false
    }

    fun borrarRutina(onResultado: (Boolean) -> Unit) {
        popUpEliminarRutina(false)
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiRutinas.eliminarRutina(_rutina.value!!.id!!)
                if (response.isSuccessful) {
                    mostrarToast(R.string.RutinaEliminada)
                    delay(1000)
                    onResultado(true)
                }else{
                    mostrarToast(R.string.ErrorAlEliminarLaRutina)
                    onResultado(false)
                }
            } catch (e: Exception) {
                manejarErrorConexion(e)
                _sinInternet.value = true
                onResultado(false)
                mostrarToast(R.string.error_conexion)
            }
        }
    }

    fun popUpEliminarRutina(estado: Boolean) {
        _ventanaEliminarRutina.value = estado
    }


}
