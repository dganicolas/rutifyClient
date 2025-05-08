package com.example.rutifyclient.componentes.textos

import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun TextoInput(texto: String, style:TextStyle = typography.headlineLarge,color: Color = colorScheme.onBackground,maxLines:Int = Int.MAX_VALUE, overflow: TextOverflow = TextOverflow.Visible) {
    Text(
        text = texto,
        style = style,
        color = color,
        maxLines = maxLines,
        overflow = overflow
    )
}