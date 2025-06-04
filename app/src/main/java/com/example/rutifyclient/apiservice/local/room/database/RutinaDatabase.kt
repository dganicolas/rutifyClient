package com.example.rutifyclient.apiservice.local.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.rutifyclient.apiservice.local.room.dao.ObjetivosDao
import com.example.rutifyclient.apiservice.local.room.dao.RutinaDao
import com.example.rutifyclient.apiservice.local.room.dao.SettingsDao
import com.example.rutifyclient.domain.room.ObjetivosDtoRoom
import com.example.rutifyclient.domain.room.RutinaDtoRoom
import com.example.rutifyclient.domain.room.SettingsDtoRoom

@Database(entities = [RutinaDtoRoom::class, ObjetivosDtoRoom::class, SettingsDtoRoom::class], version = 3)
abstract class RutinaDatabase : RoomDatabase() {
    abstract fun rutinaDao():  RutinaDao
    abstract fun objetivosDao(): ObjetivosDao
    abstract fun settingsDao(): SettingsDao
    companion object {
        @Volatile private var instancia: RutinaDatabase? = null

        fun obtenerInstancia(context: Context): RutinaDatabase {
            return instancia ?: synchronized(this) {
                Room.databaseBuilder(
                                context.applicationContext,
                                RutinaDatabase::class.java,
                                "rutinas_db"
                            ).fallbackToDestructiveMigration(false).build().also { instancia = it }
            }
        }
    }
}
