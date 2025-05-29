package com.example.rutifyclient.viewModel.rutinas

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.rutifyclient.apiservice.local.room.database.RutinaDatabase
import com.example.rutifyclient.apiservice.network.RetrofitClient
import com.example.rutifyclient.domain.rutinas.RutinaBuscadorDto
import com.example.rutifyclient.domain.rutinas.RutinaDTO
import com.example.rutifyclient.viewModel.ViewModelBase
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch

class RutinasFavoritasViewModel: ViewModelBase() {
    private val _idFirebase  = MutableLiveData<String?>(null)
    private val _rutinasFavoritas = MutableLiveData<List<RutinaDTO>>(emptyList())
    val rutinasFavoritas: LiveData<List<RutinaDTO>> = _rutinasFavoritas

    private val _pestanaActiva = MutableLiveData<Int>(0)
    val pestanaActiva: LiveData<Int> = _pestanaActiva

    private val _rutinasCreadas = MutableLiveData<List<RutinaBuscadorDto>>(emptyList())
    val rutinasCreadas: LiveData<List<RutinaBuscadorDto>> = _rutinasCreadas

    fun obtenerRutinasFavoritas(context: Context) {
        val db = RutinaDatabase.obtenerInstancia(context)
        val rutinaDao = db.rutinaDao()

        viewModelScope.launch {
            _sinInternet.value = false
            _estado.value = false
            try {
                val rutinasLocal = rutinaDao.obtenerTodas()
                val rutinasValidas = mutableListOf<RutinaDTO>()

                for (rutinaLocal in rutinasLocal) {
                    try {
                        val response = RetrofitClient.apiRutinas.obtenerRutina(rutinaLocal.id)
                        if (response.isSuccessful) {
                            response.body()?.let { rutinaApi ->
                                rutinasValidas.add(rutinaApi)
                            }
                        } else if (response.code() == 404) {
                            rutinaDao.eliminar(rutinaLocal)
                        }
                    } catch (e: Exception) {
                        manejarErrorConexion(e)
                        _sinInternet.value = true
                    }
                }
                _rutinasFavoritas.value = rutinasValidas
                _estado.value = true
            } catch (e: Exception) {
                manejarErrorConexion(e)
                _rutinasFavoritas.value = emptyList()
            }finally {
                _estado.value = true
            }
        }
    }
    fun obtenerRutinasCreadas() {

        viewModelScope.launch {
            _estado.value = false
            try {
                val creadas = RetrofitClient.apiRutinas.obtenerRutinasPorAutor(_idFirebase.value ?: Firebase.auth.currentUser?.uid!!)
                _rutinasCreadas.value = creadas.body()
            } catch (e: Exception) {
                _rutinasCreadas.value = emptyList()
            } finally {
                _estado.value = true
            }
        }
    }

    fun guardarIdFirebase(idFirebase: String?) {
        _idFirebase.value = idFirebase
    }

    fun setPestanaActiva(ventana: Int) {
        _pestanaActiva.value = ventana
    }
}
