package com.example.crypto_tracker_app.domain.usecase

import com.example.crypto_tracker_app.domain.model.CryptoTokenModel
import com.example.crypto_tracker_app.domain.repository.GetTokensRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SortTokenLowPriceUseCase(private val getAllTokens: GetTokensRepository) {

    suspend fun execute(): List<CryptoTokenModel> {
        return withContext(Dispatchers.Default){
            val result = getAllTokens.getAllTokens()
            result.sortedBy { it.currentPrice }
        }
    }
}