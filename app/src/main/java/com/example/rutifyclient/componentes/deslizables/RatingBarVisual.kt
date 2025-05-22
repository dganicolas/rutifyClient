package com.example.rutifyclient.componentes.deslizables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.rutifyclient.R
import com.example.rutifyclient.componentes.icono.IconoPuntuacion

@Composable
fun RatingBarVisual(
    rating: Float,
    modifier: Modifier = Modifier,
    starSize: Dp = 20.dp,
) {
    val starCount = 5
    Row(modifier) {
        for (i in 1..starCount) {
            IconoPuntuacion(
                icono = Icons.Filled.Star,
                R.string.estrella,
                modifier = Modifier.size(starSize),
                onClick = { },
                tint = if (i <= rating.toInt()) colorScheme.secondary else colorScheme.onSecondary
            )

        }
    }
}