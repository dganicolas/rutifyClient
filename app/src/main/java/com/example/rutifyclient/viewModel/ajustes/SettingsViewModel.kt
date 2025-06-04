package com.example.rutifyclient.viewModel.ajustes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.rutifyclient.apiservice.local.room.database.RutinaDatabase
import com.example.rutifyclient.domain.room.SettingsDtoRoom
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
}
