package com.example.rutifyclient.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.Accessibility
import androidx.compose.material.icons.filled.AccessibilityNew
import androidx.compose.material.icons.filled.AirlineSeatLegroomExtra
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.SportsGymnastics
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun obtenerIconoRutina(nombreIcono: String): ImageVector {
    return when (nombreIcono.lowercase()) {
        "fb" -> Icons.Filled.Accessibility
        "pn" -> Icons.Filled.AirlineSeatLegroomExtra
        "bz" -> Icons.Filled.FitnessCenter
        "pc" -> Icons.Filled.SelfImprovement
        "ed" -> Icons.Filled.AccessibilityNew
        "cd" -> Icons.Filled.Favorite
        "ht" -> Icons.Filled.Bolt
        "am" -> Icons.Filled.SportsGymnastics
        "mn" -> Icons.Filled.FitnessCenter
        else -> Icons.AutoMirrored.Filled.Help
    }
}