package com.example.rutifyclient.componentes.acordeon

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.example.rutifyclient.R
import com.example.rutifyclient.componentes.espaciadores.SpacerVertical
import com.example.rutifyclient.componentes.icono.Icono
import com.example.rutifyclient.componentes.textos.TextoTitulo
import com.example.rutifyclient.domain.ejercicio.EjercicioDto

@Composable
fun AcordeonGrupo(grupo: String,
                  ejercicios: List<EjercicioDto>,
                  clickDetalleEjercicios: (EjercicioDto) -> Unit) {
    var expandido by remember { mutableStateOf(false) }
    val rotacion by animateFloatAsState(if (expandido) 180f else 0f, label = "")

    Surface(
        color = colorScheme.background,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, colorScheme.surface),
        modifier = Modifier.padding(8.dp)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .clickable { expandido = !expandido }
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextoTitulo(R.string.texto_input, grupo)
                Spacer(modifier = Modifier.weight(1f))
                Icono(
                    onClick = { expandido = !expandido },
                    icono = Icons.Outlined.ArrowDropDown,
                    descripcion = R.string.expandirContraer,
                    modifier = Modifier
                        .size(45.dp)
                        .rotate(rotacion),
                    tint = colorScheme.onSurface
                )
            }

            AnimatedVisibility(visible = expandido) {
                Column(modifier = Modifier.padding(6.dp)) {
                    ejercicios.forEach { ejercicio ->
                        HorizontalDivider(thickness = 3.dp, color = colorScheme.onBackground)
                        SpacerVertical(10.dp)
                        Row(
                            modifier = Modifier.clickable { clickDetalleEjercicios(ejercicio) },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(ejercicio.imagen)
                                        .build(),
                                    imageLoader = ImageLoader.Builder(LocalContext.current)
                                        .components {
                                            add(ImageDecoderDecoder.Factory())
                                        }
                                        .build()
                                ),
                                contentDescription = "GIF ",
                                modifier = Modifier
                                    .width(80.dp)
                                    .height(80.dp)
                            )
                            TextoTitulo(R.string.texto_input, ejercicio.nombreEjercicio)
                        }
                        SpacerVertical(10.dp)
                    }
                }
            }
        }
    }
}