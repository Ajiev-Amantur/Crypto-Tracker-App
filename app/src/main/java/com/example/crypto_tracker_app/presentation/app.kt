package com.example.crypto_tracker_app.presentation

import android.app.Application
import com.example.crypto_tracker_app.di.tokenModule
import com.example.crypto_tracker_app.di.tokenPriceModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(tokenModule, tokenPriceModule)
        }
    }
}