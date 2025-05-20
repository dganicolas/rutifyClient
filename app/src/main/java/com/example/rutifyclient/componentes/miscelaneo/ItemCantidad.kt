package com.example.rutifyclient.componentes.miscelaneo

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.rutifyclient.R
import com.example.rutifyclient.componentes.textos.TextoInformativo
import com.example.rutifyclient.componentes.textos.TextoSubtitulo

@Composable
fun ItemCantidad(
    nombre: String,
    cantidad: Int,
    onCantidadChange: (Int) -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    min: Int = 0,
    max: Int = 99,
    imagen: String
) {

    HorizontalDivider(thickness = 2.dp, color = colorScheme.primary)
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp).clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Image(
            painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imagen)
                    .size(60)
                    .build()
            ),
            contentDescription = "GIF como imagen estática",
            modifier = Modifier
                .width(80.dp)
                .height(80.dp)
        )

        Column(modifier = Modifier.width(120.dp)){
            TextoSubtitulo(R.string.ejerciciosRutinas)
            TextoInformativo(R.string.texto_input,nombre)
        }

        Column{
            TextoInformativo(R.string.cantidad)
            Row (
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    if (cantidad > min) onCantidadChange(cantidad -1)
                }) {
                    Icon(Icons.Default.Remove, contentDescription = "Disminuir")
                }

                TextField(
                    value = cantidad.toString(),
                    onValueChange = {
                        when {
                            it.isEmpty() -> {
                            onCantidadChange(0) // Si el usuario borra todo, poner 0
                        }
                            it.length <= 2 -> {
                            val nuevoValor = it.toIntOrNull()
                            if (nuevoValor != null && nuevoValor in min..max) {
                                onCantidadChange(nuevoValor)
                            }
                        }
                        }
                    },
                    modifier = Modifier.width(50.dp).height(50.dp),
                    textStyle = MaterialTheme.typography.bodyMedium,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                IconButton(onClick = {
                    if (cantidad < max) onCantidadChange(cantidad + 1)
                }) {
                    Icon(Icons.Default.Add, contentDescription = "Aumentar")
                }
            }
        }
    }
}