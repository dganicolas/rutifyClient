package com.example.rutifyclient.domain.tienda

data class CoinPack(
    val id: String? = null,
    val nombre: String,
    val monedas: Int,
    val precio: Double,
    val imageUrl: String? = null
)