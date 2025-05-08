package com.example.rutifyclient.componentes.camposDeTextos

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.VisualTransformation
import com.example.rutifyclient.componentes.textos.TextoInformativo

@Composable
fun CampoTexto(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    @StringRes textoIdLabel: Int,
    error: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    readOnly: Boolean = false,
    icono: ImageVector? = null,
    descripcion: String? = null,
    modifierIcon: Modifier = Modifier,

    ) {
    OutlinedTextField(
        value = value,
        readOnly = readOnly,
        onValueChange = onValueChange,
        label = { TextoInformativo(textoIdLabel) },
        modifier = modifier
            .fillMaxWidth(),
        textStyle = typography.bodyLarge.copy(
            color = colorScheme.onSurface
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = colorScheme.onSurface, // Color del texto cuando está enfocado
            unfocusedTextColor = colorScheme.onSurfaceVariant, // Color del texto cuando no está enfocado
            focusedBorderColor = colorScheme.primary, // Color del borde cuando está enfocado
            unfocusedBorderColor = colorScheme.secondary, // Color del borde cuando no está enfocado
            focusedLabelColor = colorScheme.primary, // Color de la etiqueta cuando está enfocado
            unfocusedLabelColor = colorScheme.secondary // Color de la etiqueta cuando no está enfocado
        ),
        visualTransformation = visualTransformation,
        singleLine = true,
        isError = error,
        trailingIcon = {
            icono?.let {
                Icon(modifier = modifierIcon, imageVector = it, contentDescription = descripcion)
            }
        }
    )
}