package com.example.rutifyclient.componentes.ventanas

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.rutifyclient.R
import com.example.rutifyclient.componentes.acordeon.AcordeonGrupo
import com.example.rutifyclient.componentes.icono.Icono
import com.example.rutifyclient.componentes.tarjetas.RutinasCard
import com.example.rutifyclient.componentes.textos.TextoTitulo
import com.example.rutifyclient.domain.ejercicio.EjercicioDto

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun mostrarEjercicios(
    ejerciciosPorGrupo: Map<String, List<EjercicioDto>>,
    clickDetalleEjercicios: (EjercicioDto) -> Unit,
    cerrarVentana: () -> Unit
) {
    ventanaModal {
        Box {
            RutinasCard(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
                    .align(Alignment.TopStart)
            ) {
                stickyHeader {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.TopStart)
                            .background(colorScheme.surface),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icono(icono = Icons.Filled.Close, descripcion = R.string.cerrarVentana, onClick = { cerrarVentana() })
                        Spacer(modifier = Modifier.weight(1f))
                        TextoTitulo(R.string.anadirEjercicio)
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
                ejerciciosPorGrupo.forEach { (grupo, ejercicios) ->
                    item {
                        AcordeonGrupo(grupo, ejercicios, clickDetalleEjercicios)
                    }
                }
            }
        }
    }
}