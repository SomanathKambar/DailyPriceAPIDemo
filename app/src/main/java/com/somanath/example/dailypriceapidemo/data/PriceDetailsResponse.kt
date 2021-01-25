package com.somanath.example.dailypriceapidemo.data


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import kotlinx.android.parcel.IgnoredOnParcel

@Keep
data class PriceDetailsResponse(
    @SerializedName("records")
    val records: List<Record>,
    @SerializedName("offset")
val offSet : Int) {
    @IgnoredOnParcel
    val endOfPage = offSet == records.size
}
