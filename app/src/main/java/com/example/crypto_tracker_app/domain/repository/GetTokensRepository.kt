package com.example.crypto_tracker_app.domain.repository

import com.example.crypto_tracker_app.domain.model.CryptoTokenModel

interface GetTokensRepository {
    suspend fun getAllTokens(priceByTime: String = "24h,7d,30d,1y",): List<CryptoTokenModel>

}