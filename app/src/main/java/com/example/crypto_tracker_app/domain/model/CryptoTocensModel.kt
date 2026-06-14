package com.example.crypto_tracker_app.domain.model

data class CryptoTocensModel(
    val id: String,
    val name: String,
    val image: String,
    val currentPrice: Double,
    val atlChangePercentage: Double,
    val priceChange24h: Double,
    val atl: Double,
    val ath: Double,
    val totalSupply: Double,
    val maxSupply: Double,
    val high24h: Double,
    val low24h: Double
)