package com.example.rutifyclient.componentes.graficos

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PesoGraph(pesos: List<Double>) {
    val maxPeso = pesos.maxOrNull() ?: 0.0
    val minPeso = pesos.minOrNull() ?: 0.0
    val range = (maxPeso - minPeso).takeIf { it != 0.0 } ?: 1.0
    val colorLinea = colorScheme.primary
    val colorPuntos = colorScheme.secondary
    val limite = colorScheme.onSurface
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
    ) {
        val spacing = size.width / (pesos.size - 1).coerceAtLeast(1)
        val heightFactor = size.height / range

        drawLine(
            color = limite,
            start = Offset(0f, size.height),
            end = Offset(size.width, size.height),
            strokeWidth = 2f
        )

        drawLine(
            color = limite,
            start = Offset(0f, 0f),
            end = Offset(0f, size.height),
            strokeWidth = 2f
        )

        drawLine(
            color = limite,
            start = Offset(size.width, 0f),
            end = Offset(size.width, size.height),
            strokeWidth = 2f
        )

        pesos.forEachIndexed { index, peso ->
            val x = index * spacing
            val y = size.height - (peso - minPeso) * heightFactor

            // Dibuja líneas entre los puntos
            if (index > 0) {
                val prevX = (index - 1) * spacing
                val prevY = size.height - (pesos[index - 1] - minPeso) * heightFactor
                drawLine(
                    color = colorLinea,
                    start = Offset(prevX, prevY.toFloat()),
                    end = Offset(x, y.toFloat()),
                    strokeWidth = 4f
                )
            }

            drawCircle(
                color = colorPuntos,
                radius = 6f,
                center = Offset(x, y.toFloat())
            )
        }
    }
}