package com.example.rutifyclient.domain.tienda.compras

import java.time.LocalDateTime

data class Compra(
    val id: String? = null,
    val idUsuario: String,
    val idCosmetico: String,
    val fechaCompra: String = LocalDateTime.now().toString()
)