package com.example.rutifyclient.domain.botones

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.rutifyclient.navigation.Rutas

sealed class BottonNavegacion(val ruta: String, val icon: ImageVector, val label: String) {
    object Rutina : BottonNavegacion(Rutas.Rutina, Icons.Default.FitnessCenter, "Rutinas")
    object MiZona : BottonNavegacion("miZona/${""}", Icons.Default.Person, "Mi Zona")
    object Comunidad : BottonNavegacion(Rutas.Comunidad, Icons.Default.Groups, "Foro")
    object Ajustes : BottonNavegacion(Rutas.Ajustes, Icons.Default.Settings, "Ajustes")
    object Tienda : BottonNavegacion(Rutas.Tienda, Icons.Default.ShoppingCart, "Tienda")

}