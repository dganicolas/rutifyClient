package com.example.rutifyclient.utils

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

object Usuario {

    suspend fun obtenerJwtUsuario(): String? {
        val usuario = FirebaseAuth.getInstance().currentUser
        return usuario?.getIdToken(true)?.await()?.token?.let { "Bearer $it" }
    }
}