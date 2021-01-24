package com.somanath.example.dailypriceapidemo.data


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class TargetBucket(
    @SerializedName("field")
    val `field`: String,
    @SerializedName("index")
    val index: String,
    @SerializedName("type")
    val type: String
)