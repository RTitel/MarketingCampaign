package com.example.marketingcampaign.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.marketingcampaign.model.dao.ChannelsDao
import com.example.marketingcampaign.model.dao.MonthlyFeeDao
import com.example.marketingcampaign.model.dao.TargetDao
import com.example.marketingcampaign.model.data.Channels
import com.example.marketingcampaign.model.data.MonthlyFee
import com.example.marketingcampaign.model.data.Target

@Database(entities = [MonthlyFee::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun monthlyFeeDao(): MonthlyFeeDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            AppDatabase::class.java, "marketing.db")
            .build()
    }
}