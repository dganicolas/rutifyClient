package com.example.rutifyclient.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.example.rutifyclient.R

private val DarkColorScheme = darkColorScheme(
    primary = RutifyPrimaryDark,
    onPrimary = RutifyOnPrimaryDark,
    secondary = RutifySecondaryDark,
    onSecondary = RutifyOnSecondaryDark,
    background = RutifyBackgroundDark,
    onBackground = RutifyOnBackgroundDark,
    surface = RutifySurfaceDark,
    onSurface = RutifyOnSurfaceDark
)

private val LightColorScheme = lightColorScheme(
    primary = RutifyPrimaryLight,
    onPrimary = RutifyOnPrimaryLight,
    secondary = RutifySecondaryLight,
    onSecondary = RutifyOnSecondaryLight,
    background = RutifyBackgroundLight,
    onBackground = RutifyOnBackgroundLight,
    surface = RutifySurfaceLight,
    onSurface = RutifyOnSurfaceLight
)

@Composable
fun RutifyClientTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val backgroundResource = if (darkTheme) {
        R.drawable.imagen_fondo_oscuro
    } else {
        R.drawable.imagen_fondo
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                // Fondo con la imagen
                Image(
                    painter = painterResource(id = backgroundResource),
                    contentDescription = "",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Contenido de la app encima del fondo
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    content()
                }
            }
        }
    )
}