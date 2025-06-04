package com.example.rutifyclient.componentes.barras

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.rutifyclient.componentes.textos.TextoTitulo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarBase(
    titulo: Int,
    navigationIcon: (@Composable (() -> Unit))? = null,
    vararg args: Any = emptyArray(),
    acciones: @Composable RowScope.() -> Unit = {},
) {
    Column {
        TopAppBar(
            title = {
                Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    TextoTitulo(textoId = titulo, args = args)
                }
            },
            navigationIcon = {
                navigationIcon?.invoke()
            },
            actions = acciones,
            modifier = Modifier.height(56.dp),
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = colorScheme.background,
                titleContentColor = colorScheme.onSurface,
                navigationIconContentColor = colorScheme.onSurface,
                actionIconContentColor = colorScheme.onSurface
            )
        )
        HorizontalDivider(
            thickness = 1.dp,
            color = colorScheme.primary
        )
    }
}
