package com.example.crypto_tracker_app.domain.model.room.BalanceUser

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.crypto_tracker_app.domain.model.room.BalanceUser.BalanceDataModel

@Dao
interface BalanceDao {
    @Upsert()
    suspend fun insertBalance(sum: BalanceDataModel)

    @Query("SELECT * FROM BalanceDataModel WHERE id = 0")
    suspend fun getBalance(): BalanceDataModel?
}