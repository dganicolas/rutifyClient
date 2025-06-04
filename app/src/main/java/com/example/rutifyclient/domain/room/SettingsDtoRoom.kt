package com.example.rutifyclient.domain.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings")
data class SettingsDtoRoom(
    @PrimaryKey val id: Int = 0,  // siempre un solo registro
    val backgroundOption: Int, // 0=imagen, 1=blanco, 2=negro, 3=gris
    val fontSizeScale: Float,  // 1.0 = normal, >1.0 más grande, <1.0 más pequeño
    val themeOption: Int // 0 = seguir sistema, 1=claro, 2=oscuro
)
