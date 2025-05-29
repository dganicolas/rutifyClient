package com.example.rutifyclient.componentes.camposDeTextos

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.rutifyclient.componentes.textos.TextoInformativo

@Composable
fun CampoTexto(
    modifier: Modifier = Modifier
        .fillMaxWidth(),
    value: String,
    onValueChange: (String) -> Unit,
    @StringRes textoIdLabel: Int,
    error: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    readOnly: Boolean = false,
    icono: ImageVector? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    descripcion: String? = null,
    modifierIcon: Modifier = Modifier,
    maxLength: Int? = null,
    mostrarContador: Boolean = false,
    singleLine: Boolean = true,
    maxLines: Int = 1
    ) {
    OutlinedTextField(
        value = value,
        readOnly = readOnly,
        onValueChange = onValueChange,
        label = { TextoInformativo(textoIdLabel) },
        modifier = modifier,
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
        singleLine = singleLine,
        maxLines = maxLines,
        isError = error,
        keyboardOptions = keyboardOptions,
        trailingIcon = {
            when {
                mostrarContador && maxLength != null -> {
                    Text(
                        text = "${value.length}/${maxLength}",
                        style = typography.labelSmall.copy(color = colorScheme.onSurfaceVariant),
                        modifier = modifierIcon
                    )
                }
                icono != null -> {
                    Icon(
                        modifier = modifierIcon,
                        imageVector = icono,
                        contentDescription = descripcion
                    )
                }
            }
        }
    )
}