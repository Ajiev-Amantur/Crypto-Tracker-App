package com.example.crypto_tracker_app.domain.model

import com.google.gson.annotations.SerializedName

data class CryptoTocensModel(
    val id: String,
    val name: String,
    val image: String,
    @SerializedName("price")val current_price: Double,
    @SerializedName("allTimePrice")val atl_change_percentage: Double,
    @SerializedName("price24h")val price_change_percentage_24h: Double,
    @SerializedName("LowestPrice")val atl: Double,
    @SerializedName("highestPrice")val ath: Double,
)