package com.example.crypto_tracker_app.data.modelDto

import com.google.gson.annotations.SerializedName

data class CryptoTokenDto(
    val id: String,
    val name: String,
    val image: String,
    @SerializedName("current_price") val currentPrice: Double,
    @SerializedName("atl_change_percentage") val atlChangePercentage: Double,
    @SerializedName("price_change_percentage_24h") val priceChange24h: Double,
    val atl: Double,
    val ath: Double,
    @SerializedName("total_supply") val totalSupply: Double,
    @SerializedName("max_supply") val maxSupply: Double,
    @SerializedName("high_24h") val high24h: Double,
    @SerializedName("low_24h") val low24h: Double,
    @SerializedName("price_change_percentage_24h_in_currency") val priceChange24hProsent: Double,
    @SerializedName("price_change_percentage_7d_in_currency") val priceChange7dProsent: Double,
    @SerializedName("price_change_percentage_30d_in_currency") val priceChange30dProsent: Double,
    @SerializedName("price_change_percentage_1y_in_currency") val priceChange1yProsent: Double,
    @SerializedName("sparkline_in_7d") val sparklineIn7d: SparklineDto,
    @SerializedName("market_cap_rank") val tokenRank: Int,
    @SerializedName("market_cap")val marketCap: Double
)

data class SparklineDto(
    val price : List<Double>
)
