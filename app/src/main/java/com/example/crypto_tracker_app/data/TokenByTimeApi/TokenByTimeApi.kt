package com.example.crypto_tracker_app.data.TokenByTimeApi

import com.example.crypto_tracker_app.data.modelDto.PriceTimeDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TokenByTimeApi {
    @GET("coins/{id}/market_chart")
    suspend fun getTokenByTime(
        @Path("id") id: String,
        @Query("vs_currency") currency: String,
        @Query("days") days: String
    ): PriceTimeDto
}