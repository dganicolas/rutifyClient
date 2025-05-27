package com.example.rutifyclient.domain.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "objetivos")
data class ObjetivosDtoRoom(
    @PrimaryKey val idUsuario: String,
    val kcalDiarias: Int,
    val rutinasObjetivo: Int,
    val minutosActivosObjetivo: Float
)