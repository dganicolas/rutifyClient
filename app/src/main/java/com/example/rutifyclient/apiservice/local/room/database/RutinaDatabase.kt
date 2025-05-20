package com.example.rutifyclient.apiservice.local.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.rutifyclient.apiservice.local.room.dao.RutinaDao
import com.example.rutifyclient.domain.room.RutinaDtoRoom

@Database(entities = [RutinaDtoRoom::class], version = 1)
abstract class RutinaDatabase : RoomDatabase() {
    abstract fun rutinaDao():  RutinaDao

    companion object {
        @Volatile private var instancia: RutinaDatabase? = null

        fun obtenerInstancia(context: Context): RutinaDatabase {
            return instancia ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    RutinaDatabase::class.java,
                    "rutinas_db"
                ).build().also { instancia = it }
            }
        }
    }
}
