package com.example.rutifyclient.pantalla.rutinas

import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.rutifyclient.pantalla.uiUtils.PantallaConBarraSuperiorRutinas

@Composable
fun PantallaDetallesRutinas(idRutina:String){
    PantallaConBarraSuperiorRutinas({  }, {  }){
        Column(
            modifier = Modifier.padding(it)
        ) {
            Text(idRutina)
        }
    }
}

@Preview
@Composable
fun preview23(){
    PantallaDetallesRutinas("1")
}

