package com.example.marketingcampaign.viewModel.channel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marketingcampaign.interfaces.ApiInterface
import com.example.marketingcampaign.model.data.Channels
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChannelsViewModel : ViewModel() {
    var channelList : MutableLiveData<List<Channels>> = MutableLiveData()

    fun getChannelsList() : MutableLiveData<List<Channels>> {
        return channelList
    }

    fun getChannelApi(){
        val apiInterface = ApiInterface.create().getChannel()

        apiInterface.enqueue( object : Callback<List<Channels>> {
            override fun onResponse(call: Call<List<Channels>>?, response: Response<List<Channels>>?) {

                if(response?.body() != null) {
                    channelList.postValue(response.body())

                }
            }

            override fun onFailure(call: Call<List<Channels>>?, t: Throwable?) {
                Log.d("apiResponse", "Fail")
            }
        })
    }// TODO: Implement the ViewModel
}