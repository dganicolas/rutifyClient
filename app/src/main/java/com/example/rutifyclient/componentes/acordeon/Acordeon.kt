package com.example.rutifyclient.componentes.acordeon

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import com.example.rutifyclient.R
import com.example.rutifyclient.componentes.textos.TextoInformativo
import com.example.rutifyclient.componentes.textos.TextoTitulo

@Composable
fun Acordeon(titulo: Int, contenido: String) {
    var expandido by remember { mutableStateOf(false) }
    val rotacion by animateFloatAsState(if (expandido) 180f else 0f, label = "")
    Surface(
        color = colorScheme.background,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, colorScheme.surface),
    ) {
        Column{
            Row(
                modifier = Modifier
                    .clickable { expandido = !expandido }
                    .padding(16.dp).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextoTitulo(titulo)
                Spacer(modifier = Modifier.weight(1f))
                Surface(shape = CircleShape, color = colorScheme.onSurface.copy(alpha = 0.1f)) {
                    Icon(
                        Icons.Outlined.ArrowDropDown,
                        contentDescription = "arrow-down",
                        modifier = Modifier.rotate(rotacion),
                        tint =  colorScheme.onSurface
                    )
                }
            }
            AnimatedVisibility(visible = expandido) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    TextoInformativo(R.string.mensaje_acordeon,contenido)
                }
                HorizontalDivider(color = colorScheme.onBackground)
            }
        }
    }
}