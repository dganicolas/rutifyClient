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
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.example.rutifyclient.R
import com.example.rutifyclient.domain.room.SettingsDtoRoom
import com.example.rutifyclient.viewModel.ajustes.SettingsViewModel

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
    settingsViewModel: SettingsViewModel,
    content: @Composable () -> Unit
) {
    val settings by settingsViewModel.settings.observeAsState(SettingsDtoRoom(0,0,0f,0))

    val darkTheme = when (settings.themeOption) {
        1 -> {
            false
        }
        2 -> {
            true
        }
        else -> isSystemInDarkTheme()
    }

    val colorScheme = when {
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

    val backgroundResource = when (settings.backgroundOption) {
        1 -> R.drawable.imagen_fondo_oscuro
        2 -> R.drawable.imagen_fondo
        else -> if (darkTheme) R.drawable.imagen_fondo_oscuro else R.drawable.imagen_fondo
    }
    val fontScale = settings.fontSizeScale.coerceIn(0.8f, 1.4f)

    val appTypography = Typography(
        displayLarge = TextStyle(fontSize = (34.sp * fontScale), fontWeight = FontWeight.Bold),
        headlineLarge = TextStyle(fontSize = (28.sp * fontScale), fontWeight = FontWeight.SemiBold),
        titleLarge = TextStyle(fontSize = (22.sp * fontScale), fontWeight = FontWeight.Medium),
        bodyLarge = TextStyle(fontSize = (18.sp * fontScale)),
        bodyMedium = TextStyle(fontSize = (16.sp * fontScale)),
        labelLarge = TextStyle(fontSize = (14.sp * fontScale), fontWeight = FontWeight.Medium)
    )

    MaterialTheme(
        colorScheme = colorScheme,
        typography = appTypography ,
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                // Fondo con la imagen
                    // Imagen por defecto u oscura según tema
                    Image(
                        painter = painterResource(id = backgroundResource),
                        contentDescription = "",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(with(LocalDensity.current) {
                            WindowInsets.statusBars
                                .getTop(LocalDensity.current)
                                .toFloat()
                                .toDp()
                        })
                        .background(colorScheme.background)
                        .align(Alignment.TopCenter)
                )
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(with(LocalDensity.current) {
                            WindowInsets.navigationBars
                                .getBottom(LocalDensity.current)
                                .toFloat()
                                .toDp()
                        })
                        .background(colorScheme.background)
                        .align(Alignment.BottomCenter)
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .windowInsetsPadding(WindowInsets.systemBars),
                    contentAlignment = Alignment.Center
                ) {
                    content()
                }
            }
        }
    )
}
