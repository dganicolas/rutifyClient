package com.example.rutifyclient.domain.tienda.cosmeticos

data class Cosmetico(
    val _id: String? = null,
    val nombre: String,
    val tipo: String, // "camiseta", "pantalon", "tenis", etc.
    val precioMonedas: Int,
    val imagenUrl: String
)