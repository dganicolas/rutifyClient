package com.example.rutifyclient.pantalla.rutinas.hacerRutina

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.rutifyclient.R
import com.example.rutifyclient.componentes.botones.ButtonPrincipal
import com.example.rutifyclient.componentes.tarjetas.RutinasCard
import com.example.rutifyclient.componentes.textos.TextoInformativo
import com.example.rutifyclient.componentes.textos.TextoSubtitulo
import com.example.rutifyclient.componentes.textos.TextoTitulo
import com.example.rutifyclient.domain.estadisticas.EstadisticasDto

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun pantallaFinal(
    estado:Boolean,
    estadisticas: EstadisticasDto,
    estadisticasDtoCalculadas: EstadisticasDto,
    guardarProgreso: () -> Unit,
    mostrarVentanaPuntuarRutina: () -> Unit,
) {
    val items = listOf(
        Triple(R.string.PuntuacionBrazo, estadisticas.lvlBrazo, estadisticasDtoCalculadas.lvlBrazo),
        Triple(R.string.puntuacionPecho, estadisticas.lvlPecho, estadisticasDtoCalculadas.lvlPecho),
        Triple(
            R.string.PuntuacionEspalda,
            estadisticas.lvlEspalda,
            estadisticasDtoCalculadas.lvlEspalda
        ),
        Triple(
            R.string.puntuacionAbdominal,
            estadisticas.lvlAbdominal,
            estadisticasDtoCalculadas.lvlAbdominal
        ),
        Triple(
            R.string.puntuacionPiernas,
            estadisticas.lvlPiernas,
            estadisticasDtoCalculadas.lvlPiernas
        ),
        Triple(
            R.string.puntuacionEjercicios,
            estadisticas.ejerciciosRealizados,
            estadisticasDtoCalculadas.ejerciciosRealizados
        ),
        Triple(
            R.string.puntuacionQuemadas,
            estadisticas.kCaloriasQuemadas,
            estadisticasDtoCalculadas.kCaloriasQuemadas
        )
    )
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        RutinasCard(
            modifier = Modifier
                .padding(16.dp)
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
                    Spacer(modifier = Modifier.weight(1f))
                    TextoTitulo(R.string.rutinaFinalizada)
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
            item {
                TextoInformativo(R.string.mensajeMotivador)
            }
            items(items) { (stringId, antes, ganado) ->
                Row(modifier = Modifier.padding(vertical = 4.dp)) {
                    TextoSubtitulo(stringId, antes, ganado)
                }
            }
            item {
                ButtonPrincipal(R.string.puntuarRutina, { mostrarVentanaPuntuarRutina() },enabled = estado)
            }

            item {
                ButtonPrincipal(R.string.BRutinaFinalizar, { guardarProgreso() }, enabled = estado)
            }
        }
    }
}