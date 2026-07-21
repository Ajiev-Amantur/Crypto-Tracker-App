package com.example.crypto_tracker_app.domain.model.room.BalanceUser

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [BalanceDataModel::class], version = 1)
abstract class BalanceDataBase: RoomDatabase() {
    abstract val dao: BalanceDao
    companion object{
        fun createBalanceDataBase(context: Context): BalanceDataBase{
            return Room.databaseBuilder(
                context,
                BalanceDataBase::class.java,
                "BalanceTotal"
            ).fallbackToDestructiveMigration().build()
        }
    }
}