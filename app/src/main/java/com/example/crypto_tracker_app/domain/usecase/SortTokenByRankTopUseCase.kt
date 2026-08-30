package com.example.crypto_tracker_app.domain.usecase

import com.example.crypto_tracker_app.domain.model.CryptoTokenModel


class SortTokenByRankTopUseCase {
     fun execute(tokenList: List<CryptoTokenModel>): List<CryptoTokenModel> {
        return tokenList.sortedBy { it.tokenRank }
        }
    }