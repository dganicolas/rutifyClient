package com.example.rutifyclient.utils

import androidx.compose.runtime.Composable
import com.example.rutifyclient.R

@Composable
fun obtenerAvatarResource(nombreAvatar: String): Int {
    return when (nombreAvatar.lowercase()) {
        "a1" -> R.drawable.a1
        "a2" -> R.drawable.a2
        "a3" -> R.drawable.a3
        "a4" -> R.drawable.a4
        "a5" -> R.drawable.a5
        "a6" -> R.drawable.a6
        "a7" -> R.drawable.a7
        "a8" -> R.drawable.a8
        "a9" -> R.drawable.a9
        "a10" -> R.drawable.a10
        "a11" -> R.drawable.a11
        "a12" -> R.drawable.a12
        "a13" -> R.drawable.a13
        "a14" -> R.drawable.a14
        "a15" -> R.drawable.a15
        "a16" -> R.drawable.a16
        "a17" -> R.drawable.a17
        "a18" -> R.drawable.a18
        "a19" -> R.drawable.a19
        "a20" -> R.drawable.a20
        "a21" -> R.drawable.a21
        else -> R.drawable.a1
    }
}