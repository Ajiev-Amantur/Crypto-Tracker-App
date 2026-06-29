package com.example.crypto_tracker_app.domain.usecase

import com.example.crypto_tracker_app.domain.model.CryptoTocensModel
import com.example.crypto_tracker_app.domain.repository.GetTocensRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SortTocenHighPriceUseCase(private val getALlTocens: GetTocensRepository) {

     suspend fun execute():List<CryptoTocensModel>{
         return withContext(Dispatchers.Default){
             val result = getALlTocens.getAllTocens()
             result.sortedByDescending { it.currentPrice }
         }
    }
}