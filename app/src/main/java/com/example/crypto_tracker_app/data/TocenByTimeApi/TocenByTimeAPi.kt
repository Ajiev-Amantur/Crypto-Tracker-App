package com.example.crypto_tracker_app.data.TocenByTimeApi

import com.example.crypto_tracker_app.data.modelDto.PriceTimeDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TocenByTimeAPi {
    @GET("coins/{id}/market_chart")
    suspend fun getTocenByTime(
        @Path("id") id: String,
        @Query("vs_currency") currency: String,
        @Query("days") days: String
    ): PriceTimeDto
}