package com.example.rutifyclient.pantalla.miZona

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Store
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
import com.example.rutifyclient.componentes.avatar.cuadroAvatar
import com.example.rutifyclient.componentes.barras.NavigationBarAbajoPrincipal
import com.example.rutifyclient.componentes.barras.TopBarBase
import com.example.rutifyclient.componentes.graficos.PesoGraph
import com.example.rutifyclient.componentes.icono.Icono
import com.example.rutifyclient.componentes.tarjetas.TarjetaNormal
import com.example.rutifyclient.componentes.textos.TextoInformativo
import com.example.rutifyclient.componentes.textos.TextoSubtitulo
import com.example.rutifyclient.domain.estadisticas.EstadisticasDto
import com.example.rutifyclient.domain.usuario.UsuarioInformacionDto
import com.example.rutifyclient.navigation.Rutas
import com.example.rutifyclient.pantalla.commons.PantallaBase
import com.example.rutifyclient.viewModel.usuario.MiZonaViewModel

@Composable
fun MiZona(navControlador: NavHostController) {
    val viewModel: MiZonaViewModel = viewModel()
    val usuario by viewModel.usuario.observeAsState(
        UsuarioInformacionDto(
            "", "", "", "", false, "", EstadisticasDto("", 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0.0), 0
        )
    )
    val sinInternet by viewModel.toastMostrado.observeAsState(false)
    val estado by viewModel.estado.observeAsState(true)
    LaunchedEffect(Unit) {
        viewModel.obtenerUsuario()
    }
    PantallaBase(
        viewModel = viewModel,
        cargando = !estado,
        sinInternet = sinInternet,
        onReintentar = {
            viewModel.obtenerUsuario()
        },
        topBar = ({ TopBarBase(R.string.usuario, args = arrayOf(usuario.nombre), acciones = ({
            Icono(
                descripcion = R.string.tienda,
                icono = Icons.Filled.Store,
                onClick = {})
        })) }),
        bottomBar = ({ NavigationBarAbajoPrincipal(navControlador, Rutas.MiZona) })
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(5.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            cuadroAvatar(usuario)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TarjetaNormal(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth()
                        .clickable { },
                    modifierTarjeta = Modifier
                        .weight(1f)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icono(
                            descripcion = R.string.descKcal,
                            icono = Icons.Filled.LocalFireDepartment,
                            modifier = Modifier.size(85.dp),
                            onClick = {})
                        TextoInformativo(R.string.borrarTextoPRuebaKcal)
                        TextoSubtitulo(R.string.kcal)
                    }
                }
                TarjetaNormal(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth()
                        .clickable { },
                    modifierTarjeta = Modifier
                        .weight(1f)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icono(
                            descripcion = R.string.descKcal,
                            icono = Icons.Filled.EditNote, modifier = Modifier.size(85.dp),
                            onClick = {})
                        TextoInformativo(R.string.borrarTextoPRuebaRutinas)
                        TextoSubtitulo(R.string.rutinasHechas)
                    }
                }
                TarjetaNormal(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth()
                        .clickable { },
                    modifierTarjeta = Modifier
                        .weight(1f)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icono(
                            descripcion = R.string.descKcal,
                            icono = Icons.Filled.AccessTime,
                            onClick = {},
                            modifier = Modifier.size(85.dp)
                        )
                        TextoInformativo(R.string.borrarTextoPRuebaRutinas)
                        TextoSubtitulo(R.string.minutosActivos)
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.5f),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    TarjetaNormal(
                        modifier = Modifier
                            .padding(5.dp)
                            .fillMaxWidth()
                            .clickable { },
                        modifierTarjeta = Modifier
                            .weight(1f)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(5.dp)
                        ) {
                            TextoSubtitulo(R.string.pesoUsuario, 124.43f)
                            PesoGraph(pesos = listOf(72f, 71.5f, 71f, 70.8f, 70.3f))
                        }
                    }
                    TarjetaNormal(
                        modifier = Modifier
                            .padding(5.dp)
                            .fillMaxWidth()
                            .clickable { }
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icono(
                                descripcion = R.string.descKcal,
                                icono = Icons.Filled.EmojiEvents,
                                onClick = {},
                                modifier = Modifier.size(85.dp)
                            )
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                TextoInformativo(R.string.tiempoRestante)
                                TextoSubtitulo(R.string.borrarPerubaRetodiario)
                            }
                        }
                    }
                }
                TarjetaNormal(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth()
                        .weight(1f)
                        .clickable { },
                    modifierTarjeta = Modifier
                        .weight(1f)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TextoSubtitulo(R.string.misRutinas)
                    }
                }
            }
        }
    }
}