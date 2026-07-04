package com.example.crypto_tracker_app.data.mapper

import com.example.crypto_tracker_app.data.modelDto.CryptoTokenDto
import com.example.crypto_tracker_app.data.modelDto.SparklineDto
import com.example.crypto_tracker_app.domain.model.CryptoTokenModel

fun CryptoTokenDto.toDomain(): CryptoTokenModel {
        return CryptoTokenModel(
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
            low24h = low24h,
            priceChange24hProsent = priceChange24hProsent,
            priceChange7dProsent = priceChange7dProsent,
            priceChange30dProsent = priceChange30dProsent,
            priceChange1yProsent = priceChange1yProsent,
            sparkline = sparklineIn7d.price,
            tokenRank = tokenRank,
            marketCap = marketCap
        )
    }
    fun CryptoTokenModel.toDto(): CryptoTokenDto {
        return CryptoTokenDto(
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
            low24h = low24h,
            priceChange24hProsent = priceChange24hProsent,
            priceChange7dProsent = priceChange7dProsent,
            priceChange30dProsent = priceChange30dProsent,
            priceChange1yProsent = priceChange1yProsent,
            sparklineIn7d = SparklineDto(price = sparkline),
            tokenRank = tokenRank,
            marketCap = marketCap
        )
    }
