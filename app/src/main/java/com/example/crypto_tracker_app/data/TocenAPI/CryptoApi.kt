package com.example.crypto_tracker_app.data.TocenAPI

import com.example.crypto_tracker_app.domain.repository.CryptoTocensModel
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoApi {
//    @GET("coins/markets")
//    suspend fun getTocens(): List<CryptoTocensModel>
@GET("coins/markets")
    suspend fun getTocens(
    @Query("vs_currency") currency: String): List<CryptoTocensModel>

}