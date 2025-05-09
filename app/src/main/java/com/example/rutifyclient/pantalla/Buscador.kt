package com.example.rutifyclient.pantalla

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.rutifyclient.domain.UsuarioBusquedaDto
import com.example.rutifyclient.pantalla.foro.utils.PantallaBusquedaUsuarios
import com.example.rutifyclient.pantalla.barScaffolding.PantallaConBarraInferior

@Preview
@Composable
fun Buscador() {
    val focusManager = LocalFocusManager.current
    val usuarios = listOf(
        UsuarioBusquedaDto("1", "xX_ElRey_Xx", "H", true, "a1"),          // Corto
        UsuarioBusquedaDto("2", "LaSuperEstrella123", "M", false, "a2"),   // Largo
        UsuarioBusquedaDto("3", "Pedro_123", "H", true, "a3"),             // Medio
        UsuarioBusquedaDto("4", "Maria_JuegaBien", "M", false, "a4"),      // Medio
        UsuarioBusquedaDto("5", "Carlos2025", "H", true, "a5"),            // Medio
        UsuarioBusquedaDto("6", "Lucia_Fantastica_xX", "M", false, "a6"),  // Largo
        UsuarioBusquedaDto("7", "Luis_1234", "H", true, "a7"),             // Medio
        UsuarioBusquedaDto("8", "Paula_Juega", "M", false, "a8"),          // Medio
        UsuarioBusquedaDto("9", "Sergio_ElBest", "H", false, "a9"),        // Medio
        UsuarioBusquedaDto("10", "Raquel_Pro_89", "M", true, "a10"),       // Medio
        UsuarioBusquedaDto("11", "Javi_Rapido", "H", true, "a11"),         // Medio
        UsuarioBusquedaDto("12", "Claudia_EsLaMejor", "M", false, "a12"), // Largo
        UsuarioBusquedaDto("13", "Eduardo_Futuro_23", "H", false, "a13"),  // Largo
        UsuarioBusquedaDto("14", "XxXxXxElena_MeHizoFamosaXxXxXx", "M", true, "a14"),  // Largo
        UsuarioBusquedaDto("15", "Miguel_2025", "H", true, "a15"),         // Medio
        UsuarioBusquedaDto("16", "Carmen_VamosX", "M", false, "a16"),      // Medio
        UsuarioBusquedaDto("17", "Rafa_TengoElPoder", "H", true, "a17"),   // Largo
        UsuarioBusquedaDto("18", "Isabel_100", "M", false, "a18"),         // Medio
        UsuarioBusquedaDto("19", "Antonio_FuturoCampeon", "H", true, "a19"),// Largo
        UsuarioBusquedaDto("20", "Veronica_ElJuegoMaster", "M", true, "a20") // Largo
    )

    val buscarTexto = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Barra de búsqueda fija en la parte superior
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = buscarTexto.value,
                onValueChange = { buscarTexto.value = it },
                label = { Text("Buscar Usuario") },
                leadingIcon = { Icon(imageVector = Icons.Filled.Search, contentDescription = "Buscar") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                ),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        // Aquí haces la llamada al endpoint
                        focusManager.clearFocus()
                    }
                )
            )
        }
        PantallaConBarraInferior(rememberNavController(),"miZona"){
            PantallaBusquedaUsuarios(usuarios, rememberNavController())
        }

    }
}

