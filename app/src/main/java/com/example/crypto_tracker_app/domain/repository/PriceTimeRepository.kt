package com.example.crypto_tracker_app.domain.repository

import com.example.crypto_tracker_app.domain.model.PriceTimeModel

interface PriceTimeRepository {
    suspend fun getTokenPriceByTime(
        id: String,
        currency: String,
        days: String
    ): PriceTimeModel
}