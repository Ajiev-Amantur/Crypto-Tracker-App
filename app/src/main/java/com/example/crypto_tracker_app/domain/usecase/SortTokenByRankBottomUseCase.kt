package com.example.crypto_tracker_app.domain.usecase

import com.example.crypto_tracker_app.domain.model.CryptoTokenModel


class SortTokenByRankBottomUseCase {
     fun execute(tokenList: List<CryptoTokenModel>): List<CryptoTokenModel> {
            return tokenList.sortedByDescending { it.tokenRank }
        }
    }
