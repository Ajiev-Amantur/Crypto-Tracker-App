package com.example.crypto_tracker_app.data.repository

import com.example.crypto_tracker_app.data.TocenByTimeApi.TocenByTimeAPi
import com.example.crypto_tracker_app.data.mapper.toDomain
import com.example.crypto_tracker_app.domain.model.PriceTimeModel
import com.example.crypto_tracker_app.domain.repository.PriceTimeRepository

class PriceTimeRepositoryImpl(private val tocenByTime: TocenByTimeAPi): PriceTimeRepository {
    override suspend fun getTocenPriceByTime(
        id: String,
        currency: String,
        days: String
    ): PriceTimeModel {
        val tocen = tocenByTime.getTocenByTime(id,currency,days)
        return tocen.toDomain()
    }
}