package com.example.rutifyclient.pantalla.Ajustes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.rutifyclient.R
import com.example.rutifyclient.componentes.barras.NavigationBarAbajoPrincipal
import com.example.rutifyclient.componentes.botones.ButtonAlerta
import com.example.rutifyclient.componentes.botones.ButtonPrincipal
import com.example.rutifyclient.componentes.tarjetas.TarjetaNormal
import com.example.rutifyclient.componentes.textos.TextoSubtitulo
import com.example.rutifyclient.domain.room.SettingsDtoRoom
import com.example.rutifyclient.navigation.Rutas
import com.example.rutifyclient.pantalla.commons.PantallaBase
import com.example.rutifyclient.viewModel.ajustes.SettingsViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun Ajustes(navControlador: NavHostController, viewModel: SettingsViewModel) {
    val settings by viewModel.settings.observeAsState(SettingsDtoRoom(0, 0, 0.0f, 0))
    val esAdmin by viewModel.esSuyaOEsAdmin.observeAsState(false)
    LaunchedEffect(Unit) {
        viewModel.obtenerUsuario()
        viewModel.comprobarAdmin()
    }
    PantallaBase(
        viewModel = viewModel(),
        cargando = false,
        sinInternet = false,
        onReintentar = {},
        bottomBar = ({ NavigationBarAbajoPrincipal(navControlador, Rutas.Ajustes) })
    ) {
        Box(Modifier.padding(it)) {
            TarjetaNormal(Modifier.fillMaxSize()) {
                Column {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Tamaño de fuente
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text("Tamaño de fuente", style = typography.titleLarge)
                            Spacer(Modifier.height(8.dp))

                            val fontSizes = listOf(0.8f, 1f, 1.2f)
                            val fontLabels = listOf("Pequeña", "Normal", "Grande")

                            fontLabels.forEachIndexed { index, label ->
                                Row(
                                    Modifier
                                        .fillMaxWidth()
                                        .selectable(
                                            selected = (settings.fontSizeScale == fontSizes[index]),
                                            onClick = {
                                                viewModel.updateSettings(
                                                    settings.copy(fontSizeScale = fontSizes[index])
                                                )
                                            }
                                        )
                                        .padding(vertical = 4.dp)
                                ) {
                                    RadioButton(
                                        selected = (settings.fontSizeScale == fontSizes[index]),
                                        onClick = null
                                    )
                                    Spacer(Modifier.width(8.dp))
                                    Text(label)
                                }
                            }

                            Spacer(Modifier.height(16.dp))

                            // Tema
                            Text("Tema", style = typography.titleLarge)
                            Spacer(Modifier.height(8.dp))

                            val themeOptions = listOf("Seguir sistema", "Claro", "Oscuro")

                            themeOptions.forEachIndexed { index, label ->
                                Row(
                                    Modifier
                                        .fillMaxWidth()
                                        .selectable(
                                            selected = (settings.themeOption == index),
                                            onClick = {
                                                viewModel.updateSettings(
                                                    settings.copy(themeOption = index)
                                                )
                                            }
                                        )
                                        .padding(vertical = 4.dp)
                                ) {
                                    RadioButton(
                                        selected = (settings.themeOption == index),
                                        onClick = null
                                    )
                                    Spacer(Modifier.width(8.dp))
                                    Text(label)
                                }
                            }
                            ButtonPrincipal(
                                textoId = R.string.cerrar_sesion,
                                onClick = {
                                    FirebaseAuth.getInstance().signOut()
                                    navControlador.navigate(Rutas.Login) {
                                        popUpTo(Rutas.Ajustes) { inclusive = true }
                                    }
                                },
                                modifier = Modifier
                                    .height(60.dp)
                                    .fillMaxWidth()
                            )

                            ButtonAlerta(
                                textoId = R.string.eliminar_cuenta,
                                onClick = {

                                },
                                modifier = Modifier
                                    .height(60.dp)
                                    .fillMaxWidth()
                            )
                        }
                        if (esAdmin) {
                            TextoSubtitulo(R.string.zonaAdmin)
                            ButtonPrincipal(
                                R.string.administrarComentarios,
                                onClick = {
                                    navControlador.navigate(Rutas.zonaAdmin)
                                },
                                modifier = Modifier
                                    .height(60.dp)
                                    .fillMaxWidth()
                            )

                        }
                    }

                }
            }
        }
    }
}