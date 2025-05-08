package com.example.rutifyclient.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.core.view.WindowCompat
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

    val view = LocalView.current
    SideEffect {
        val window = (view.context as Activity).window
        WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
        }
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
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(with(LocalDensity.current) { WindowInsets.statusBars.getTop(LocalDensity.current).toFloat().toDp() })
                        .background(colorScheme.background)
                        .align(Alignment.TopCenter)
                )
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(with(LocalDensity.current) { WindowInsets.navigationBars.getBottom(LocalDensity.current).toFloat().toDp() })
                        .background(colorScheme.background)
                        .align(Alignment.BottomCenter)
                )
                Box(
                    modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.systemBars),
                    contentAlignment = Alignment.Center
                ) {
                    content()
                }
            }
        }
    )
}
