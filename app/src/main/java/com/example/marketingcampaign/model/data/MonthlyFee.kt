package com.example.marketingcampaign.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MonthlyFee(
    @PrimaryKey @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "chanelId") val chanelId: Int,
    @ColumnInfo(name = "facilities") val facilities: String,
    @ColumnInfo(name = "price") val price: String
)