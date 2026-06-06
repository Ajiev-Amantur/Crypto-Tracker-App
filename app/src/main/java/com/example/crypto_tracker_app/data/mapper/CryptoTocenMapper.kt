package com.example.crypto_tracker_app.data.mapper

import com.example.crypto_tracker_app.data.modelDto.CryptoTocenDto
import com.example.crypto_tracker_app.domain.model.CryptoTocensModel

    fun CryptoTocenDto.toDomain(): CryptoTocensModel {
        return CryptoTocensModel(
            id = id,
            name = name,
            image = image,
            current_price = current_price,
            atl_change_percentage = atl_change_percentage,
            price_change_percentage_24h = price_change_percentage_24h,
            atl = atl,
            ath = ath
        )
    }
    fun CryptoTocensModel.toDto(): CryptoTocenDto {
        return CryptoTocenDto(
            id = id,
            name = name,
            image = image,
            current_price = current_price,
            atl_change_percentage = atl_change_percentage,
            price_change_percentage_24h = price_change_percentage_24h,
            atl = atl,
            ath = ath
        )
    }
