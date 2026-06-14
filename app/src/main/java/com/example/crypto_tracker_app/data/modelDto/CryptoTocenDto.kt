package com.example.crypto_tracker_app.data.modelDto

import com.google.gson.annotations.SerializedName

data class CryptoTocenDto(
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
)