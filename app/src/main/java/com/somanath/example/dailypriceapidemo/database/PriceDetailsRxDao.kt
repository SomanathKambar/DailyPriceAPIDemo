package com.somanath.example.dailypriceapidemo.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.somanath.example.dailypriceapidemo.data.Record
import com.somanath.example.dailypriceapidemo.data.RecordRemoteKeys
import com.somanath.example.dailypriceapidemo.data.RecordToDB

@Dao
interface PriceDetailsRxDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<RecordToDB>)

    @Query("SELECT * FROM  commodity_details")
    fun selectAll(): PagingSource<Int, RecordToDB>

    @Query("DELETE FROM commodity_details")
    fun clearPriceDetails()

}

