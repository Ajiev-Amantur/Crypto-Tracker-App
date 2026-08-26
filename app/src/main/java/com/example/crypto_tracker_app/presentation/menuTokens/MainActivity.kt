package com.example.crypto_tracker_app.presentation.menuTokens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.crypto_tracker_app.presentation.viewmodel.TokenP_TimeViewModel
import com.example.crypto_tracker_app.presentation.viewmodel.TokenViewModel
import com.example.crypto_tracker_app.ui.theme.CryptoTrackerAppTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme

class MainActivity : ComponentActivity() {
    private val tokenPriceViewModel: TokenP_TimeViewModel by viewModel()
    private val tokenViewModel: TokenViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptoTrackerAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainListTokens(tokenViewModel, tokenPriceViewModel)
                }
            }
        }
    }
    @Preview
    @Composable
    fun Show(){
        MainListTokens(tokenViewModel, tokenPriceViewModel)
    }
}