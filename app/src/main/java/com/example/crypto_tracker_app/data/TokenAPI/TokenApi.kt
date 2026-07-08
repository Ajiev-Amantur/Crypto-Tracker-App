package com.example.crypto_tracker_app.data.TokenAPI

import com.example.crypto_tracker_app.data.modelDto.CryptoTokenDto
import retrofit2.http.GET
import retrofit2.http.Query

interface TokenApi {
@GET("coins/markets")
    suspend fun getTokens(
    @Query("price_change_percentage") priceChange: String = "24h,7d,30d,1y",
    @Query("vs_currency") currency: String,
    @Query("sparkline") sparkline: Boolean = true
    ): List<CryptoTokenDto>

}