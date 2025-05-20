package com.example.rutifyclient.componentes.icono

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.rutifyclient.R

@Composable
fun Icono(imagen: Painter? = null, icono: ImageVector? = null, descripcion: Int, onClick: () -> Unit, modifier: Modifier = Modifier.size(45.dp), tint: Color = Color.Unspecified) {
    if (icono != null) {
        Icon(
            imageVector = icono,
            contentDescription = stringResource(descripcion),
            modifier = modifier.clickable { onClick() },
            tint = tint
        )
    } else if (imagen != null) {
        Image(
            painter = imagen,
            contentDescription = stringResource(descripcion),
            modifier = modifier.clickable { onClick() }
        )
    }
}