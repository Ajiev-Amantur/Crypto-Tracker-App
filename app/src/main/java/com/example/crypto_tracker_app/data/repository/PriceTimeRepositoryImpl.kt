package com.example.crypto_tracker_app.data.repository

import com.example.crypto_tracker_app.data.TokenByTimeApi.TokenByTimeApi
import com.example.crypto_tracker_app.data.mapper.toDomain
import com.example.crypto_tracker_app.domain.model.PriceTimeModel
import com.example.crypto_tracker_app.domain.repository.PriceTimeRepository

class PriceTimeRepositoryImpl(private val tokenByTime: TokenByTimeApi): PriceTimeRepository {
    override suspend fun getTokenPriceByTime(
        id: String,
        currency: String,
        days: String
    ): PriceTimeModel {
        val token = tokenByTime.getTokenByTime(id,currency,days)
        return token.toDomain()
    }
}