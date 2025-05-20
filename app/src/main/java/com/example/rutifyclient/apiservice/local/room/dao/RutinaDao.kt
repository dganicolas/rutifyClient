package com.example.rutifyclient.apiservice.local.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rutifyclient.domain.room.RutinaDtoRoom

@Dao
interface RutinaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(rutina: RutinaDtoRoom)

    @Query("SELECT * FROM rutinas_favoritas WHERE id = :id")
    suspend fun obtenerPorId(id: String): RutinaDtoRoom?

    @Delete
    suspend fun eliminar(rutina: RutinaDtoRoom)

    @Query("SELECT * FROM rutinas_favoritas")
    suspend fun obtenerTodas(): List<RutinaDtoRoom>
}