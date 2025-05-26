package com.example.rutifyclient.viewModel.usuario

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.rutifyclient.R
import com.example.rutifyclient.apiservice.network.RetrofitClient
import com.example.rutifyclient.domain.estadisticas.EstadisticasDto
import com.example.rutifyclient.domain.usuario.UsuarioInformacionDto
import com.example.rutifyclient.viewModel.ViewModelBase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class MiZonaViewModel : ViewModelBase() {

    private val _usuario = MutableLiveData(
        UsuarioInformacionDto(
            "", "", "", "", false, "", EstadisticasDto("", 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0.0), 0
        )
    )
    val usuario: LiveData<UsuarioInformacionDto> = _usuario


    fun obtenerUsuario() {
        _sinInternet.value = false
        _estado.value = false
        viewModelScope.launch {
            try {
                val response =
                    RetrofitClient.apiUsuarios.obtenerDetalleUsuario(FirebaseAuth.getInstance().currentUser!!.uid)

                if (response.isSuccessful) {
                    _usuario.value = response.body()
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

}
