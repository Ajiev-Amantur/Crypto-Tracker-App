package com.example.crypto_tracker_app.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.example.crypto_tracker_app.presentation.viewmodel.TokenViewModel
import com.example.crypto_tracker_app.presentation.viewmodel.TokenP_TimeViewModel
import com.example.crypto_tracker_app.ui.theme.CryptoTrackerAppTheme

class MainActivity : ComponentActivity() {
    private val tokenPriceViewModel: TokenP_TimeViewModel by viewModel()
    private val tokenViewModel: TokenViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptoTrackerAppTheme {
                MainListTokens(tokenViewModel, tokenPriceViewModel)
            }
        }
    }
}
