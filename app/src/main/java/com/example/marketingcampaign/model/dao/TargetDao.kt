package com.example.marketingcampaign.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.marketingcampaign.model.data.MonthlyFee
import com.example.marketingcampaign.model.data.Target


interface TargetDao {
    /*@Query("SELECT * FROM target")
    fun getAll(): List<Target>

    @Query("SELECT * FROM Target WHERE id IN (:target)")
    fun loadAllByIds(target: IntArray): List<Target>

    *//*@Query("SELECT * FROM MonthlyFee WHERE name LIKE :name LIMIT 1")
    fun findByPrice(name: String): MonthlyFee*//*

    @Insert
    fun insertAll(vararg target: Target)

    @Delete
    fun delete(target: Target)*/
}