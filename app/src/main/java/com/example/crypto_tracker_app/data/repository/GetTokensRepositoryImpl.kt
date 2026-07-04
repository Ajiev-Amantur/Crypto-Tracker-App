package com.example.crypto_tracker_app.data.repository

import android.util.Log
import com.example.crypto_tracker_app.data.TokenAPI.TokenApi
import com.example.crypto_tracker_app.data.mapper.toDomain
import com.example.crypto_tracker_app.domain.model.CryptoTokenModel
import com.example.crypto_tracker_app.domain.repository.GetTokensRepository

class GetTokensRepositoryImpl(private val api: TokenApi): GetTokensRepository {
    override suspend fun getAllTokens(priceBytime: String): List<CryptoTokenModel> {
         val result = api.getTokens(priceBytime,"usd").map { it.toDomain() }
        Log.d("ololo", "$result")

        return result
    }
}