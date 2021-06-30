package com.example.marketingcampaign.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.marketingcampaign.model.data.Channels


interface ChannelsDao {
    /*@Query("SELECT * FROM Channels")
    fun getAll(): LiveData<List<Channels>>

    @Query("SELECT * FROM Channels WHERE id IN (:channelsIds)")
    fun loadAllByIds(channelsIds: IntArray): List<Channels>

    @Query("SELECT * FROM Channels WHERE name LIKE :name LIMIT 1")
    fun findByName(name: String): Channels

    @Insert
    fun insertAll(vararg channels: Channels)

    @Delete
    fun delete(channels: Channels)*/
}