package com.example.rutifyclient.domain.stripe

data class PaymentRequestDto(
    val userId:String,
    val coins: Int
)