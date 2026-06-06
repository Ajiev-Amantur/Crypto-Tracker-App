package com.example.crypto_tracker_app.data.TocenByTime

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PriceTimeIntance {

    val loggingInterceptor = HttpLoggingInterceptor().apply {
        setLevel(Level.BODY)
    }
    val client = OkHttpClient.Builder().addInterceptor(loggingInterceptor)
        .build()
    val api = Retrofit.Builder()
        .baseUrl("https://api.coingecko.com/api/v3/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(TocenByTimeAPi::class.java)
}