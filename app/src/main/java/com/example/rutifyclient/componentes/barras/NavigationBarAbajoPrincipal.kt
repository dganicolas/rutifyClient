package com.example.rutifyclient.componentes.barras

import androidx.compose.foundation.layout.height
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.rutifyclient.domain.botones.BottonNavegacion


@Composable
fun NavigationBarAbajoPrincipal(navController: NavController, selectedRoute: String) {
    val items = listOf(
        BottonNavegacion.Rutina,
        BottonNavegacion.MiZona,
        BottonNavegacion.Comunidad,
        BottonNavegacion.Ajustes
    )
    NavigationBar(
        containerColor = Color.Transparent,
        contentColor = colorScheme.onSurface,
        modifier = Modifier.height(56.dp)
    ) {
        items.forEach { item ->
            NavigationBarItem(
                selected = selectedRoute == item.ruta,
                onClick = { navController.navigate(item.ruta) },
                icon = { Icon(imageVector = item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = colorScheme.primary,
                    unselectedIconColor = colorScheme.onSurface,
                    selectedTextColor = colorScheme.primary,
                    unselectedTextColor = colorScheme.onSurface,
                    indicatorColor = colorScheme.primary.copy(alpha = 0.1f)
                )
            )
        }
    }
}


