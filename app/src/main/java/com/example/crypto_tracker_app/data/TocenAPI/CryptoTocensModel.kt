package com.example.crypto_tracker_app.data.TocenAPI

data class CryptoTocensModel(
    val id: String,
    val name: String,
    val image: String,
    val current_price: Double,
    val atl_change_percentage: Double,
    val price_change_percentage_24h: Double,
    val atl: Double,
    val ath: Double,
)