package com.example.crypto_tracker_app.domain.repository

import com.example.crypto_tracker_app.domain.model.CryptoTocensModel

interface GetTocensRepository {
    suspend fun getAllTocens(pricebyTime: String = "24h,7d,30d,1y"): List<CryptoTocensModel>

}