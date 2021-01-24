package com.somanath.example.dailypriceapidemo.data


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class PriceDetailsResponse(
    @SerializedName("records")
    val records: List<Record>)