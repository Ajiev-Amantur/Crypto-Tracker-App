package com.example.crypto_tracker_app.presentation

import com.example.crypto_tracker_app.domain.model.CryptoTokenModel

sealed class TokenUiState {
    object loading: TokenUiState()
    data class Sucsess(val tokens: List<CryptoTokenModel>): TokenUiState()
    data class Error(val error: String): TokenUiState()
}