package com.example.rutifyclient.pantalla

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Bienvenida() {
// Usamos el color en función del tema actual
    val textColor = MaterialTheme.colorScheme.onBackground

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), // Agregar un poco de padding
        contentAlignment = Alignment.Center // Centra el contenido
    ) {


        val borderColor = MaterialTheme.colorScheme.primary
        val backgroundColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)

        OutlinedCard(

            shape = MaterialTheme.shapes.medium, // Forma de la tarjeta
            border = BorderStroke(4.dp, borderColor),
            colors = CardDefaults.cardColors(containerColor = backgroundColor) // Color de fondo
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "¡Bienvenido a Rutify!",
                    color = textColor,
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(
                    text = "Iniciar sesión",
                    color = textColor,
                    style = MaterialTheme.typography.bodyLarge
                )

                // Campos de texto para ingresar usuario y contraseña
                OutlinedTextField(
                    value = "",
                    onValueChange = { /* manejar cambio de valor */ },
                    label = { Text("Correo electrónico") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = "",
                    onValueChange = { /* manejar cambio de valor */ },
                    label = { Text("Contraseña") },
                    modifier = Modifier.fillMaxWidth(),
                    //visualTransformation = PasswordVisualTransformation() // Ocultar el texto
                )

                // Botón para iniciar sesión
                Button(
                    onClick = { /* acción para iniciar sesión */ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Iniciar sesión")
                }

                // Botón para registrarse
                Button(
                    onClick = { /* acción para registrarse */ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Registrarse")
                }

                // Botón para salir
                Button(
                    onClick = { /* acción para salir */ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Salir")
                }
            }
        }

    }
}

@Preview
@Composable
fun previewBienvenida() {
    Bienvenida()
}