package com.example.crypto_tracker_app.domain.usecase

import com.example.crypto_tracker_app.domain.model.CryptoTokenModel


class SortTokenHighPriceUseCase {

      fun execute(tokenList: List<CryptoTokenModel>): List<CryptoTokenModel> {
         return tokenList.sortedByDescending { it.currentPrice }
         }
    }
