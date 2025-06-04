package com.example.rutifyclient.apiservice.network.api.stripe

import com.example.rutifyclient.apiservice.network.ApiRoutes
import com.example.rutifyclient.domain.stripe.PaymentRequestDto
import com.example.rutifyclient.domain.stripe.PaymentResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface StripeApiService {
    @POST("${ApiRoutes.STRIPE}")
    suspend fun createPaymentIntent(@Body request: PaymentRequestDto): Response<PaymentResponse>
}