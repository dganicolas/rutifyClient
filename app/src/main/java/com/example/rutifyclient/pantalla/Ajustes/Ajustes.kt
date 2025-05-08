package com.example.rutifyclient.pantalla.Ajustes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.rutifyclient.R
import com.example.rutifyclient.componentes.botones.ButtonAlerta

@Composable
fun Ajustes(modifier: Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        ButtonAlerta(
            textoId = R.string.eliminar_cuenta,
            onClick = {},
            modifier = Modifier.fillMaxWidth(0.5f).size(60.dp))
    }
}