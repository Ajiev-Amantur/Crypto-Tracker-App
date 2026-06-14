package com.example.crypto_tracker_app.data.mapper

import com.example.crypto_tracker_app.data.modelDto.CryptoTocenDto
import com.example.crypto_tracker_app.domain.model.CryptoTocensModel

fun CryptoTocenDto.toDomain(): CryptoTocensModel {
        return CryptoTocensModel(
            id = id,
            name = name,
            image = image,
            currentPrice = currentPrice,
            atlChangePercentage = atlChangePercentage,
            priceChange24h = priceChange24h,
            atl = atl,
            ath = ath,
            totalSupply = totalSupply,
            maxSupply = maxSupply,
            high24h = high24h,
            low24h = low24h
        )
    }
    fun CryptoTocensModel.toDto(): CryptoTocenDto {
        return CryptoTocenDto(
            id = id,
            name = name,
            image = image,
            currentPrice = currentPrice,
            atlChangePercentage = atlChangePercentage,
            priceChange24h = priceChange24h,
            atl = atl,
            ath = ath,
            totalSupply = totalSupply,
            maxSupply = maxSupply,
            high24h = high24h,
            low24h = low24h
        )
    }
