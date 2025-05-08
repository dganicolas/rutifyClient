package com.example.rutifyclient.datos

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.rutifyclient.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object DataStore {

    private val TOKEN_KEY = stringPreferencesKey("auth_token")

    suspend fun guardarToken(context: Context, token: String) {
        context.dataStore.edit { prefs ->
            prefs[TOKEN_KEY] = token
        }
    }

    fun obtenerToken(context: Context): Flow<String?> {
        return context.dataStore.data.map { prefs ->
            prefs[TOKEN_KEY]
        }
    }

    suspend fun borrarToken(context: Context) {
        context.dataStore.edit { prefs ->
            prefs.remove(TOKEN_KEY)
        }
    }
}