package com.example.rutifyclient.pantalla.miZona

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.rutifyclient.R
import com.example.rutifyclient.componentes.barras.NavigationBarAbajoPrincipal
import com.example.rutifyclient.componentes.textos.TextoTitulo
import com.example.rutifyclient.componentes.textos.TextoInput
import com.example.rutifyclient.navigation.Rutas
import com.example.rutifyclient.pantalla.PantallaBase
import com.example.rutifyclient.viewModel.usuario.MiZonaViewModel

@Composable
fun MiZona(navControlador: NavHostController) {
    val viewModel: MiZonaViewModel = viewModel()
    LaunchedEffect(Unit) {
        viewModel.obtenerUsuario()
    }
    PantallaBase(
        cargando = false,
        sinInternet = false,
        onReintentar = {},
        bottomBar = ({ NavigationBarAbajoPrincipal(navControlador, Rutas.MiZona) })
    ) {
        Column(
            modifier = Modifier.padding(it).fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.05f)
                    .padding(top = 5.dp)
            ) {
                TextoTitulo(R.string.usuario)
                TextoInput("LoremIpsum")
                Spacer(Modifier.weight(1f))
                Icon(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    imageVector = Icons.Default.Settings,
                    contentDescription = ""
                )
            }
            HorizontalDivider(
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.primary
            )
            //cuerpo
            Column() {
                Text("Aqui el progreso del ususario")
            }
    }


//        Row {
//            Card(
//                modifier = Modifier
//                    .aspectRatio(1f)
//            ) {
//                Text("KCAL")
//            }
//            Card(
//                modifier = Modifier
//                    .aspectRatio(1f)
//            ) {
//                Text("meta rutinas")
//            }
//            Card(
//                modifier = Modifier
//                    .aspectRatio(1f)
//            ) {
//                Text("actividad")
//            }
//        }
//        LazyRow(modifier = Modifier.fillMaxWidth()) {
//            item {
//                Card(
//                    modifier = Modifier
//                        .aspectRatio(1f)
//                ) {
//                    Text("rutinas completadas")
//                }
//            }
//            item {
//                Card(
//                    modifier = Modifier
//                        .aspectRatio(1f)
//                ) {
//                    Text("reto diario")
//                }
//            }
//            item {
//                Card(
//                    modifier = Modifier
//                        .aspectRatio(1f)
//                ) {
//                    Text("racha de dias")
//                }
//            }
//        }
        //menu navegacion

    }
}