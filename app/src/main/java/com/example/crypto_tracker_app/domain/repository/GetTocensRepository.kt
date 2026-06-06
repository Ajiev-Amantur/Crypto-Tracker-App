package com.example.crypto_tracker_app.domain.repository

import com.example.crypto_tracker_app.domain.model.CryptoTocensModel

interface GetTocensRepository {
    suspend fun getAllTocens(): List<CryptoTocensModel>

}