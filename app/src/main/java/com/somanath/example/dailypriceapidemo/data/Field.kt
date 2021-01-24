package com.somanath.example.dailypriceapidemo.data


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Field(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("type")
    val type: String
)