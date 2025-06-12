package com.example.rutifyclient.viewModel.ajustes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.rutifyclient.R
import com.example.rutifyclient.apiservice.local.room.database.RutinaDatabase
import com.example.rutifyclient.apiservice.network.RetrofitClient
import com.example.rutifyclient.domain.room.SettingsDtoRoom
import com.example.rutifyclient.domain.usuario.EliminarUsuarioDTO
import com.example.rutifyclient.viewModel.ViewModelBase
import kotlinx.coroutines.launch

class SettingsViewModel(application: Application) : ViewModelBase() {

    private val settingsDao = RutinaDatabase.obtenerInstancia(application).settingsDao()

    private val _settings = MutableLiveData(
        SettingsDtoRoom(
            backgroundOption = 0,
            fontSizeScale = 1f,
            themeOption = 0
        )
    )
    val settings: LiveData<SettingsDtoRoom> = _settings

    val _fontSizes:  MutableLiveData<List<Float>> =  MutableLiveData(listOf(0.8f, 1f, 1.2f))
    val fontSizes: LiveData<List<Float>> =  _fontSizes

    val _fontLabels:  MutableLiveData<List<Int>> = MutableLiveData(listOf(R.string.pequeno, R.string.normal, R.string.grande))
    val fontLabels: LiveData<List<Int>> = _fontLabels

    val _themeOptions:  MutableLiveData<List<Int>> = MutableLiveData(listOf(R.string.seguirSistema, R.string.claro, R.string.oscuro))
    val themeOptions: LiveData<List<Int>> = _themeOptions

    val _popupVentanaEliminarCUenta:  MutableLiveData<Boolean> = MutableLiveData(false)
    val popupVentanaEliminarCUenta: LiveData<Boolean> = _popupVentanaEliminarCUenta

    init {
        viewModelScope.launch {
            settingsDao.getSettings()?.let {
                _settings.value = it
            }
        }
    }

    fun updateSettings(newSettings: SettingsDtoRoom) {
        _settings.value = newSettings
        viewModelScope.launch {
            settingsDao.insertSettings(newSettings)
        }
    }

    fun mostrarPoupEliminarCuenta() {
        _popupVentanaEliminarCUenta.value = !_popupVentanaEliminarCUenta.value!!
    }

    fun eliminarCuenta(navegarALogin: () -> Unit = {}) {
        _estado.value = false
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiUsuarios.eliminarCuenta(EliminarUsuarioDTO(_usuario.value!!.correo))
                if (response.isSuccessful) {
                    navegarALogin()
                }
            } catch (e: Exception) {
                manejarErrorConexion(e)
                _sinInternet.value = true
                mostrarToast(R.string.error_conexion)
            }
        }
    }
}
