package com.example.crypto_tracker_app.data.TokenAPI

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
val loggingInterceptor = HttpLoggingInterceptor().apply {
    setLevel(Level.BODY)
}
    val client = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
    val api = Retrofit.Builder()
        .baseUrl("https://api.coingecko.com/api/v3/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
        .create(TokenApi::class.java)

}