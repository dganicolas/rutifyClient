package com.example.rutifyclient.componentes.icono

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun IconoPuntuacion(icono: ImageVector, descripcion: Int, onClick: () -> Unit, modifier: Modifier = Modifier.size(45.dp), tint: Color = Color.Unspecified) {
        Icon(
            imageVector = icono,
            contentDescription = stringResource(descripcion),
            modifier = modifier.clickable { onClick() },
            tint = tint
        )
}