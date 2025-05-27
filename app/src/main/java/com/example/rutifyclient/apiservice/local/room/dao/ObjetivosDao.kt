package com.example.rutifyclient.apiservice.local.room.dao

import androidx.room.*
import com.example.rutifyclient.domain.room.ObjetivosDtoRoom

@Dao
interface ObjetivosDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(objetivos: ObjetivosDtoRoom)

    @Query("SELECT * FROM objetivos WHERE idUsuario = :id")
    suspend fun obtenerPorId(id: String): ObjetivosDtoRoom?

    @Update
    suspend fun actualizar(objetivos: ObjetivosDtoRoom)

    @Delete
    suspend fun eliminar(objetivos: ObjetivosDtoRoom)
}