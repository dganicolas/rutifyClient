package com.example.rutifyclient.domain.paypal

data class OrderRequest(
    val intent: String = "CAPTURE",
    val purchase_units: List<PurchaseUnit>
)