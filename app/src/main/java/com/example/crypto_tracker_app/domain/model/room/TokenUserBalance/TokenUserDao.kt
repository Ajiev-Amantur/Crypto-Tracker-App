package com.example.crypto_tracker_app.domain.model.room.TokenUserBalance

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TokenUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToken(token: UserTokenModel)

    @Query("SELECT * FROM UserTokenModel")
    suspend fun getTokenBalance(): List<UserTokenModel>
}