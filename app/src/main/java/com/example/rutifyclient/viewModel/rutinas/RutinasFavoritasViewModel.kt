package com.example.rutifyclient.viewModel.rutinas

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.rutifyclient.R
import com.example.rutifyclient.apiservice.local.room.database.RutinaDatabase
import com.example.rutifyclient.apiservice.network.RetrofitClient
import com.example.rutifyclient.domain.comentario.ComentarioDto
import com.example.rutifyclient.domain.rutinas.RutinaBuscadorDto
import com.example.rutifyclient.domain.rutinas.RutinaDTO
import com.example.rutifyclient.domain.voto.VotodDto
import com.example.rutifyclient.viewModel.ViewModelBase
import kotlinx.coroutines.launch
import java.time.LocalDate

class RutinasFavoritasViewModel: ViewModelBase() {
    private val _idFirebaseParam =  MutableLiveData<String?>("")
    val idFirebaseParam: LiveData<String?> = _idFirebaseParam
    private val _rutinasFavoritas = MutableLiveData<List<RutinaDTO>>(emptyList())
    val rutinasFavoritas: LiveData<List<RutinaDTO>> = _rutinasFavoritas

    private val _pestanaActiva = MutableLiveData<Int>(0)
    val pestanaActiva: LiveData<Int> = _pestanaActiva

    private val _rutinasCreadas = MutableLiveData<List<RutinaBuscadorDto>>(emptyList())
    val rutinasCreadas: LiveData<List<RutinaBuscadorDto>> = _rutinasCreadas

    private val _votoAEliminar = MutableLiveData<VotodDto>(VotodDto("","","","",0.0f))

    private val _popupEliminarVoto = MutableLiveData<Boolean>(false)
    val popupEliminarVoto: LiveData<Boolean> = _popupEliminarVoto

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
                val creadas = RetrofitClient.apiRutinas.obtenerRutinasPorAutor(_idFirebaseParam.value ?: _idFirebase.value!! )
                _rutinasCreadas.value = creadas.body()
            } catch (e: Exception) {
                _rutinasCreadas.value = emptyList()
            } finally {
                _estado.value = true
            }
        }
    }

    fun guardarIdFirebase(idFirebase: String?) {
        _idFirebaseParam.value = if(idFirebase.isNullOrEmpty()) _idFirebase.value else idFirebase
    }

    fun setPestanaActiva(ventana: Int) {
        _pestanaActiva.value = ventana
    }

    private val _comentariosAutor = MutableLiveData<List<ComentarioDto>>(emptyList())
    val comentariosAutor: LiveData<List<ComentarioDto>> = _comentariosAutor

    fun obtenerComentariosAutor() {
        viewModelScope.launch {
            _estado.value = false
            try {
                val creadas = RetrofitClient.apiComentarios.obtenerComentariosPorAutor(_idFirebaseParam.value ?: _idFirebase.value!! )
                _comentariosAutor.value = creadas.body()
            } catch (e: Exception) {
                _comentariosAutor.value = emptyList()
            } finally {
                _estado.value = true
            }
        }
    }

    private val _votosAutor = MutableLiveData<List<VotodDto>>(emptyList())

    val votosAutor: LiveData<List<VotodDto>> = _votosAutor

    fun obtenerVotosAutor() {
        viewModelScope.launch {
            _estado.value = false
            try {
                val response = RetrofitClient.apiVotos.obtenerRutinasPorAutor(_idFirebaseParam.value ?: _idFirebase.value!! )
                _votosAutor.value = response.body() ?: emptyList()
            } catch (e: Exception) {
                _votosAutor.value = emptyList()
            } finally {
                _estado.value = true
            }
        }
    }

    private val _comentarioAEliminar = MutableLiveData<ComentarioDto>(
        ComentarioDto("","","","",
            LocalDate.now(),"","",""))
    fun guardarComentarioAEliminar(comentario: ComentarioDto) {
        _comentarioAEliminar.value = comentario
    }

    private val _mostrarVentanaEliminarComentario = MutableLiveData(false)
    val mostrarVentanaEliminarComentario: LiveData<Boolean> = _mostrarVentanaEliminarComentario
    fun mostrarventanaEliminar() {
        _mostrarVentanaEliminarComentario.value = !_mostrarVentanaEliminarComentario.value!!
    }

    fun actualizarPuntuacion(votoEntrante: VotodDto, nuevaPuntuacion: Float) {
        val nuevoVoto = votoEntrante.copy(puntuacion = nuevaPuntuacion)
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiVotos.actualizarVoto(
                    nuevoVoto
                )
                if (response.isSuccessful && response.body() != null) {
                    _votosAutor.value = _votosAutor.value?.map {
                        if (it.id == nuevoVoto.id) nuevoVoto else it
                    }
                    mostrarToast(R.string.votoGuardado)
                }

            } catch (e: Exception) {
                manejarErrorConexion(e)
                _sinInternet.value = true
                mostrarToast(R.string.error_conexion)
            }finally {
                _estado.value = true
            }
        }
    }

    fun popUpEliminarVoto() {
        _popupEliminarVoto.value = !_popupEliminarVoto.value!!
    }

    fun guardarVotoAEliminar(voto: VotodDto) {
        _votoAEliminar.value = voto
    }

    fun borrarVoto() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiVotos.eliminarVoto(_votoAEliminar.value!!.id!!)
                if (response.isSuccessful) {
                    _votosAutor.value = _votosAutor.value?.filterNot { it.id == _votoAEliminar.value!!.id!! }?.toList()
                    mostrarToast(R.string.votoEliminado)
                }

            } catch (e: Exception) {
                manejarErrorConexion(e)
                _sinInternet.value = true
                mostrarToast(R.string.error_conexion)
            }finally {
                _estado.value = true
            }
        }
    }

    fun borrarComentario() {
        viewModelScope.launch {
            try {
                val response =
                    RetrofitClient.apiComentarios.eliminarComentario(_comentarioAEliminar.value!!._id!!)
                if (response.isSuccessful) {
                    val listaActual = _comentariosAutor.value?.toMutableList() ?: mutableListOf()
                    listaActual.removeAll { it._id == _comentarioAEliminar.value!!._id }
                    _comentariosAutor.postValue(listaActual)
                    mostrarToast(R.string.comentarioEliminado)
                }
            } catch (e: Exception) {
                manejarErrorConexion(e)
                _sinInternet.value = true
            }
        }
    }
}
