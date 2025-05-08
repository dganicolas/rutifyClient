package com.example.rutifyclient.componentes.espaciadores

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun SpacerVertical(tamano: Dp){
    Spacer(modifier = Modifier.height(tamano))
}
