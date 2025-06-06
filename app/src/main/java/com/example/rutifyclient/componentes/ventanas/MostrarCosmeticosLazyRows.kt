package com.example.rutifyclient.componentes.ventanas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.rutifyclient.R
import com.example.rutifyclient.componentes.tarjetas.CosmeticoCard
import com.example.rutifyclient.componentes.textos.TextoTitulo
import com.example.rutifyclient.domain.tienda.cosmeticos.Cosmetico

@Composable
fun MostrarCosmeticosLazyRows(
    cosmeticosPorTipo: Map<String, List<Cosmetico>>,
    onCosmeticoClick: (Cosmetico) -> Unit,
    textoBoton: Int = R.string.comprar,
    estado: Boolean = true
) {
    // Scroll vertical para toda la pantalla
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        cosmeticosPorTipo.forEach { (tipo, lista) ->
            TextoTitulo(R.string.texto_input, tipo.replaceFirstChar { it.uppercase() })
            Spacer(modifier = Modifier.height(8.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(lista) { cosmetico ->
                    CosmeticoCard(
                        cosmetico = cosmetico,
                        onClick = { onCosmeticoClick(cosmetico) },
                        textoBoton = textoBoton,
                        estado = estado
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}