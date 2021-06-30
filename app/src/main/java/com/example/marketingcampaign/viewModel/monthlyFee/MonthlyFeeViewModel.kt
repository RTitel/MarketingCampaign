package com.example.marketingcampaign.viewModel.monthlyFee

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketingcampaign.interfaces.ApiInterface
import com.example.marketingcampaign.model.dao.MonthlyFeeDao
import com.example.marketingcampaign.model.data.MonthlyFee
import com.example.marketingcampaign.model.db.AppDatabase
import com.example.marketingcampaign.model.repository.MonthlyFeeRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MonthlyFeeViewModel(application: Application) : AndroidViewModel(application) {
    var monthlyFeeList : MutableLiveData<List<MonthlyFee>> = MutableLiveData()


    fun getMonthlyFeesList() : MutableLiveData<List<MonthlyFee>> {
        return monthlyFeeList
    }

    fun getMonthlyFeeApi(){
        val apiInterface = ApiInterface.create().getMonthlyFee()

        apiInterface.enqueue( object : Callback<List<MonthlyFee>> {
            override fun onResponse(call: Call<List<MonthlyFee>>?, response: Response<List<MonthlyFee>>?) {

                if(response?.body() != null) {
                    monthlyFeeList.postValue(response.body())

                }
            }

            override fun onFailure(call: Call<List<MonthlyFee>>?, t: Throwable?) {
                Log.d("apiResponse", "Fail")
            }
        })
    }// TODO: Implement the ViewModel

    fun insert(monthlyFee: MonthlyFee) = viewModelScope.launch {
        AppDatabase(getApplication()).monthlyFeeDao().insert(monthlyFee)
    }


}