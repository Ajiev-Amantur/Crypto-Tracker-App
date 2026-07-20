package com.example.crypto_tracker_app.presentation

import android.app.Application
import android.content.Context
import com.example.crypto_tracker_app.di.tokenModule
import com.example.crypto_tracker_app.di.tokenPriceModule
import com.example.crypto_tracker_app.domain.model.room.BalanceDataBase
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