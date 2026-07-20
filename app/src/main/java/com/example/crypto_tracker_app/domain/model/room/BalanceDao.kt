package com.example.crypto_tracker_app.domain.model.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface BalanceDao {
    @Upsert()
    suspend fun insertBalance(sum: BalanceDataModel)

    @Query("SELECT * FROM BalanceDataModel WHERE id = 0")
    suspend fun getBalance(): BalanceDataModel?
}