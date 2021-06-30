package com.example.marketingcampaign.viewModel.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marketingcampaign.interfaces.ApiInterface
import com.example.marketingcampaign.model.data.Target
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    var targetsList : MutableLiveData<List<Target>> = MutableLiveData()

    fun getTargetList() : MutableLiveData<List<Target>> {
        return targetsList
    }

    fun getTargetApi(){
        val apiInterface = ApiInterface.create().getTarget()

        apiInterface.enqueue( object : Callback<List<Target>> {
            override fun onResponse(call: Call<List<Target>>?, response: Response<List<Target>>?) {

                if(response?.body() != null) {
                    targetsList.postValue(response.body())

                }
            }

            override fun onFailure(call: Call<List<Target>>?, t: Throwable?) {
                Log.d("apiResponse", "Fail")
            }
        })
    }
}


