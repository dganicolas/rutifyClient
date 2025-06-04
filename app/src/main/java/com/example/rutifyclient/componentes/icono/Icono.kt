package com.example.rutifyclient.componentes.icono

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rutifyclient.apiservice.local.room.database.RutinaDatabase
import com.example.rutifyclient.domain.room.SettingsDtoRoom
import com.example.rutifyclient.viewModel.ajustes.SettingsViewModel
import kotlinx.coroutines.launch

@Composable
fun Icono(imagen: Painter? = null, icono: ImageVector? = null, descripcion: Int, onClick: () -> Unit, modifier: Modifier = Modifier.size(45.dp), tint: Color = colorScheme.onBackground,estado:Boolean = true) {
    val context = LocalContext.current
    val settingsDao = remember {
        RutinaDatabase.obtenerInstancia(context.applicationContext).settingsDao()
    }

    var settings by remember {
        mutableStateOf(
            SettingsDtoRoom(
                backgroundOption = 0,
                fontSizeScale = 1f,
                themeOption = 0
            )
        )
    }

    val isDarkTheme = when (settings.themeOption) {
        1 -> false
        2 -> true
        else -> isSystemInDarkTheme()
    }

    LaunchedEffect(Unit) {
        val loadedSettings = settingsDao.getSettings()
        if (loadedSettings != null) {
            settings = loadedSettings
        }
    }
    if (icono != null) {
        Icon(
            imageVector = icono,
            contentDescription = stringResource(descripcion),
            modifier = modifier.clickable { onClick() },
            tint = tint
        )
    } else if (imagen != null) {
        Image(
            painter = imagen,
            contentDescription = stringResource(descripcion),
            modifier = modifier.clickable { onClick() },
            colorFilter = if (isDarkTheme && estado) ColorFilter.tint(Color.White.copy(alpha = 0.3f)) else null
        )
    }
}