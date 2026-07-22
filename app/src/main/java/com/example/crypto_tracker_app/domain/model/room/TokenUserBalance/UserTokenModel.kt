package com.example.crypto_tracker_app.domain.model.room.TokenUserBalance

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserTokenModel(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val name: String,
    val image: String,
    val price: Double,
    var amount: Double,
    var totalValue: Double,
)