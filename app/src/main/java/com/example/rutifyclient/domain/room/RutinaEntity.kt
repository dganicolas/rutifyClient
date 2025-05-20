package com.example.rutifyclient.domain.room
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rutinas_favoritas")
data class RutinaDtoRoom(
    @PrimaryKey val id: String,
    val nombre: String,
    val descripcion: String,
    val imagen: String,
    val equipo: String
)