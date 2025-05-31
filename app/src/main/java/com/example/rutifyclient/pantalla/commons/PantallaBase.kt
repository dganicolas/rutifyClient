package com.example.rutifyclient.pantalla.commons

import android.widget.Toast
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import com.example.rutifyclient.R
import com.example.rutifyclient.viewModel.ViewModelBase

@Composable
fun PantallaBase(
    viewModel: ViewModelBase,
    cargando: Boolean,
    sinInternet: Boolean,
    onReintentar: () -> Unit,
    onClickFloatingButton: () -> Unit = {},
    topBar: @Composable (() -> Unit)? = null,
    bottomBar: @Composable (() -> Unit)? = null,
    iconoFloatingButton: ImageVector? = null,
    contenido: @Composable (PaddingValues) -> Unit,
) {
    val mensajeToast by viewModel.mensajeToast.observeAsState(R.string.dato_defecto)
    val toastMostrado by viewModel.toastMostrado.observeAsState(true)
    val toastMostradoApi by viewModel.mensajeToastApiMostrado.observeAsState(true)
    val mensajeToastApi by viewModel.mensajeToastApi.observeAsState("")
    val context = LocalContext.current
    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            topBar?.invoke()
        },
        bottomBar = {
            bottomBar?.invoke()
        },
        floatingActionButton = {
            if (iconoFloatingButton != null) {
                FloatingActionButton(
                    onClick = onClickFloatingButton,
                    containerColor = colorScheme.primary,
                    contentColor = colorScheme.onPrimary
                ) {
                    Icon(imageVector = iconoFloatingButton, contentDescription = "Crear rutina")
                }
            }
        },
    ) { innerPadding ->
        LaunchedEffect(mensajeToast) {
            if (!toastMostrado) {
                Toast.makeText(context, mensajeToast, Toast.LENGTH_LONG).show()
                viewModel.toastMostrado()
            }
        }
        LaunchedEffect(mensajeToastApi) {
            if (!toastMostradoApi) {
                Toast.makeText(context, mensajeToastApi, Toast.LENGTH_LONG).show()
                viewModel.toastMostrado()
            }
        }
        if (sinInternet) {
            SinConexionPantalla(onReintentar)
            return@Scaffold;
        }
        if (cargando) {
            Cargando()
        }
        contenido(innerPadding)
    }
}