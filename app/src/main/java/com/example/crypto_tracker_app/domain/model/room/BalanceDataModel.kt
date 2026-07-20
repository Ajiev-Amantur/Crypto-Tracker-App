package com.example.crypto_tracker_app.domain.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BalanceDataModel(
    @PrimaryKey()
    val id: Int = 0,
    val balance: Int
)