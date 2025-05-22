package com.example.rutifyclient.componentes.deslizables

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.rutifyclient.R
import com.example.rutifyclient.componentes.icono.IconoPuntuacion


@Composable
fun RatingBar(
    rating: Float,
    modifier: Modifier = Modifier,
    onRatingChanged: (Float) -> Unit
) {
    val starCount = 5

    Row(modifier) {
        for (i in 1..starCount) {
            IconoPuntuacion(icono = Icons.Filled.Star, R.string.estrella,{onRatingChanged(i.toFloat())},tint=if(i <= rating.toInt()) colorScheme.secondary else  colorScheme.onSecondary)
        }
    }
}
