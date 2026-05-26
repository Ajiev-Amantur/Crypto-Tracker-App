package com.example.crypto_tracker_app.data

import retrofit2.http.GET

interface CryptoApi {
    @GET("coins/markets")
    suspend fun getTocens(): List<CryptoTocensModel>
}