package com.example.rutifyclient.pantalla.zonaAdmin

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.rutifyclient.R
import com.example.rutifyclient.componentes.barras.TopBarBase
import com.example.rutifyclient.componentes.espaciadores.SpacerVertical
import com.example.rutifyclient.componentes.icono.Icono
import com.example.rutifyclient.componentes.tarjetas.TarjetaBuscadorPersonas
import com.example.rutifyclient.componentes.tarjetas.TarjetaNormal
import com.example.rutifyclient.componentes.textos.TextoSubtitulo
import com.example.rutifyclient.pantalla.commons.PantallaBase
import com.example.rutifyclient.viewModel.ZonaAdminViewModel

@Composable
fun AdministrarUsuarios(navControlador: NavHostController) {
    val viewModel: ZonaAdminViewModel = viewModel()
    val sinInternet by viewModel.sinInternet.observeAsState(false)
    val usuarios by viewModel.usuarios.observeAsState(emptyList())
    val estado by viewModel.estado.observeAsState(true)
    val esSuyaOEsAdmin by viewModel.esSuyaOEsAdmin.observeAsState(true)

    LaunchedEffect(Unit) {
        viewModel.cargarUsuariosConReportes()
        viewModel.comprobarAdmin()
    }
    PantallaBase(
        navControlador,
        viewModel = viewModel,
        cargando = !estado,
        sinInternet = sinInternet,
        onReintentar = {
            viewModel.obtenerUsuario()
        },
        topBar = ({
            TopBarBase(R.string.administrarUsuarios, navigationIcon = ({
                Icono(
                    icono = Icons.AutoMirrored.Filled.ArrowBack,
                    descripcion = R.string.volver,
                    onClick = { navControlador.popBackStack() },
                    tint = colorScheme.onBackground
                )
            }))
        }),
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .padding(5.dp)
        ) {
            TarjetaNormal(modifier = Modifier.fillMaxSize()) {
                LazyColumn {
                    item {
                        if (usuarios.isEmpty()) {
                            TextoSubtitulo(R.string.noHayUsuariosNuevos)
                        }
                    }
                    items(usuarios, key = { comentario -> comentario.idFirebase }) { usuario ->
                        TarjetaBuscadorPersonas(
                            usuario = usuario,
                            navController = navControlador,
                            esAdmin = esSuyaOEsAdmin
                        ) { viewModel.eliminarUsuario(it) }
                        SpacerVertical(10.dp)
                    }
                }
            }
        }
    }
}