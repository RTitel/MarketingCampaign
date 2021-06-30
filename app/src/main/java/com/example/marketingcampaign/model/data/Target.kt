package com.example.marketingcampaign.model.data

import android.os.ParcelUuid
import com.example.marketingcampaign.interfaces.IEntityAdapterTrackerKeyProvider
import com.google.gson.annotations.SerializedName


data class Target(
    @SerializedName("channelId")
    val channelId: List<Int>?,

    @SerializedName("name")
    val name: String?
): IEntityAdapterTrackerKeyProvider {
    override fun getKey(): ParcelUuid {
        TODO("Not yet implemented")
    }
}