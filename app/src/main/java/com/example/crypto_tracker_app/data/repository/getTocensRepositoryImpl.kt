package com.example.crypto_tracker_app.data.repository

import com.example.crypto_tracker_app.data.TocenAPI.CryptoApi
import com.example.crypto_tracker_app.domain.repository.CryptoTocensModel
import com.example.crypto_tracker_app.domain.repository.GetTocensRepository

class getTocensRepositoryImpl(private val api: CryptoApi): GetTocensRepository {
    override suspend fun getAllTocens(): List<CryptoTocensModel> {
        return api.getTocens("usd")
    }
}