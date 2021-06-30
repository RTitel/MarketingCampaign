package com.example.marketingcampaign.model.repository

import androidx.annotation.WorkerThread
import com.example.marketingcampaign.model.dao.MonthlyFeeDao
import com.example.marketingcampaign.model.data.MonthlyFee
import com.example.marketingcampaign.model.db.AppDatabase

class MonthlyFeeRepository(private val monthlyFeeDao: MonthlyFeeDao)  {

    /*val allMonthlyFee: MutableList<MonthlyFee> = monthlyFeeDao.getAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(monthlyFee: MonthlyFee) {
        monthlyFeeDao.insert(monthlyFee)
    }*/
}