package com.example.rutifyclient.pantalla

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.rutifyclient.datos.DataStore
import com.example.rutifyclient.utils.Usuario
import kotlinx.coroutines.launch

@Composable
fun TokenTestScreen() {
    var tokenInput by remember { mutableStateOf("") }
    var savedToken by remember { mutableStateOf("") }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // Observar cambios en el token guardado
    LaunchedEffect(Unit) {
        savedToken = Usuario.obtenerJwtUsuario().toString()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Campo para ingresar el token
        OutlinedTextField(
            value = tokenInput,
            onValueChange = { tokenInput = it },
            label = { Text("Ingresa tu token") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para guardar
        Button(
            onClick = {
                coroutineScope.launch {
                    //DataStore.guardarToken(context, tokenInput)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar Token")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para borrar
        Button(
            onClick = {
                coroutineScope.launch {
                    //DataStore.borrarToken(context)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Borrar Token")
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Mostrar el token guardado
        Text(
            text = "Token guardado: $savedToken",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
        )
    }
}