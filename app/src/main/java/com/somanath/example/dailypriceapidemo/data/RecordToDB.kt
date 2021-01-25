package com.somanath.example.dailypriceapidemo.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "commodity_details")
class RecordToDB(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val arrivalDate: String,
    val commodity: String,
    val district: String,
    val market: String,
    val maxPrice: String,
    val minPrice: String,
    val modalPrice: String,
    val state: String,
    val timestamp: String,
    val variety: String
) : Parcelable

@Parcelize
@Entity(tableName = "record_remote_keys")
data class RecordRemoteKeys(
    @PrimaryKey val recordId: Long,
    val prevKey: Int?,
    val nextKey: Int?
) : Parcelable
