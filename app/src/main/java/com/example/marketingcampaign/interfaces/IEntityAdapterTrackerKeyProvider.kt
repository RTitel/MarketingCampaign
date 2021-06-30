package com.example.marketingcampaign.interfaces

import android.os.ParcelUuid

interface IEntityAdapterTrackerKeyProvider {
    fun getKey(): ParcelUuid
}