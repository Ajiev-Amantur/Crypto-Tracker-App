package com.example.crypto_tracker_app.data.mapper

import com.example.crypto_tracker_app.data.modelDto.PriceTimeDto
import com.example.crypto_tracker_app.domain.model.PriceTimeModel

fun PriceTimeDto.toDomain(): PriceTimeModel{
    return PriceTimeModel(
        prices = prices
    )
}
fun PriceTimeModel.toDto(): PriceTimeDto{
    return PriceTimeDto(
        prices = prices
    )
}