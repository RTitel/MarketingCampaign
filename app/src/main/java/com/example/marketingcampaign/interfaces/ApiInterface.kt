package com.example.marketingcampaign.interfaces

import com.example.marketingcampaign.model.data.*
import com.example.marketingcampaign.model.data.Target
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiInterface {

    @GET("b/U8QO")
    fun getTarget() : Call<List<Target>>

    @GET("b/U26B")
    fun getChannel() : Call<List<Channels>>

    @GET("b/EXV2")
    fun getMonthlyFee() : Call<List<MonthlyFee>>

    companion object {

        var BASE_URL = "https://jsonkeeper.com/"

        fun create() : ApiInterface {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)

        }
    }

}