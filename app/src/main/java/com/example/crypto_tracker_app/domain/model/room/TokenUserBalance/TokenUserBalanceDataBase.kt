package com.example.crypto_tracker_app.domain.model.room.TokenUserBalance

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [BalanceTokenModel::class], version = 1)
abstract class TokenUserBalanceDataBase(): RoomDatabase() {

    abstract val dao: TokenUserDao
    companion object {
        fun createTokenUserBDataBase(context: Context): TokenUserBalanceDataBase {
            return Room.databaseBuilder(
                context,
                TokenUserBalanceDataBase::class.java,
                "TokenBalance"
            ).build()
        }
    }
}