package com.example.crypto_tracker_app.domain.usecase

import com.example.crypto_tracker_app.domain.model.CryptoTokenModel


class SortTokensByPriceUp24h {
     fun execute(tokenList: List<CryptoTokenModel> ): List<CryptoTokenModel> {
        return tokenList.sortedByDescending { it.priceChange24hProsent }
        }
    }
