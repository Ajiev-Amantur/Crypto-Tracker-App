package com.example.crypto_tracker_app.domain.model

data class BalanceTokenModel(
    val name: String,
    val image: String,
    val price: Double,
    val amount: Double,
    val totalValue: Double,
)