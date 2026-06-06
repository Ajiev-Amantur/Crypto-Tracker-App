package com.example.crypto_tracker_app.data.repository

import com.example.crypto_tracker_app.data.TocenAPI.CryptoApi
import com.example.crypto_tracker_app.data.mapper.toDomain
import com.example.crypto_tracker_app.domain.model.CryptoTocensModel
import com.example.crypto_tracker_app.domain.repository.GetTocensRepository

class GetTocensRepositoryImpl(private val api: CryptoApi): GetTocensRepository {
    override suspend fun getAllTocens(): List<CryptoTocensModel> {
        return api.getTocens("usd").map { it.toDomain() }
    }
}