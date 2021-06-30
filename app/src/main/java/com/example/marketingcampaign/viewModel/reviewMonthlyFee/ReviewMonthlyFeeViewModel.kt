package com.example.marketingcampaign.viewModel.reviewMonthlyFee

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.marketingcampaign.interfaces.ApiInterface
import com.example.marketingcampaign.model.dao.MonthlyFeeDao
import com.example.marketingcampaign.model.data.MonthlyFee
import com.example.marketingcampaign.model.db.AppDatabase
import com.example.marketingcampaign.model.repository.MonthlyFeeRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewMonthlyFeeViewModel(application: Application) : AndroidViewModel(application) {
    var monthlyFeeList : LiveData<List<MonthlyFee>> = MutableLiveData()


    fun getMonthlyFeesList() : LiveData<List<MonthlyFee>> {
        return monthlyFeeList
    }



    fun insert(monthlyFee: MonthlyFee) = viewModelScope.launch {
        AppDatabase(getApplication()).monthlyFeeDao().insert(monthlyFee)
    }

    fun getAllLiveData() {
        monthlyFeeList = AppDatabase(getApplication()).monthlyFeeDao().getAllLiveData()
    }

    suspend fun getAll() = AppDatabase(getApplication()).monthlyFeeDao().getAll()

    suspend fun clear() = AppDatabase(getApplication()).monthlyFeeDao().deleteAll()

}