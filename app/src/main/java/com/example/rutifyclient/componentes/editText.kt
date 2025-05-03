@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.rutifyclient.componentes

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldDefaults

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.rutifyclient.componentes.TextoInformativo

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun editText(
    texto: String,
    cambioTexto: (String) -> Unit,
    marcador: String,
    infoError: Boolean,
    redondearEsquinas: Dp = 30.dp,
    tamano: Float = 0.93f,
    esPrivado: VisualTransformation = VisualTransformation.None,
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(tamano),
        value = texto,
        onValueChange = { cambioTexto(it) },
//        label = { texto(
//            marcador,
//            bold = FontWeight.Bold,
//            color = Color.Black,
//            align = TextAlign.Center,
//            modifier = Modifier.padding(horizontal = 16.dp)
//        ) },
        shape = RoundedCornerShape(redondearEsquinas),
        colors = TextFieldDefaults.outlinedTextFieldColors(
//            focusedBorderColor = if (infoError) Color.Green else MaterialTheme.colorScheme.error,
//            unfocusedBorderColor = if (infoError) Color.Green else Color.Gray,
//            errorBorderColor = MaterialTheme.colorScheme.error
        ),
        visualTransformation = esPrivado
    )
}

@Composable
fun CampoTexto(
    value: String,
    onValueChange: (String) -> Unit,
    @StringRes textoId: Int,
    error: Boolean = false,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { TextoInformativo(textoId) },
        modifier = modifier
            .fillMaxWidth(),
        textStyle = MaterialTheme.typography.bodyLarge.copy(
            color = MaterialTheme.colorScheme.onSurface
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = MaterialTheme.colorScheme.onSurface, // Color del texto cuando está enfocado
            unfocusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant, // Color del texto cuando no está enfocado
            focusedBorderColor = MaterialTheme.colorScheme.primary, // Color del borde cuando está enfocado
            unfocusedBorderColor = MaterialTheme.colorScheme.secondary, // Color del borde cuando no está enfocado
            focusedLabelColor = MaterialTheme.colorScheme.primary, // Color de la etiqueta cuando está enfocado
            unfocusedLabelColor = MaterialTheme.colorScheme.secondary // Color de la etiqueta cuando no está enfocado
        ),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        singleLine = true,
        isError = error
    )
}