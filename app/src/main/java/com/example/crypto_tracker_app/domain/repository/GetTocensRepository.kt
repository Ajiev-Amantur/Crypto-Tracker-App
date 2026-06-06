package com.example.crypto_tracker_app.domain.repository

interface GetTocensRepository {
    suspend fun getAllTocens(): List<CryptoTocensModel>

}