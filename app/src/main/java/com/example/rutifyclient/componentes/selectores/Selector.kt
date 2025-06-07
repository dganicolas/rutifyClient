package com.example.rutifyclient.componentes.selectores

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.rutifyclient.componentes.textos.TextoInformativo


@Composable
fun SelectorSexo(
    opciones: List<Int>,
    opcionSeleccionada: Int,
    onSeleccionar: (Int) -> Unit,
    textoId: Int,
) {

    OutlinedCard(
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
        ) {
            TextoInformativo(
                textoId = textoId
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),

            ) {

            opciones.forEach {
                Row(
                    modifier = Modifier
                        .testTag("campo_selector")
                        .clickable { onSeleccionar(it) },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = it == opcionSeleccionada,
                        onClick = { onSeleccionar(it) }
                    )
                    TextoInformativo(
                        textoId = it
                    )
                }
            }
        }
    }
}
