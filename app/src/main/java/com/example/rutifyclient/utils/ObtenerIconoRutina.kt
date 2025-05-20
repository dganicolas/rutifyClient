package com.example.rutifyclient.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.example.rutifyclient.R

@Composable
fun obtenerIconoRutina(nombreIcono: String): Painter {
    return when (nombreIcono.lowercase()) {
        "r1" -> painterResource(id = R.drawable.r1)
        "r2" -> painterResource(id = R.drawable.r2)
        "r3" -> painterResource(id = R.drawable.r3)
        "r4" -> painterResource(id = R.drawable.r4)
        "r5" -> painterResource(id = R.drawable.r5)
        "r6" -> painterResource(id = R.drawable.r6)
        "r7" -> painterResource(id = R.drawable.r7)
        "r8" -> painterResource(id = R.drawable.r8)
        "r9" -> painterResource(id = R.drawable.r9)
        "r10" -> painterResource(id = R.drawable.r10)
        "r11" -> painterResource(id = R.drawable.r11)
        "r12" -> painterResource(id = R.drawable.r12)
        else -> painterResource(id = R.drawable.r1)
    }
}