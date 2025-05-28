package com.example.rutifyclient.viewModel.rutinas

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.rutifyclient.apiservice.local.room.database.RutinaDatabase
import com.example.rutifyclient.apiservice.network.RetrofitClient
import com.example.rutifyclient.domain.rutinas.RutinaDTO
import com.example.rutifyclient.viewModel.ViewModelBase
import kotlinx.coroutines.launch

class RutinasFavoritasViewModel: ViewModelBase() {
    private val _rutinas = MutableLiveData<List<RutinaDTO>>(emptyList())
    val rutinas: LiveData<List<RutinaDTO>> = _rutinas

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
                _rutinas.value = rutinasValidas
                _estado.value = true
            } catch (e: Exception) {
                manejarErrorConexion(e)
                _rutinas.value = emptyList()
            }finally {
                _estado.value = true
            }
        }
    }
}
