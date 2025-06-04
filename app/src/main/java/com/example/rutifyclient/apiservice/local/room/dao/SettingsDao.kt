package com.example.rutifyclient.apiservice.local.room.dao

import androidx.room.*
import com.example.rutifyclient.domain.room.SettingsDtoRoom

@Dao
interface SettingsDao {
    @Query("SELECT * FROM settings WHERE id = 0")
    suspend fun getSettings(): SettingsDtoRoom?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSettings(settings: SettingsDtoRoom)
}