package com.example.rutifyclient.componentes.tarjetas

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.rutifyclient.R
import com.example.rutifyclient.componentes.textos.TextoInformativo
import com.example.rutifyclient.utils.obtenerEmojiEstado

@Composable
fun CuadroEstadoAnimo(listaEstados: Map<String, String>, estadoAnimo: String) {
    if(estadoAnimo == "") return
    val texto = listaEstados[estadoAnimo] ?: estadoAnimo
    val emoji = obtenerEmojiEstado(estadoAnimo)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(top = 8.dp)
            .border(
                width = 1.dp,
                color = colorScheme.primary,
                shape = RoundedCornerShape(50)
            )
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        TextoInformativo(R.string.texto_input, "$emoji $texto")
    }
}