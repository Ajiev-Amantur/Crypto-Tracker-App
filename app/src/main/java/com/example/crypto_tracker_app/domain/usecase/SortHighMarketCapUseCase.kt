package com.example.crypto_tracker_app.domain.usecase

import com.example.crypto_tracker_app.domain.model.CryptoTocensModel
import com.example.crypto_tracker_app.domain.repository.GetTocensRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SortHighMarketCapUseCase(private val getTocenRepo: GetTocensRepository) {
    suspend fun execute(): List<CryptoTocensModel>{
        return withContext(Dispatchers.Default){
            val result = getTocenRepo.getAllTocens()
            result.sortedByDescending { it.marketCap }
        }
    }
}