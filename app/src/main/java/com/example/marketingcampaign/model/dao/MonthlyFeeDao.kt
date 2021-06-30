package com.example.marketingcampaign.model.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.example.marketingcampaign.model.data.MonthlyFee

@Dao
interface MonthlyFeeDao {

    @Query("SELECT * FROM MonthlyFee")
    suspend fun getAll(): MutableList<MonthlyFee>

    @Query("SELECT * FROM MonthlyFee")
    fun getAllLiveData(): LiveData<List<MonthlyFee>>

    @Query("SELECT * FROM MonthlyFee WHERE id IN (:monthlyFee)")
    fun loadAllByIds(monthlyFee: IntArray): List<MonthlyFee>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(monthlyFee: MonthlyFee)

    @Delete
    fun delete(monthlyFee: MonthlyFee)

    @Query("DELETE FROM MonthlyFee")
    suspend fun deleteAll()
}